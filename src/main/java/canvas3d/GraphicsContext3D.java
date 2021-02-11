package canvas3d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Vector;
import math3d.Vector3;
import math3d.Vector4;

public class GraphicsContext3D {
    private GraphicsContext graphicsContext2D;
    private Camera3D camera;

    public GraphicsContext3D(GraphicsContext graphicsContext2D, Camera3D camera){
        this.graphicsContext2D = graphicsContext2D;
        this.camera = camera;
    }

    public void strokeLine(double x1, double y1, double z1, double x2, double y2, double z2){
        Vector4 start = camera.project(new Vector3(x1, y1, z1));
        Vector4 end = camera.project(new Vector3(x2, y2, z2));

        /*System.out.println("start" + start.getVector().toString());
        System.out.println("end" + end.getVector().toString());

        System.out.println("startX: " + start.getX() + ", startY: " +start.getY());
        System.out.println("endX: " + end.getX() + ", endY: " +end.getY());*/

        graphicsContext2D.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public void strokeLine(Vector3 start, Vector3 end){
        Vector4 start4 = camera.project(start);
        Vector4 end4 = camera.project(end);

        graphicsContext2D.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public void fillSphere(Vector position, double radius){

    }



    public void setFill(Paint paint){
        graphicsContext2D.setFill(paint);
    }
    public void setFill(String color){
        graphicsContext2D.setFill(Paint.valueOf(color));
    }
    public void setStroke(Paint paint){
        graphicsContext2D.setStroke(paint);
    }
    public void setStroke(String color){
        graphicsContext2D.setStroke(Paint.valueOf(color));
    }

    public void clearRect(double x, double y, double w, double h){
        graphicsContext2D.clearRect(x, y, w, h);
    }
}
