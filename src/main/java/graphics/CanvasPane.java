package graphics;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class CanvasPane extends Pane {

    private final Canvas canvas;
    private double startDragX;
    private double startDragY;
    private double endDragX;
    private double endDragY;

    public CanvasPane(double width, double height) {
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


        CanvasRenderer.updateNonCSLines();
    }

    private EventHandler<MouseEvent> startDragEvent = mouse -> {
        startDragX = mouse.getX();
        startDragY = mouse.getY();
    };

    private EventHandler<MouseEvent> endDragEvent = mouse -> {
        endDragX = mouse.getX();
        endDragY = mouse.getY();

        CanvasRenderer.changeOffsetX(endDragX - startDragX);
        CanvasRenderer.changeOffsetY(endDragY - startDragY);

        CanvasRenderer.updateNonCSLines();

        startDragX = mouse.getX();
        startDragY = mouse.getY();
    };

    private EventHandler<ScrollEvent> scrollEvent = event ->{
        if(event.getDeltaY() > 0)
            CanvasRenderer.scaleUnitSize(1.2);
        else
            CanvasRenderer.scaleUnitSize(1/1.2);
    };

}
