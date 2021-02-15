package canvas3d;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math3d.Vector3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class CanvasRenderer3D {
    private static Canvas canvas;
    private static GraphicsContext graphicsContext2D;
    private static GraphicsContext3D graphicsContext3D;
    public static long deltaTime;
    public static Camera3D camera3D;
    private static DecimalFormat fpsFormat = new DecimalFormat("0");

    private static final List<GameObject> gameObjectList = new ArrayList<>();

    public static void start(){
        /*Vector3Renderer iHat = new Vector3Renderer(1, 0, 0);
        Vector3Renderer jHat = new Vector3Renderer(0, 1, 0);
        Vector3Renderer kHat = new Vector3Renderer(0, 0, 1);
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(iHat, "iHat"));
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(jHat, "jHat"));
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(kHat, "kHat"));*/


        /*GameObject mesh = Mesh.fromFile("chevrolet.obj", new Vector3(0, -1, 0));
        gameObjectList.add(mesh);*/

        GameObject cube = new Cube(Vector3.ZERO());
        gameObjectList.add(cube);


        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext3D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                camera3D.updateMatrix();
                graphicsContext3D.clearPolygons();
                camera3D.getLightSource().update();

                /*DefinedVariables.get3DRenderables().forEach(r -> {
                    r.getVariable().render(graphicsContext3D, r.getName(), r.getPaint());
                });
                DefinedVariables.updateText();*/


                gameObjectList.forEach(gameObject -> {
                    gameObject.render(graphicsContext3D);
                });




                graphicsContext2D.setFill(Paint.valueOf("black"));
                if(deltaTime > 0)
                    graphicsContext2D.fillText("FPS: " + 1000/deltaTime, 10, 10);

                lastFrameTime = now;
            }
        };
        animationTimer.start();
    }

    public static void setGraphicsContext(GraphicsContext graphicsContext2D) {
        CanvasRenderer3D.graphicsContext2D = graphicsContext2D;
        camera3D = new Camera3D();
        CanvasRenderer3D.graphicsContext3D = new GraphicsContext3D(graphicsContext2D, camera3D);
    }

    public static void setCanvas(Canvas canvas) {
        CanvasRenderer3D.canvas = canvas;
    }

    public static double getCanvasWidth() {
        return canvas.getWidth();
    }

    public static double getCanvasHeight() {
        return canvas.getHeight();
    }

    public static Camera3D getCamera(){
        return camera3D;
    }
}
