package org.math;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {
    private Vector vector1;
    private Vector vector2;

    @Test
    public void constructorTest(){
        Vector v1 = new Vector(1,2,3,4,5);
        assertEquals(v1.getDimensions(), 5);
        assertEquals(v1.getElement(0), 1);
        assertEquals(v1.getElement(3), 4);
    }

    @Test
    public void getMagnitude(){

    }

    @Test
    public void alteringOperationsTest(){
        Vector v1 = new Vector(1,3,-2);
        v1.scale(2);
        assertEquals(v1.getElement(1), 6);
        v1.addDimensions(5,7);
        assertEquals(v1.getDimensions(), 5);
        assertEquals(v1.getElement(4), 7);
        Vector v2 = new Vector(3,4);
        v2.setMagnitude(10);
        assertEquals(v2.getElement(0),6);
        v2.setElement(0, 4);
        assertEquals(v2.getElement(0), 4);
    }

    @Test
    public void getterTest(){
        Vector v1 = new Vector(3,4);
        Vector v2 = new Vector(2,4);
        assertEquals(v1.getMagnitude(), 5d);
        assertEquals(v2.factorize(), 2);

    }

    @Test
    public void calculationTest(){
        Vector v1 = new Vector(1,2);
        Vector v2 = new Vector(-9,5);
        v1.add(v2);
        assertTrue(v1.equals(new Vector(-8, 7)));
        assertTrue(v1.equals(new Vector(1,2)));
        assertEquals(v1.dot(v2), 1);
        assertTrue(v1.hasSameDimensions(v2));
        Vector v3 = new Vector(1,0);
        Vector v4 = new Vector(0,1);
        assertEquals(v3.angle(v4), Math.PI/2);
        Vector v5 = new Vector(3,0);
        assertTrue(v3.isParallel(v5));
        assertFalse(v1.isParallel(v2));
        assertThrows(IllegalArgumentException.class, ()->
                v1.cross(v2));
        v3.addDimensions(2);
        v4.addDimensions(-5);
        assertTrue(v3.cross(v4).equals(new Vector(-2,5,1)));
        Matrix m1 = new Matrix(new double[][]{{1,-1},{2,1}});
        assertTrue(v5.getTransformed(m1).equals(new Vector(3,6)));
    }
}
