package math;

import exceptions.IllegalNumberOfDimensionsException;

public class Matrices {
    public static Matrix getIdentityMatrix(int n) throws IllegalArgumentException{
        if(n<=0)
            throw new IllegalArgumentException();
        double[][] m = new double[n][n];
        for(int i = 0; i<n; i++){
            m[i][i] = 1;
        }
        return new Matrix(m);
    }
    public static Matrix product(Matrix m1, Matrix m2){
        if(m1.getWidth() != m2.getHeight())
            throw new IllegalArgumentException("Illegal size of matrices");
        double[][] m = new double[m1.getHeight()][m2.getWidth()];
        for(int i = 0; i < m1.getHeight(); i++){
            for(int j = 0; j < m2.getWidth(); j++){
                for(int k = 0; k < m1.getWidth(); k++){
                    m[i][j] += m1.get(i,k) * m1.get(k,j);
                }
            }
        }
        return new Matrix(m);
    }
}
