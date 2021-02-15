package math3d;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class Vector3Test {
    Vector3 vector3;

    @BeforeEach
    public void random(){
        vector3 = new Vector3(Math.random()*10 - 5, Math.random()*10 - 5, Math.random()*10 - 5);
    }

    @Test
    public void normalized() {
        for(int i = 0; i < 10; i++) {
            random();
            Assertions.assertEquals(true, Math.abs(vector3.normalized().getMagnitude() - 1) <= 0.0001);
        }
    }

    @Test
    public void scale() {
    }

    @Test
    public void rotateZ() {
    }

    @Test
    public void rotateX() {
    }

    @Test
    public void rotateY() {
    }

    @Test
    public void cross() {
        System.out.println("UP: " + Vector3.UP());
        System.out.println("FORWARD: " + Vector3.FORWARD());
        Vector3 cross = Vector3.cross(Vector3.UP(), Vector3.FORWARD());
        System.out.println(cross);
        Assertions.assertEquals(true, cross.equals(Vector3.RIGHT()));
    }

    @Test
    public void subtract() {
    }

    @Test
    public void add() {
    }
}