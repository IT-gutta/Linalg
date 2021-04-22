package org.math;

import org.TestUtils;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {
    private Vector vector1;
    private Vector vector2;

    @Test
    public void constructorTest(){
        vector1 = new Vector(1,2,3,4,5);
        assertEquals(vector1.getDimensions(), 5);
        assertEquals(vector1.getElement(0), 1);
        assertEquals(vector1.getElement(3), 4);
    }

    @Test
    public void getMagnitude(){
        vector1 = new Vector(1,2,3);
        TestUtils.assertApproximate(Math.sqrt(14), vector1.getMagnitude());
    }

    @Test
    public void addDimensions(){
        vector1 = new Vector(1,3,-2);
        vector1.addDimensions(5,7);
        assertEquals(5, vector1.getDimensions());
        assertEquals(7, vector1.getElement(4));
    }

    @Test
    public void setMagnitude(){
        vector1 = new Vector(3,4);
        vector1.setMagnitude(10);
        assertEquals(6, vector1.getElement(0));
        vector1.setElement(0, 4);
        assertEquals(4, vector1.getElement(0));
    }

    @Test
    public void scale(){
        vector1 = new Vector(1,3,-2);
        vector1.scale(2);
        assertEquals(6, vector1.getElement(1));
    }

    @Test
    public void getterTest(){
        vector1 = new Vector(3,4);
        vector2 = new Vector(2,4);
        TestUtils.assertApproximate(5d, vector1.getMagnitude());
        assertEquals(2, vector2.factorize());
    }


    @Test
    public void add(){
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        vector1.add(vector2);
        assertTrue(vector1.equals(new Vector(-8, 7)));
        assertFalse(vector1.equals(new Vector(1,2)));
    }

    @Test
    public void dot(){
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        assertEquals(vector1.dot(vector2), 1);
    }

    @Test
    public void hasSameDimensions(){
        vector1 = new Vector(1,2);
        vector2 = new Vector(-9,5);
        assertTrue(vector1.hasSameDimensions(vector2));
        assertFalse(vector1.hasSameDimensions(new Vector(1, 2, 3)));
    }

    @Test
    public void angle(){
        vector1 = new Vector(1,0);
        vector2 = new Vector(0,1);
        assertEquals(vector1.angle(vector2), Math.PI/2);

        vector2 = new Vector(0, 0);
        assertThrows(IllegalArgumentException.class, ()-> vector1.angle(vector2));

        vector2 = new Vector(2, 3, 41);
        assertThrows(IllegalArgumentException.class, ()-> vector1.angle(vector2));
    }

    @Test
    public void isParallel(){
        vector1 = new Vector(1,0);
        vector2 = new Vector(3,0);
        assertTrue(vector1.isParallel(vector2));
        assertFalse(vector1.isParallel(new Vector(1, 2)));
    }

    @Test
    public void cross(){
        vector1 = new Vector(-8, 7);
        vector2 = new Vector(-9,5);
        assertThrows(IllegalArgumentException.class, ()-> vector1.cross(vector2));

        vector1 = new Vector(1,0);
        vector2 = new Vector(0,1);

        vector1.addDimensions(2);
        vector2.addDimensions(-5);
        assertTrue(vector1.cross(vector2).equals(new Vector(-2,5,1)));
    }
}
