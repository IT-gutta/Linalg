package org.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexNumbersTest {

    @Test
    void fromVector() {
        Vector v = new Vector(1,2,3);
        assertThrows(IllegalArgumentException.class, ()->
                ComplexNumbers.fromVector(v));
        Vector u = new Vector(1,2);
        Complex z = ComplexNumbers.fromVector(u);
        assertEquals(z.getRe(), 1);
        assertEquals(z.getIm(), 2);
        assertEquals(z.getLength(), Math.sqrt(5));
        assertEquals(z.getAngle(), Math.atan(2));
    }

    @Test
    void fromPolar() {
        assertThrows(IllegalArgumentException.class, ()->
                ComplexNumbers.fromPolar(-1,2));
        Complex z = ComplexNumbers.fromPolar(Math.sqrt(5),Math.atan(2));
        assertEquals(z.getRe(), 1);
        assertEquals(z.getIm(), 2);
        assertEquals(z.getLength(), Math.sqrt(5));
        assertEquals(z.getAngle(), Math.atan(2));

    }

    @Test
    void multiply() {
    }

    @Test
    void add() {
    }

    @Test
    void copyOf() {
    }

    @Test
    void pow() {
    }
}