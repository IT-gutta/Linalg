package graphics.math2d;

import graphics.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.*;

public class LineSegment2 extends Renderer2D implements Interpolatable {
    private final Point2 start;
    private final Point2 end;
    private Interpolator interpolator;

    public LineSegment2(double x1, double y1, double x2, double y2){
        start = new Point2(x1, y1);
        end = new Point2(x2, y2);
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        gc.setLineWidth(1);
        //linear interpolation for the points
        handleInterpolation();

        gc.setStroke(paint);

        gc.strokeLine(CanvasRenderer2D.toCanvasX(start.getX()), CanvasRenderer2D.toCanvasY(start.getY()), CanvasRenderer2D.toCanvasX(end.getX()), CanvasRenderer2D.toCanvasY(end.getY()));
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {
        start.startInterpolation(m , millis);
        end.startInterpolation(m, millis);
    }

    @Override
    public void handleInterpolation() {
        start.handleInterpolation();
        end.handleInterpolation();
    }


    @Override
    public String toString(){
        return "Start: " + start.toString() + "\tEnd: " + end.toString();
    }
}
