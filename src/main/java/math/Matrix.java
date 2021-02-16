package math;

import math3d.Vector3;

import java.util.ArrayList;

public class Matrix{
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

    public Matrix(){
        this(1,1);
        double[][] dArr = {{0}};
        matrix = dArr;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void invert2x2(){
        double determinant = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
        if(determinant <= 0.0000001)
            throw new RuntimeException("Matrix is invertible");
        double s = 1d / determinant;

        matrix = new double[][]{
                {s*matrix[1][1], -s*matrix[0][1]},
                {-s*matrix[1][0], s*matrix[0][0]}
        };
    }

    public Vector transform(Vector vector) throws IllegalArgumentException {
        if(vector.getDimensions() != width)
            throw new IllegalArgumentException("The vectors number of dimensions doesnt match the matrix width");

        return new Vector(transform(vector.getVector()));
    }
    public Vector3 transform3x3(Vector3 vector3){
        return new Vector3(transform(vector3.getVector()));
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

    /*public Matrix multiply(Matrix other){
        if(width != other.height)
            throw new IllegalArgumentException("Illegal size of matrices");
        double[][] m = new double[height][other.width];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < other.width; j++){
                for(int k = 0; k < width; k++){
                    m[i][j] += matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix(m);
    }*/

    public Matrix multiply(Matrix other){
        if(width != other.height)
            throw new IllegalArgumentException("Illegal size of matrices");
        double[][] m = new double[height][other.width];

        for(int y = 0; y < m.length; y++){
            for(int x = 0; x < m[y].length; x++){
                m[y][x] = new Vector(getRow(y)).dot(new Vector(other.getColumn(x)));
            }
        }
        return new Matrix(m);
    }

    public double[][] getMatrix(){
        return matrix;
    }

    public double[] getColumn(int columnNumber){
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

    public double det(){
        if(width==1){
            return matrix[0][0];
        }
        double sum = 0;
        int j = 0;
        for(int i = 0; i<getWidth(); i++){
            sum+=matrix[0][i]*getDetMatrix(i,j).det()*Math.pow(-1, i);
        }
        return sum;
    }

    private Matrix getDetMatrix(int x, int y){
        double[][] newMatrix = new double[height-1][width-1];
        int counter = 0;
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                if(i!=y && j!=x){
                    newMatrix[counter/(width-1)][counter%(width-1)] = matrix[i][j];
                    counter++;
                }
            }
        }
        return new Matrix(newMatrix);
    }

    public void scaleRow(int row, double scale) throws IllegalArgumentException{
        if(row>=height)
            throw new IllegalArgumentException();
        for(int i = 0; i<width; i++){
            matrix[row][i]*=scale;
        }
    }

    public double[] getRow(int row)throws IllegalArgumentException{
        if(row>=height)
            throw new IllegalArgumentException();
        return matrix[row];
    }

    public double[] getScaledRow(int row, double scale) throws IllegalArgumentException{
        double[] scaledRow = new double[getWidth()];
        for(int i = 0; i<width; i++){
            scaledRow[i] = Math.round(Math.pow(10,10)*matrix[row][i]*scale)/Math.pow(10,10);
        }
        return scaledRow;
    }
    public void addRowToRow(int row1, double[] row2) throws IllegalArgumentException{
        if(row1>=height || width!=row2.length)
            throw new IllegalArgumentException();
        for(int i = 0; i<width; i++){
            matrix[row1][i]+=row2[i];
        }
    }
    public void swapRows(int row1, int row2) throws IllegalArgumentException{
        if(row1>=width || row2>=width)
            throw new IllegalArgumentException();
        double[] r1 = matrix[row1]; double[] r2 = matrix[row2];
        matrix[row1] = r2; matrix[row2] = r1;
    }
    public void append(Vector v){
        for(int i = 0; i<height; i++){
            double[] d = new double[width+1];
            for(int j = 0; j<width; j++)
                d[j] = matrix[i][j];
            d[width] = v.getElement(i);
            matrix[i] = d;
        }
        width+=1;
    }
    public void append(Matrix m){
        double[][] newM = new double[height][2*height];
        for(int i = 0; i<height; i++){
            System.arraycopy(this.getRow(i),0,newM[i], 0, height);
            System.arraycopy(m.getRow(i),0,newM[i], height, height);
        }
        width+=m.getWidth();
        matrix = newM;
    }
    public boolean isIdentityMatrix(){
        for(int i = 0; i<height; i++){
            for(int j = 0; j<height; j++){
                if(i==j){
                    if(matrix[i][j]!=1)
                        return false;
                }
                else
                    if(matrix[i][j]!=0)
                        return false;
            }
        }
        return true;
    }
    public Matrix getInverted(){
        return Solver.invertedMatrix(this);
    }
    public void invert(){
        matrix = Solver.invertedMatrix(this).getMatrix();
    }
    public boolean isRowEchelon(){
        for(int i = 0; i<height; i++){
            for(int j = 0; j<i; j++){
                if(matrix[i][j]!=0)
                    return false;
            }
            if(matrix[i][i]!=1)
                return false;
        }
        return true;
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
                {1, 2, 4, 7},
                {2, 1, -3, 5},
                {8, 1, -6, 8},
                {2, -1, 3, -5}
        };

        Matrix m = new Matrix(arr);

        System.out.println(m.det());
        System.out.println(m);
    }
}
