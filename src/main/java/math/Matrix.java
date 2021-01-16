package math;

import java.util.Arrays;

public class Matrix {
    private double[][] matrix;
    private int width;
    private int height;
    public Matrix(double[][] matrix){
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }
    public Matrix(int height, int width){
        this.height = height;
        this.width = width;
        matrix = new double[height][width];
    }

    public Vector linearTransformation(Vector vector){
        if(vector.getDimensions() != width)
            throw new IllegalArgumentException("The vectors number of dimensions doesnt match the matrix width");
        Vector newVec = new Vector(vector.getDimensions());
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                newVec.setElement(y, newVec.getElement(y) + vector.getElement(y) * matrix[y][x]);
            }
        }
        return newVec;
    }


    @Override
    public String toString(){
        String s = "[\n";
        for(int y = 0; y < height; y++){
            s += "[";
            for(int x = 0; x < width; x++){
                s += matrix[y][x];
                if(x != width-1)
                    s += ", ";
            }
            s+= "]\n";
        }

        s += "]";

        return s;
    }

    public static void main(String[] args) {
        double[][] arr = {
                {1, 2},
                {2, 1}
        };

        Matrix m = new Matrix(arr);

        System.out.println(m.linearTransformation(new Vector(2, 4)));
        System.out.println(m);
    }

}
