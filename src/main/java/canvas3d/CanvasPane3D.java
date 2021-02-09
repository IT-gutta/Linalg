package canvas3d;

import canvas2d.CanvasRenderer2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;


public class CanvasPane3D extends Pane {

    private final Canvas canvas;

    public CanvasPane3D(double width, double height) {
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    protected void layoutChildren() {
//        canvas.setOnMousePressed(startDragEvent);
//        canvas.setOnMouseDragged(endDragEvent);
//        canvas.setOnScroll(scrollEvent);

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
}
