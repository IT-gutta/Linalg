package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Solver {
    public static Matrix toReducedRowEchelon(Matrix m) throws IllegalArgumentException{
        HashMap<Integer, ArrayList<Integer>> pivotIndexes = new HashMap<>();
        while(!m.isRowEchelon()){
            //find pivot indexes
            pivotIndexes = findPivotIndexes(m);
            //scale rows
            for(int i = 0; i<m.getHeight(); i++){
                for(int j = 0; j<m.getHeight(); j++){
                    if(m.get(i,j)!=0){
                        m.scaleRow(i, 1/m.get(i,j));
                        break;
                    }
                }
            }
            //sort rows according to pivot element
            int count = 0;
            double[][] sortedM = new double[m.getHeight()][m.getWidth()];
            for(int i = 0; i<m.getHeight(); i++){
                for(int n:pivotIndexes.get(i)){
                    sortedM[count] = m.getRow(n);
                    count++;
                }
            }
            m = new Matrix(sortedM);
            pivotIndexes = findPivotIndexes(m);
            //subtract rows with same pivot index
            count = 0;
            for(int i = 0; i<m.getHeight(); i++){
                for(int j = 0; j<pivotIndexes.get(i).size(); j++){
                    count++;
                    if(j==0)
                        continue;
                    m.addRowToRow(pivotIndexes.get(i).get(j), m.getScaledRow(pivotIndexes.get(i).get(0),-1));
                }
            }
        }
        for(int i = 0; i<m.getHeight(); i++){
            for(int j = i+1; j<m.getHeight(); j++){
                m.addRowToRow(i, m.getScaledRow(j, -m.get(i,j)));
            }
        }
        return m;
    }

    private static HashMap<Integer, ArrayList<Integer>> findPivotIndexes(Matrix m){
        HashMap<Integer, ArrayList<Integer>> pivotIndexes = new HashMap<>();
        for(int i = 0; i<m.getHeight(); i++)
            pivotIndexes.put(i, new ArrayList<Integer>());
        for(int i = 0; i<m.getHeight(); i++){
            for(int j = 0; j<m.getHeight(); j++){
                if(m.get(i,j)!=0){
                    pivotIndexes.get(j).add(i);
                    break;
                }
            }
        }
        return pivotIndexes;
    }

    public static Matrix invertedMatrix(Matrix m){
        m.append(Matrices.getIdentityMatrix(m.getHeight()));
        int a = 0;
        m = toReducedRowEchelon(m);
        double[][] ansM = new double[m.getHeight()][m.getHeight()];
        for(int i = 0; i< m.getHeight(); i++)
            ansM[i] = Arrays.copyOfRange(m.getRow(i),m.getHeight(),m.getHeight()*2);
        return new Matrix(ansM);
    }

    public static Vector solveLinSys(Matrix matrix, Vector v){
        Matrix m = new Matrix(matrix.getMatrix());
        m.append(v);
        m = toReducedRowEchelon(m);
        return new Vector(m.getColumn(m.getHeight()));
    }

    public static void main(String[] args) {
        double[][] arr = {
                {1, 2, 4},
                {2, 1, -3},
                {8, 1, -6},
        };
        Matrix m = new Matrix(arr);
        Vector v = new Vector(1,2,3);
        System.out.println(invertedMatrix(m));
    }
}
