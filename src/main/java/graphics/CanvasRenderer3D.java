package graphics;

import math3d.Vector3;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class CanvasRenderer3D {
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;
    private static GraphicsContext3D graphicsContext3D;
    public static long deltaTime;

    public static void start(){

        Vector3 vector3 = new Vector3(10, 5, -4);
        DefinedVariables.add(new VariableContainer<Vector3>(vector3, "vector3"));

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                DefinedVariables.get3DRenderables().forEach(r -> {
                    r.getVariable().render(graphicsContext, r.getName(), r.getPaint());
                });

                lastFrameTime = now;

                DefinedVariables.updateText();
            }
        };
        animationTimer.start();
    }

    public static void setGraphicsContext(GraphicsContext graphicsContext) {
        CanvasRenderer3D.graphicsContext = graphicsContext;
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
}
