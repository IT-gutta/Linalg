package math3d;

import exceptions.IllegalNumberOfDimensionsException;
import math.Matrix;
import math.Vector;

public class Vector3 extends Vector{
    public Vector3(double... args){
        super(args);
        if(args.length != 3)
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

    public static Vector3 cross(Vector3 v1, Vector3 v2){
        return new Vector3(v1.getY()*v2.getZ()-v2.getY()*v1.getZ(), -(v1.getX()*v2.getZ()-v2.getX()*v1.getZ()), v1.getX()*v2.getY()-v2.getX()*v1.getY());
    }

    public static Vector3 subtract(Vector3 v1, Vector3 v2) throws IllegalNumberOfDimensionsException {
        if(v1.getDimensions() != v2.getDimensions())
            throw new IllegalNumberOfDimensionsException("Vectors must have same dimensions");

        double[] u = new double[v1.getDimensions()];

        for(int i = 0; i<u.length; i++){
            u[i] = v1.getElement(i) - v2.getElement(i);
        }
        return new Vector3(u);
    }


}
