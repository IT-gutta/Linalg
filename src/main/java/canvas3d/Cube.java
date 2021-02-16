package canvas3d;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import math3d.Vector3;

import java.util.ArrayList;
import java.util.HashSet;

public class Cube extends Render3D{
    private Vector3[] corners;
    private final String[] colors = {"blue", "red", "yellow", "grey", "green", "cyan"};
    private final Color[][] colorsjall= {
        {Color.valueOf("blue"),Color.valueOf("blue"),Color.valueOf("blue")},
        {Color.valueOf("red"),Color.valueOf("red"),Color.valueOf("red")},
        {Color.valueOf("yellow"),Color.valueOf("yellow"),Color.valueOf("yellow")},
        {Color.valueOf("grey"),Color.valueOf("grey"),Color.valueOf("grey")},
        {Color.valueOf("green"),Color.valueOf("green"),Color.valueOf("green")},
        {Color.valueOf("cyan"),Color.valueOf("cyan"),Color.valueOf("cyan")}
    };

    public Cube(Vector3 position){
        super(position);
        corners = new Vector3[8];

        corners[0] = new Vector3(-0.5, -0.5, -0.5);
        corners[1] = new Vector3(-0.5, 0.5, -0.5);
        corners[2] = new Vector3(0.5, 0.5, -0.5);
        corners[3] = new Vector3(0.5, -0.5, -0.5);
        corners[4] = new Vector3(-0.5, -0.5, +0.5);
        corners[5] = new Vector3(-0.5, 0.5, 0.5);
        corners[6] = new Vector3(0.5, 0.5, 0.5);
        corners[7] = new Vector3(0.5, -0.5, 0.5);
        updateTriangles();
    }

    public void updateTriangles(){
        triangles = new Triangle[12];
        triangles[0] = new Triangle(corners[0], corners[1], corners[2], colorsjall[0][0], colorsjall[0][1], colorsjall[0][2]);
        triangles[1] = new Triangle(corners[0], corners[2], corners[3], colorsjall[0][0], colorsjall[0][1], colorsjall[0][2]);
        triangles[2] = new Triangle(corners[7], corners[6], corners[5], colorsjall[1][0], colorsjall[1][1], colorsjall[1][2]);
        triangles[3] = new Triangle(corners[7], corners[5], corners[4], colorsjall[1][0], colorsjall[1][1], colorsjall[1][2]);
        triangles[4] = new Triangle(corners[4], corners[5], corners[1], colorsjall[2][0], colorsjall[2][1], colorsjall[2][2]);
        triangles[5] = new Triangle(corners[4], corners[1], corners[0], colorsjall[2][0], colorsjall[2][1], colorsjall[2][2]);
        triangles[6] = new Triangle(corners[3], corners[2], corners[6], colorsjall[3][0], colorsjall[3][1], colorsjall[3][2]);
        triangles[7] = new Triangle(corners[3], corners[6], corners[7], colorsjall[3][0], colorsjall[3][1], colorsjall[3][2]);
        triangles[8] = new Triangle(corners[1], corners[5], corners[6], colorsjall[4][0], colorsjall[4][1], colorsjall[4][2]);
        triangles[9] = new Triangle(corners[1], corners[6], corners[2], colorsjall[4][0], colorsjall[4][1], colorsjall[4][2]);
        triangles[10] = new Triangle(corners[4], corners[0], corners[3], colorsjall[5][0], colorsjall[5][1], colorsjall[5][2]);
        triangles[11] = new Triangle(corners[4], corners[3], corners[7], colorsjall[5][0], colorsjall[5][1], colorsjall[5][2]);
    }


    @Override
    public void update(String name, Paint paint) {
        double dThetaX = 0.001 * CanvasRenderer3D.deltaTime;
        double dThetaZ = 0.002 * CanvasRenderer3D.deltaTime;
        double dThetaY = 0.0007 * CanvasRenderer3D.deltaTime;

        rotateX(dThetaX);
        rotateY(dThetaY);
        rotateZ(dThetaZ);
    }

    @Override
    public Object getMath() {
        return null;
    }
}
