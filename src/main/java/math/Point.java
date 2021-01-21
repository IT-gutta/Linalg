package math;

import graphics.CanvasRenderer;
import graphics.CoordinateSystem;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class Point implements Renderable {
    private double[] point;
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
        Point p = CanvasRenderer.toCanvasPoint(new Point(point));
        gc.fillOval(p.getElement(0) - 5, p.getElement(1)- 5, 10,10);
    }


}
