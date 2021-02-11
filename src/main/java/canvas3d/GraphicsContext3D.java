package canvas3d;

import canvas2d.CanvasRenderer2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Vector;
import math3d.Vector3;

import java.util.Arrays;

public class GraphicsContext3D {
    private GraphicsContext graphicsContext2D;
    private Camera3D camera;

    public GraphicsContext3D(GraphicsContext graphicsContext2D, Camera3D camera){
        this.graphicsContext2D = graphicsContext2D;
        this.camera = camera;
    }

    public void strokeLine(double x1, double y1, double z1, double x2, double y2, double z2){
        double[] start = camera.project(new double[]{x1, y1, z1});
        double[] end = camera.project(new double[]{x2, y2, z2});

        start[0] = CanvasRenderer2D.toCanvasX(start[0]);
        start[1] = CanvasRenderer2D.toCanvasY(start[1]);
        end[0] = CanvasRenderer2D.toCanvasX(end[0]);
        end[1] = CanvasRenderer2D.toCanvasY(end[1]);

//        System.out.println("start" + Arrays.toString(start));
//        System.out.println("end" + Arrays.toString(end));

        graphicsContext2D.strokeLine(start[0], start[1], end[0], end[1]);
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
