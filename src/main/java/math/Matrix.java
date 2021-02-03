package math;

import exceptions.IllegalNumberOfDimensionsException;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix{
    //TODO fix toString
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

    public Matrix(double a, double b, double c, double d){
        this(2, 2);
        double[][] dArr = {
            {a, b},
            {c, d}
        };
        matrix = dArr;
    }

    public Vector transform(Vector vector) throws IllegalNumberOfDimensionsException {
        if(vector.getDimensions() != width)
            throw new IllegalNumberOfDimensionsException("The vectors number of dimensions doesnt match the matrix width");

        return new Vector(transform(vector.getVector()));
    }

    public double[] transform(double[] coords){
        double[][] allColumns = getAllColumns();

        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                allColumns[i][j] *= coords[i];

        double[] sum = new double[height];

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sum[j] += allColumns[i][j];
            }
        }
        return sum;
    }

    private double[] getColumn(int columnNumber){
        double[] col = new double[height];
        for(int i = 0; i < height; i++){
            col[i] = get(i, columnNumber);
        }
        return col;
    }

    public double[][] getAllColumns(){
        var vecs = new double[width][height];
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
        /*String s = "[\n";
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
        return s;*/
        int[] paddings = new int[width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(Double.toString(matrix[i][j]).length() > paddings[j])
                    paddings[j] = Double.toString(matrix[i][j]).length();
            }
        }
        String str = "\n";
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                String inner = Double.toString(matrix[i][j]);
                while(inner.length() < paddings[j])
                    inner += " ";

                str += inner;
                if(j < width - 1)
                    str += "\t";
            }
            str += "\n";
        }
        return str;
    }

    public static void main(String[] args) {
        double[][] arr = {
                {1, 2},
                {2, 1}
        };

        Matrix m = new Matrix(arr);

        System.out.println(m.transform(new Vector(2, 4)));
        System.out.println(m);
    }
}
