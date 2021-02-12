package canvas3d;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import math.Line;
import math.Point;
import math3d.Line3;
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
        //Vector3 middle = getCentroid();
        double brightness = CanvasRenderer3D.getCamera().getLightSource().getBrightness(vertices[0], getNormal());


        gc.setFill(Color.grayRgb((int) (Math.sqrt(brightness) * 255)));
        //gc.setFill(paint);
        gc.fillPolygon(vertices);

        //gc.setStroke("black");
        gc.strokeLine(vertices[0], vertices[1]);
        gc.strokeLine(vertices[1], vertices[2]);
        gc.strokeLine(vertices[2], vertices[0]);
    }

    public boolean facingCamera(){
        try {
            return getNormal().dot(Vector3.subtract(vertices[0], CanvasRenderer3D.getCamera().getPosition())) < 0;
        }
        catch (Exception e){
            System.out.println("facingCamera");
            e.printStackTrace();
        }
        return false;
    }

    public Vector3 getNormal(){
        try {
            return Vector3.cross(Vector3.subtract(vertices[1], vertices[0]), Vector3.subtract(vertices[2], vertices[0]));
        }
        catch (Exception e){
            System.out.println("getNormal");
            e.printStackTrace();
        }
        return null;
    }

    public Vector3 getCentroid(){
        Vector3 normal = getNormal();
        Vector3 r1 = new Vector3(vertices[0].getX()-vertices[1].getX(), vertices[0].getY()-vertices[1].getY(), vertices[0].getZ()-vertices[1].getZ());
        Vector3 r2 = new Vector3(vertices[0].getX()-vertices[2].getX(), vertices[0].getY()-vertices[2].getY(), vertices[0].getZ()-vertices[2].getZ());
        Vector3 v1 = Vector3.cross(normal, r1);
        Vector3 v2 = Vector3.cross(normal, r2);

        Line3 line1 = new Line3(new Vector3((vertices[0].getX() + vertices[1].getX())/2, (vertices[0].getY() + vertices[1].getY())/2, (vertices[0].getZ() + vertices[1].getZ())/2), v1);
        Line3 line2 = new Line3(new Vector3((vertices[0].getX() + vertices[2].getX())/2, (vertices[0].getY() + vertices[2].getY())/2, (vertices[0].getZ() + vertices[2].getZ())/2), v2);

        Vector3 intersection = line1.intersection(line2);
//        if(intersection == null)
//            return null;
        if(intersection == null)
            throw new Error("FEIL I getCentroid()-funksjonen");

        return intersection;
    }

    public Vector3[] getVertices(){
        return vertices;
    }
}
