package canvas3d;

import graphics.DefinedVariables;
import graphics.VariableContainer;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math3d.Vector3;

import java.text.DecimalFormat;

public abstract class CanvasRenderer3D {
    private static Canvas canvas;
    private static GraphicsContext graphicsContext2D;
    private static GraphicsContext3D graphicsContext3D;
    public static long deltaTime;
    public static Camera3D camera3D;
    private static DecimalFormat fpsFormat = new DecimalFormat("0");

    public static void start(){
        /*Vector3Renderer iHat = new Vector3Renderer(1, 0, 0);
        Vector3Renderer jHat = new Vector3Renderer(0, 1, 0);
        Vector3Renderer kHat = new Vector3Renderer(0, 0, 1);
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(iHat, "iHat"));
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(jHat, "jHat"));
        DefinedVariables.add(new VariableContainer<Vector3Renderer>(kHat, "kHat"));*/

        Mesh mesh = Mesh.fromFile("chevrolet.obj", new Vector3(0, -1, 0));
        DefinedVariables.add(new VariableContainer<Mesh>(mesh, "mesh"));
        /*Cube cube1 = new Cube(new Vector3(0, 0, 0));
        DefinedVariables.add(new VariableContainer<Cube>(cube1, "cube"));
        Cube cube2 = new Cube(new Vector3(-2, 0, 0));
        DefinedVariables.add(new VariableContainer<Cube>(cube2, "cube2"));
        Cube cube3 = new Cube(new Vector3(2, 0, 0));
        DefinedVariables.add(new VariableContainer<Cube>(cube3, "cube3"));

        Cube cube4 = new Cube(new Vector3(0, 0, 2));
        DefinedVariables.add(new VariableContainer<Cube>(cube4, "cube4"));
        Cube cube5 = new Cube(new Vector3(-2, 0, 2));
        DefinedVariables.add(new VariableContainer<Cube>(cube5, "cube5"));
        Cube cube6 = new Cube(new Vector3(2, 0, 2));
        DefinedVariables.add(new VariableContainer<Cube>(cube6, "cube6"));

        Cube cube7 = new Cube(new Vector3(0, 0, 4));
        DefinedVariables.add(new VariableContainer<Cube>(cube7, "cube7"));
        Cube cube8 = new Cube(new Vector3(-2, 0, 4));
        DefinedVariables.add(new VariableContainer<Cube>(cube8, "cube8"));
        Cube cube9 = new Cube(new Vector3(2, 0, 4));
        DefinedVariables.add(new VariableContainer<Cube>(cube9, "cube9"));*/

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext3D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                camera3D.updateMatrix();
                graphicsContext3D.clearPolygons();
                camera3D.getLightSource().update();

                DefinedVariables.get3DRenderables().forEach(r -> {
                    r.getVariable().render(graphicsContext3D, r.getName(), r.getPaint());
                });

                lastFrameTime = now;

                DefinedVariables.updateText();

                graphicsContext2D.setFill(Paint.valueOf("black"));
                if(deltaTime > 0)
                    graphicsContext2D.fillText("FPS: " + 1000/deltaTime, 10, 10);
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
