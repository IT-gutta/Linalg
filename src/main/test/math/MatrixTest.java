package org.math;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {
    private Matrix m1;
    @Test
    public void constructorTest(){
        m1 = new Matrix(1,2,3,4);
        assertEquals(m1.get(0,0),1);
        assertEquals(m1.get(1,0),3);
        Matrix m2 = new Matrix(new double[][]{{1, 2},{3,4}});
        assertTrue(m1.equals(m2));
        m2.set(0,0,0);
        assertFalse(m1.equals(m2));
    }

    @Test
    public void getterTest(){
        m1 = new Matrix(1,2,3,4);
        assertTrue(new Vector(m1.getColumn(1)).equals(new Vector(2,4)));
        Matrix m2 = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        assertEquals(m2.det(), 0);
        m2.set(1,1,-1);
        assertEquals(m2.det(), 72);
        assertTrue(new Vector(m2.getScaledRow(0,3)).equals(new Vector(3,6,9)));
        assertFalse(m2.isIdentityMatrix());
        Matrix m3 = new Matrix(1,0,0,1);
        assertTrue(m3.isIdentityMatrix());
        assertFalse(m1.isRowEchelon());
        assertTrue(m3.isRowEchelon());
    }
}
