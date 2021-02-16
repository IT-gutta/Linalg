package math3d;

import math.Vector;

public class Vector4 {
    private Vector vector;
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

    public double getX(){
        return vector.getElement(0);
    }
    public double getY(){
        return vector.getElement(1);
    }
    public double getZ(){
        return vector.getElement(2);
    }
    public double getW(){
        return vector.getElement(3);
    }
    public double[] getVector(){
        return vector.getVector();
    }
    public void setX(double x){
        vector.setElement(0, x);
    }
    public void setY(double y){
        vector.setElement(1, y);
    }
    public void setZ(double z){
        vector.setElement(2, z);
    }
    public void setW(double w){
        vector.setElement(3, w);
    }


}
