package canvas3d;

import math.Matrices;
import math.Matrix;
import math.Vector;
import math.Vectors;

public class Camera3D{
    private double[] position;
    private double[] orientation;
    private double[] displayPosition;
    private Matrix dMatrix;
    public Camera3D(){
        this.position = new double[]{10, 10, 10};
        this.orientation = new double[]{-1, -1, -1};
        this.displayPosition = new double[]{0, 0, 5};
    }

    public double[] project(double[] input){
        if(input.length != 3)
            throw new IllegalArgumentException("Must be 3d vector");

        double[] ac = new double[3];
        for(int i = 0; i < 3; i++)
            ac[i] -= position[i];

        double[] d = dMatrix.transform(ac);

        return new double[]{
                displayPosition[2] * d[0] / d[2] + displayPosition[0],
                displayPosition[2] * d[1] / d[2] + displayPosition[1]};
    }

    public void updateMatrix(){
        double[][] m1 = {
                {1, 0, 0},
                {0, cos(orientation[0]), sin(orientation[0])},
                {0, -sin(orientation[0]), cos(orientation[0])}
        };
        double[][] m2 = {
                {cos(orientation[1]), 0, -sin(orientation[1])},
                {0, 1, 0},
                {sin(orientation[1]), 0, cos(orientation[1])}
        };
        double[][] m3 = {
                {cos(orientation[2]), sin(orientation[2]), 0},
                {-sin(orientation[2]), cos(orientation[2]), 0},
                {0, 0, 1}
        };
        dMatrix = new Matrix(m1).multiply(new Matrix(m2).multiply(new Matrix(m3)));
    }

    private double cos(double angle){
        return Math.cos(angle);
    }
    private double sin(double angle){
        return Math.sin(angle);
    }
}
