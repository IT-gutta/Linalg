package math3d;

import math.Matrices;
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
    public void setX(double x){
        setElement(0, x);
    }
    public void setY(double y){
        setElement(1, y);
    }
    public void setZ(double z){
        setElement(2, z);
    }


    public Vector3 normalized(){
        Vector vec = Vectors.scale(this, 1d/getMagnitude());
        return new Vector3(vec.getVector());
    }



    //STATISKE UTILITY FUNCTIONS
    public static Vector3 scale(Vector3 vec, double s){
        double[] doubles = new double[3];
        for(int i = 0; i < 3; i++){
            doubles[i] = vec.getElement(i) * s;
        }
        return new Vector3(doubles);
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


    //roterer rundt en angitt akse
    public static Vector3 rotate(Vector3 axis, Vector3 input, double angle){
        //TODO fikse denne funksjonen
        Matrix c = new Matrix(new double[][]{
                {0, -axis.getZ(), axis.getY()},
                {axis.getZ(), 0, -axis.getX()},
                {-axis.getY(), axis.getX(), 0}
        });
        Matrix rotationMatrix = Matrices.add(Matrices.getIdentityMatrix(3), Matrices.scale(c, Math.sin(angle)), Matrices.scale(Matrices.product(c, c), 1-Math.cos(angle)));
        return new Vector3(rotationMatrix.transform(input.getVector()));
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

    public static Vector3 add(Vector3 v1, Vector3 v2){
        return new Vector3(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
    }

    public static Vector3 add(Vector3... vecs){
        double[] u = vecs[0].getVector().clone();
        for(int i = 1; i < vecs.length; i++){
            for(int j = 0; j < vecs[0].getDimensions(); j++)
                u[j] += vecs[i].getElement(j);
        }
        return new Vector3(u);
    }

    public static Vector3 UP() {
        return new Vector3(0, 1, 0);
    }
    public static Vector3 FORWARD() {
        return new Vector3(0, 0, 1);
    }
    public static Vector3 RIGHT(){
        return new Vector3(1, 0, 0);
    }
    public static Vector3 ZERO(){
        return new Vector3(0, 0, 0);
    }
}
