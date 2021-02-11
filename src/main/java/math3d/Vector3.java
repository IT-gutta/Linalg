package math3d;

import math.Matrix;
import math.Vector;

public class Vector3 extends Vector{
    public Vector3(double... ds){
        super(ds);
        if(ds.length != 3)
            throw new IllegalArgumentException("Illegal dimension");
    }

    public double getX(){
        return getElement(0);
    }
    public double getY(){
        return getElement(1);
    }
    public double getZ(){
        return getElement(2);
    }

    public static Vector3 rotateZ(Vector3 vec, double angle){
        Matrix rotation = new Matrix(new double[][]{
                {Math.cos(angle), -Math.sin(angle), 0},
                {Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 1}
        });
        return new Vector3(rotation.transform(vec.getVector()));
    }

    public static Vector3 rotateX(Vector3 vec, double angle){
        Matrix rotation = new Matrix(new double[][]{
                {1, 0, 0},
                {0, Math.cos(angle), -Math.sin(angle)},
                {0, Math.sin(angle), Math.cos(angle)},
        });
        return new Vector3(rotation.transform(vec.getVector()));
    }

    public static Vector3 rotateY(Vector3 vec, double angle){
        Matrix rotation = new Matrix(new double[][]{
                {Math.cos(angle), 0, -Math.sin(angle)},
                {0, 1, 0},
                {Math.sin(angle), 0, Math.cos(angle)},
        });
        return new Vector3(rotation.transform(vec.getVector()));
    }
}
