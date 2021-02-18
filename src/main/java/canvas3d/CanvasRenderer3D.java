package canvas3d;

import graphics.DefinedVariables;
import graphics.VariableContainer;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import math.Matrix;

import java.text.DecimalFormat;

public abstract class CanvasRenderer3D {
    private static Canvas canvas;
    private static GraphicsContext graphicsContext2D;
    private static GraphicsContext3D graphicsContext3D;
    public static long deltaTime;
    public static Camera3D camera3D;
    private static DecimalFormat fpsFormat = new DecimalFormat("0");

    //private static final List<Render3D> gameObjects = new ArrayList<>();

    public static void start(){
        /*Vector3Renderer iHat = new Vector3Renderer(1, 0, 0);
        Vector3Renderer jHat = new Vector3Renderer(0, 1, 0);
        Vector3Renderer kHat = new Vector3Renderer(0, 0, 1);
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(iHat, "iHat"));
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(jHat, "jHat"));
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(kHat, "kHat"));*/


        /*Render3D mesh = new Mesh("chevrolet.obj", new Vector3(0, 0, 0), 1);
        mesh.setForward(Vector3.scale(Vector3.FORWARD(), -1));
        DefinedVariables.add(mesh, "chevrolet");*/

//        Render3D cube = new Cube(Vector3.ZERO());
//        DefinedVariables.add(cube, "cube");


        Vector3D x = new Vector3D(1, 0, 0);
        Vector3D y = new Vector3D(0, 1, 0);
        Vector3D z = new Vector3D(0, 0, 1);


        DefinedVariables.add(x, "x");
        DefinedVariables.add(y, "y");
        DefinedVariables.add(z, "z");

        Vector3D vector1 = new Vector3D(3, 3, 3);
        DefinedVariables.add(vector1, "vector1");

        Matrix rotate3 = new Matrix(new double[][]{
                {0, -1, 0},
                {0, 0, -1},
                {1, 0, 0}
        });
        DefinedVariables.add(new VariableContainer<>(rotate3, "rotate3"));

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext3D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                camera3D.updateMatrix();
                graphicsContext3D.clearPolygons();

                DefinedVariables.get3DRenderables().forEach(r -> {
                    r.getVariable().render(graphicsContext3D, r.getName(), r.getColor());
                });
                DefinedVariables.updateText();



                graphicsContext2D.setFont(new Font(13));
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
        DefinedVariables.add(camera3D, "Camera");
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
