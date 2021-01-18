package math;

import exceptions.IllegalNumberOfDimensionsException;

import java.util.ArrayList;

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

    public Vector transformVector(Vector vector) throws IllegalNumberOfDimensionsException {
        if(vector.getDimensions() != width)
            throw new IllegalNumberOfDimensionsException("The vectors number of dimensions doesnt match the matrix width");
        Vector[] allColumns = getAllColumns();
        for(int i = 0; i < width; i++){
            allColumns[i].scale(vector.getElement(i));
        }
        return Vectors.add(allColumns);
    }

    private Vector getColumn(int columnNumber){
        double[] col = new double[height];
        for(int i = 0; i < height; i++){
            col[i] = get(i, columnNumber);
        }
        return new Vector(col);
    }

    public Vector[] getAllColumns(){
        var vecs = new Vector[width];
        for(int column = 0; column < width; column++){
            vecs[column] = getColumn(column);
        }
        return vecs;
    }

    public ArrayList<Vector> getBasisVectors(){
        ArrayList<Vector> vectors = new ArrayList<>();
        for(int x = 0; x < width; x++){
            var vec = new Vector();
            for(int y = 0; y < height; y++){
                vec.setElement(y, get(y, x));
            }
            vectors.add(vec);
        }

        return vectors;
    }


    public double get(int y, int x){
        return matrix[y][x];
    }

    public void set(int y, int x, double value){
        matrix[y][x] = value;
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

        System.out.println(m.transformVector(new Vector(2, 4)));
        System.out.println(m);
    }

}
