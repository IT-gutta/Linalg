package canvas2d;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class CanvasPane2D extends Pane {

    private final Canvas canvas;
    private double startDragX;
    private double startDragY;
    //private double scrollScale = Math.pow(2,(double)1/5);
    private final double scrollScale = 0.1;
    public CanvasPane2D(double width, double height) {
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    protected void layoutChildren() {
        canvas.setOnMousePressed(startDragEvent);
        canvas.setOnMouseDragged(endDragEvent);
        canvas.setOnScroll(scrollEvent);

        super.layoutChildren();
        final double x = snappedLeftInset();
        final double y = snappedTopInset();

        final double w = snapSizeX(getWidth()) - x - snappedRightInset();
        final double h = snapSizeY(getHeight()) - y - snappedBottomInset();
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        canvas.setWidth(w);
        canvas.setHeight(h);


        CanvasRenderer2D.accountForChanges();
    }

    private EventHandler<MouseEvent> startDragEvent = mouse -> {
        startDragX = mouse.getX();
        startDragY = mouse.getY();
    };

    private EventHandler<MouseEvent> endDragEvent = mouse -> {
        double endDragX = mouse.getX();
        double endDragY = mouse.getY();

        CanvasRenderer2D.changeOffsetX(endDragX - startDragX);
        CanvasRenderer2D.changeOffsetY(endDragY - startDragY);

        CanvasRenderer2D.accountForChanges();

        startDragX = mouse.getX();
        startDragY = mouse.getY();
    };

    private double clampScroll(double val){
        if(Math.abs(val) < 1.05)
            return 1.05;
        if(Math.abs(val) > 1.2)
            return 1.2;
        return Math.abs(val);
    }

    private EventHandler<ScrollEvent> scrollEvent = event ->{
        if(event.getDeltaY() > 0)
            CanvasRenderer2D.scaleUnitSize(clampScroll(event.getDeltaY()));
        else
            CanvasRenderer2D.scaleUnitSize(1/clampScroll(event.getDeltaY()));

        CanvasRenderer2D.accountForChanges();
    };
}