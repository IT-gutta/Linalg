package canvas3d;

import canvas2d.CanvasRenderer2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import math3d.Vector3;


public class CanvasPane3D extends Pane {

    private final Canvas canvas;
    private boolean mouseIsPressed;
    private final double mouseSensitivity = 0.004;
    private double previousX;
    private double previousY;

    public CanvasPane3D(double width, double height) {
        canvas = new Canvas(width, height);
        //canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());

        getChildren().add(canvas);

        canvas.setOnKeyPressed(keyHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMousePressed(event -> {
            previousX = event.getX();
            previousY = event.getY();
            this.mouseIsPressed = true;
        });
        canvas.setOnMouseReleased(event -> {
            this.mouseIsPressed = false;
        });
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


    private EventHandler<MouseEvent> mouseHandler = mouseEvent -> {
        double movementX = (mouseEvent.getX() - previousX) * mouseSensitivity;
        double movementY = (mouseEvent.getY() - previousY) * mouseSensitivity;
        CanvasRenderer3D.getCamera().rotateY(-movementX);

        //TODO fix pitching
        //CanvasRenderer3D.getCamera().setForward(Vector3.rotate(CanvasRenderer3D.getCamera().right, CanvasRenderer3D.getCamera().forward, movementY));

        previousX = mouseEvent.getX();
        previousY = mouseEvent.getY();
    };

    private EventHandler<KeyEvent> keyHandler = keyEvent ->{
        //System.out.println("Character: " + keyEvent.getCode());

        if(keyEvent.getCode().equals(KeyCode.SPACE))
            CanvasRenderer3D.getCamera().moveUp(0.5);

        else if(keyEvent.getCode().equals(KeyCode.SHIFT))
            CanvasRenderer3D.getCamera().moveUp(-0.5);

        else if(keyEvent.getCode().equals(KeyCode.D))
            CanvasRenderer3D.getCamera().moveRight(0.5);

        else if(keyEvent.getCode().equals(KeyCode.A))
            CanvasRenderer3D.getCamera().moveRight(-0.5);

        else if(keyEvent.getCode().equals(KeyCode.W))
            CanvasRenderer3D.getCamera().moveForward(0.5);

        else if(keyEvent.getCode().equals(KeyCode.S))
            CanvasRenderer3D.getCamera().moveForward(-0.5);
    };
}
