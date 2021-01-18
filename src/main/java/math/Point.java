package math;

import java.util.Arrays;

public class Point{
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
        return Arrays.toString(point);
    }

    public Vector toVector(){
        return Vectors.fromPoint(this);
    }


}
