package math;

import graphics.CanvasRenderer;
import graphics.Interpolator;
import graphics.Renderable;
import graphics.VariableContainer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Point implements Renderable, Transformable {
    private double[] point;
    private boolean isHidden = false;
    private VariableContainer<Point> wrapper;
    private Interpolator interpolator;
    public Point(double... args){
        point = args;
    }
    public Point(String name, double... args){
        point = args;
        this.name = name;
    }
    public double[] getPoint(){
        return point;
    }
    public double getElement(int i){
        return point[i];
    }
    public void setElement(int i, double d){
        point[i] = d;
    }
    public String name;

//    public Point transform(Matrix matrix){
//        return matrix.transform(toVector()).toPoint();
//    }

    public int getDimensions(){
        return point.length;
    }

    @Override
    public String toString(){
        String s = "";
        for(double element:point){
            s+=Double.toString(element)+", ";
        }
        return "("+s.substring(0,s.length()-2)+")";
    }

    public Vector toVector(){
        return Vectors.fromPoint(this);
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        //linear interpolation
        handleLerp();

        if(isHidden())
            return;

        gc.setFill(paint);

        gc.fillOval(CanvasRenderer.toCanvasX(point[0]) - 5, CanvasRenderer.toCanvasY(point[1])- 5, 10,10);
        Point p = CanvasRenderer.toCanvasPoint(new Point(point));
        gc.fillOval(p.getElement(0) - 5, p.getElement(1)- 5, 10,10);
        if(name!=null){
            gc.setFill(Paint.valueOf("purple"));
            Vector d = Vectors.scale(Vectors.fromPoint(this), 1/toVector().getMagnitude()/3);
            gc.fillText(name, CanvasRenderer.toCanvasX(getElement(0)+d.getElement(0)), CanvasRenderer.toCanvasY(getElement(1)+d.getElement(1)));
        }
    }


    @Override
    public void transform(Matrix m){
        transform(m, 1000);
    }

    public void transform(Matrix m, int millis){
        double[] endPos = m.transform(point);
        double startAngle = Math.atan2(point[1], point[0]);
        double endAngle = startAngle + Vectors.angle2(point, endPos);
        double startLength = Math.sqrt(Math.pow(point[0], 2) + Math.pow(point[1], 2));
        double endLength = Math.sqrt(Math.pow(endPos[0], 2) + Math.pow(endPos[1], 2));
        interpolator = new Interpolator(millis, new double[]{startLength, startAngle}, new double[]{endLength, endAngle});
    }

    public void handleLerp(){
        if(interpolator != null){
            interpolator.handle();
            //0 is the length, and 1 is the angle
            setElement(0, interpolator.get(0) * Math.cos(interpolator.get(1)));
            setElement(1, interpolator.get(0) * Math.sin(interpolator.get(1)));
            if(interpolator.isFinished())
                interpolator = null;
        }
    }



    @Override
    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public void show() {
        isHidden = false;
    }

    @Override
    public void hide() {
        isHidden = true;
    }
}
