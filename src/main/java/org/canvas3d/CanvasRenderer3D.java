package org.canvas3d;

import org.graphics.DefinedVariables;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
/**
 * Handles rendering of 3D objects onto the canvas
 */
public abstract class CanvasRenderer3D {
    private static Canvas canvas;
    private static GraphicsContext graphicsContext2D;
    private static GraphicsContext3D graphicsContext3D;
    public static long deltaTime;
    public static Camera3D camera3D;
    private static DecimalFormat fpsFormat = new DecimalFormat("0");
    public static int chunksRenderedCount;
    public static int chunksSpawnedCount;
    public enum CameraMode{STANDARD, FPS};
    public static CameraMode cameraMode = CameraMode.STANDARD;

    //private static final List<Render3D> gameObjects = new ArrayList<>();

    /**
     * Starts an animation timer
     */
    public static void start(){
//        Mesh chevrolet = new Mesh("chevrolet.obj", new Vector3(0, 0, 0), 1);
//        chevrolet.setForward(Vector3.scale(Vector3.FORWARD(), -1));
//        DefinedVariables.add(chevrolet, "chevrolet");
//        chevrolet.setColors(Color.AZURE, Color.AZURE, Color.AZURE);

//        Render3D cube = new Cube(Vector3.ZERO(), 1);
//        DefinedVariables.add(cube, "cube");
//
//        Point3D point3D = new Point3D(5, 2, 0);
//        DefinedVariables.add(point3D, "point3d");


//        Vector3D x = new Vector3D(1, 0, 0);
//        Vector3D y = new Vector3D(0, 1, 0);
//        Vector3D z = new Vector3D(0, 0, 1);
//
//
//        DefinedVariables.add(x, "x");
//        DefinedVariables.add(y, "y");
//        DefinedVariables.add(z, "z");
////
//        Sphere sphere = new Sphere(Vector3.ZERO(), 3);
//        DefinedVariables.add(sphere, "sphere");


//        Matrix rotate3 = new Matrix(new double[][]{
//                {0, -1, 0},
//                {0, 0, -1},
//                {1, 0, 0}
//        });
//        DefinedVariables.add(new VariableContainer<>(rotate3, "rotate3"));

//        TerrainChunk terrain = new TerrainChunk(Vector3.ZERO(), 100, new PerlinNoiseMap());
//        DefinedVariables.add(terrain, "terrain");

//        InfiniteTerrain infiniteTerrain = new InfiniteTerrain();
//        DefinedVariables.add(infiniteTerrain, "terrain");

        /*Vector3D v1 = new Vector3D(1, 0, -1);
        Vector3D v2 = new Vector3D(1, Math.sqrt(2), 1);
        Vector3D v3 = new Vector3D(1, -Math.sqrt(2), 1);

        DefinedVariables.add(v1, "v1");
        DefinedVariables.add(v2, "v2");
        DefinedVariables.add(v3, "v3");*/


        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                chunksRenderedCount = 0;
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext3D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                camera3D.updateMatrix();
                graphicsContext3D.clearDepthBuffer();

                DefinedVariables.get3DRenderables().forEach(r -> {
                    r.getVariable().render(graphicsContext3D);
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

    /**
     * Initializes the Camera3D and the 3D graphic context (which internally is using the canvas 2D graphics context)
     */
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
