package canvas3d;

import javafx.scene.paint.Paint;
import math3d.Vector3;

public class Triangle {
    private Paint paint;
    private Vector3[] vertices;
    public Triangle(String color, Vector3 p1, Vector3 p2, Vector3 p3){
        this.vertices = new Vector3[]{p1, p2, p3};
        this.paint = Paint.valueOf(color);
    }
    public Triangle(String color, double... coords){
        if(coords.length != 9)
            throw new IllegalArgumentException("Illegal number of points, must be 9");
        this.vertices = new Vector3[3];
        for(int i = 0; i < 3; i++){
            this.vertices[i] = new Vector3(coords[i], coords[i+1], coords[i+2]);
        }
        this.paint = Paint.valueOf(color);
    }

    public void render(GraphicsContext3D gc){
        if(!facingCamera())
            return;

        gc.setFill(paint);
        gc.fillPolygon(vertices);

        gc.setStroke("black");
        gc.strokeLine(vertices[0], vertices[1]);
        gc.strokeLine(vertices[1], vertices[2]);
        gc.strokeLine(vertices[2], vertices[0]);
    }

    public boolean facingCamera(){
        return getNormal().dot(Vector3.subtract(vertices[0], CanvasRenderer3D.getCamera().getPosition())) < 0;
    }

    public Vector3 getNormal(){
        return Vector3.cross(Vector3.subtract(vertices[1], vertices[0]), Vector3.subtract(vertices[2], vertices[0]));
    }
}
