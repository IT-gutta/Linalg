package math3d;

import math.Vector;

import java.io.Serializable;

/**
 * Represents a four dimensional vector
 */
public class Vector4 implements Serializable {
    private final Vector vector;

    public Vector4(double... ds){
        if(ds.length != 4)
            throw new IllegalArgumentException("Illegal dimension!");
        this.vector = new Vector(ds);
    }

    public Vector4(Vector vec){
        if(vec.getDimensions() != 4)
            throw new IllegalArgumentException("Illegal dimension!");
        this.vector = vec;
    }

    /**
     * Returns the x coordinate of the vector
     */
    public double getX(){
        return vector.getElement(0);
    }

    /**
     * Returns the y coordinate of the vector
     */
    public double getY(){
        return vector.getElement(1);
    }

    /**
     * Returns the z coordinate of the vector
     */
    public double getZ(){
        return vector.getElement(2);
    }

    /**
     * Returns the w coordinate of the vector
     */
    public double getW(){
        return vector.getElement(3);
    }

    /**
     * Returns Vector as an array
     */
    public double[] getVector(){
        return vector.getVector();
    }

    /**
     * Sets the x coordinate of the vector
     */
    public void setX(double x){
        vector.setElement(0, x);
    }

    /**
     * Sets the y coordinate of the vector
     */
    public void setY(double y){
        vector.setElement(1, y);
    }

    /**
     * Sets the z coordinate of the vector
     */
    public void setZ(double z){
        vector.setElement(2, z);
    }

    /**
     * Sets the w coordinate of the vector
     */
    public void setW(double w){
        vector.setElement(3, w);
    }

    @Override
    public String toString(){
        return vector.toString();
    }
}
