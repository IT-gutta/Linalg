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
}
