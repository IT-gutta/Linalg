package canvas3d;

import javafx.scene.paint.Paint;
import math.Vector;
import math3d.Vector3;

public class Cube extends Renderer3D<Vector>{
    private Triangle[] tris;
    private Vector3[] corners;

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

        tris = new Triangle[12];
        tris[0] = new Triangle(corners[0], corners[1], corners[2]);
        tris[1] = new Triangle(corners[0], corners[2], corners[3]);
        tris[2] = new Triangle(corners[7], corners[6], corners[5]);
        tris[3] = new Triangle(corners[7], corners[5], corners[4]);
        tris[4] = new Triangle(corners[4], corners[5], corners[1]);
        tris[5] = new Triangle(corners[4], corners[1], corners[0]);
        tris[6] = new Triangle(corners[3], corners[2], corners[6]);
        tris[7] = new Triangle(corners[3], corners[6], corners[7]);
        tris[8] = new Triangle(corners[1], corners[5], corners[6]);
        tris[9] = new Triangle(corners[1], corners[6], corners[2]);
        tris[10] = new Triangle(corners[4], corners[0], corners[3]);
        tris[11] = new Triangle(corners[4], corners[3], corners[7]);
    }

    @Override
    public void render(GraphicsContext3D gc, String name, Paint paint) {
        for(Triangle tri : tris){
            tri.render(gc);
        }
    }
}
