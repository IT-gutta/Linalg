package canvas3d;

import math.Matrix;
import math.Vector;
import math.Vectors;

public class Camera3D{
    private double[] position;
    private double[] direction;
    private double[] displayPosition;
    private Matrix dMatrix;
    public Camera3D(){
        this.position = new double[]{10, 10, 10};
        this.direction = new double[]{-1, -1, -1};
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

    public void setDirection(Vector direction){
        this.direction = direction.getVector();
    }

    public void updateMatrix(){
        double[] angles = {
                Math.atan2(direction[0], -direction[1]),
                Math.atan2(Math.sqrt(Math.pow(direction[0], 2) + Math.pow(direction[1], 2)), direction[2]),
                0
        };
        double[][] m1 = {
                {1, 0, 0},
                {0, cos(angles[0]), sin(angles[0])},
                {0, -sin(angles[0]), cos(angles[0])}
        };
        double[][] m2 = {
                {cos(angles[1]), 0, -sin(angles[1])},
                {0, 1, 0},
                {sin(angles[1]), 0, cos(angles[1])}
        };
        double[][] m3 = {
                {cos(angles[2]), sin(angles[2]), 0},
                {-sin(angles[2]), cos(angles[2]), 0},
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
