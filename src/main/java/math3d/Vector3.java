package math3d;

import math.Matrix;
import math.Vector;
import math.Vectors;

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


    public Vector3 normalized(){
        Vector vec = Vectors.scale(this, 1d/getMagnitude());
        return new Vector3(vec.getVector());
    }



    //STATISKE UTILITY FUNCTIONS
    public static Vector3 scale(Vector3 vec, double s){
        for(int i = 0; i < 3; i++){

        }
        return new Vector3();
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

    public static Vector3 subtract(Vector3 v1, Vector3 v2) throws IllegalArgumentException {
        if(v1.getDimensions() != v2.getDimensions())
            throw new IllegalArgumentException("Vectors must have same dimensions");

        double[] u = new double[3];

        for(int i = 0; i<3; i++){
            u[i] = v1.getElement(i) - v2.getElement(i);
        }
        if(Double.isNaN(u[0]))
            throw new IllegalArgumentException("Input cant be have NaN as an element");

        return new Vector3(u);
    }
}
