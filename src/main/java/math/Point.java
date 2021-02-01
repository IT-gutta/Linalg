package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

public class Point implements Renderable {
    private double[] point;
    private boolean isHidden = false;
    public Point(double... args){
        point = args;
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

    public Point transform(Matrix matrix){
        return matrix.transformVector(toVector()).toPoint();
    }

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
    public void render(GraphicsContext gc){
        if(isHidden())
            return;
        Point p = CanvasRenderer.toCanvasPoint(new Point(point));
        gc.fillOval(p.getElement(0) - 5, p.getElement(1)- 5, 10,10);
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
