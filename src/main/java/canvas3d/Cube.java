package canvas3d;

import javafx.scene.paint.Paint;
import math.Vector;
import math3d.Vector3;

public class Cube extends Renderer3D<Vector>{
    private Triangle[] tris;
    private Vector3[] corners;
    private final String[] colors = {"maroon", "red", "yellow", "grey", "green", "cyan"};

    public Cube(){
        super(new Vector());
        corners = new Vector3[8];

        corners[0] = new Vector3(0, 0, 0);
        corners[1] = new Vector3(0, 1, 0);
        corners[2] = new Vector3(1, 1, 0);
        corners[3] = new Vector3(1, 0, 0);
        corners[4] = new Vector3(0, 0, 1);
        corners[5] = new Vector3(0, 1, 1);
        corners[6] = new Vector3(1, 1, 1);
        corners[7] = new Vector3(1, 0, 1);
        updateTriangles();
    }

    public void updateTriangles(){
        tris = new Triangle[12];
        tris[0] = new Triangle(colors[0], corners[0], corners[1], corners[2]);
        tris[1] = new Triangle(colors[0], corners[0], corners[2], corners[3]);
        tris[2] = new Triangle(colors[1], corners[7], corners[6], corners[5]);
        tris[3] = new Triangle(colors[1], corners[7], corners[5], corners[4]);
        tris[4] = new Triangle(colors[2], corners[4], corners[5], corners[1]);
        tris[5] = new Triangle(colors[2], corners[4], corners[1], corners[0]);
        tris[6] = new Triangle(colors[3], corners[3], corners[2], corners[6]);
        tris[7] = new Triangle(colors[3], corners[3], corners[6], corners[7]);
        tris[8] = new Triangle(colors[4], corners[1], corners[5], corners[6]);
        tris[9] = new Triangle(colors[4], corners[1], corners[6], corners[2]);
        tris[10] = new Triangle(colors[5], corners[4], corners[0], corners[3]);
        tris[11] = new Triangle(colors[5], corners[4], corners[3], corners[7]);
    }

    @Override
    public void render(GraphicsContext3D gc, String name, Paint paint) {
        double dThetaX = 0.0005 * CanvasRenderer3D.deltaTime;
        double dThetaZ = 0.0001 * CanvasRenderer3D.deltaTime;
        double dThetaY = 0.0007 * CanvasRenderer3D.deltaTime;

        for(int i = 0; i < corners.length; i++){
            corners[i] = Vector3.rotateX(corners[i], dThetaX);
            corners[i] = Vector3.rotateZ(corners[i], dThetaZ);
            corners[i] = Vector3.rotateY(corners[i], dThetaY);
        }
        updateTriangles();
        for(Triangle tri : tris){
            tri.render(gc);
        }
    }
}
