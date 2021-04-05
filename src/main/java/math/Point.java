package math;

import java.io.Serializable;

/**
 * Represents a mathematical point
 */
public class Point implements Transformable, Serializable {
    private final double[] point;

    public Point(double... args){
        point = args;
    }

    /**
     * Returns the point as a an array
     */
    public double[] getPoint(){
        return point;
    }

    /**
     * Returns a given element
     */
    public double getElement(int i){
        return point[i];
    }

    /**
     * Sets a given element
     */
    public void setElement(int i, double d){
        point[i] = d;
    }

    /**
     * Returns the dimension of the Point
     */
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

    /**
     * Returns the Point as a Vector
     */
    public Vector toVector(){
        return Vectors.fromPoint(this);
    }


    /**
     * Transforms the Point with a Matrix
     */
    @Override
    public void transform(Matrix m){
        //skriv kode her
    }
}
