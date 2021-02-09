package math2d;

import canvas2d.CanvasRenderer2D;
import graphics.Interpolatable;
import graphics.Interpolator;
import canvas2d.Renderer2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.*;

public class Point2 extends Renderer2D<Point> implements Interpolatable {
    private Interpolator interpolator;

    public Point2(Point point){
        super(point);
    }

    public Point2(double x, double y){
        super(new Point(x, y));
    }

    public double getX(){
        return math.getElement(0);
    }
    public double getY(){
        return math.getElement(1);
    }
    public void setX(double x){
        math.setElement(0, x);
    }
    public void setY(double y){
        math.setElement(1, y);
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {
        double[] p = math.getPoint();
        double[] endPos = m.transform(p);
        double startAngle = Math.atan2(p[1], p[0]);
        double endAngle = startAngle + Vectors.angle2(p, endPos);
        double startLength = Math.sqrt(Math.pow(p[0], 2) + Math.pow(p[1], 2));
        double endLength = Math.sqrt(Math.pow(endPos[0], 2) + Math.pow(endPos[1], 2));
        interpolator = new Interpolator(millis, new double[]{startLength, startAngle}, new double[]{endLength, endAngle});
    }

    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();
            //0 is the length, and 1 is the angle
            math.setElement(0, interpolator.get(0) * Math.cos(interpolator.get(1)));
            math.setElement(1, interpolator.get(0) * Math.sin(interpolator.get(1)));
            if(interpolator.isFinished())
                interpolator = null;
        }
    }



    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        double[] p = math.getPoint();
        //linear interpolation
        handleInterpolation();

        gc.setFill(paint);

        gc.fillOval(CanvasRenderer2D.toCanvasX(p[0]) - 5, CanvasRenderer2D.toCanvasY(p[1])- 5, 10,10);
        gc.fillOval(CanvasRenderer2D.toCanvasX(p[0]) - 5, CanvasRenderer2D.toCanvasY(p[1])- 5, 10,10);
        if(name!=null){
            gc.setFill(Paint.valueOf("purple"));
            Vector d = Vectors.scale(Vectors.fromPoint(math), 1/ math.toVector().getMagnitude()/3);
            gc.fillText(name, CanvasRenderer2D.toCanvasX(p[0]+d.getElement(0)), CanvasRenderer2D.toCanvasY(p[1]+d.getElement(1)));
        }
    }

}