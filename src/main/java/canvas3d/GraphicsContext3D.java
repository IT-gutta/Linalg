package canvas3d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import math.Vector;
import math3d.Vector3;
import math3d.Vector4;

import java.util.ArrayList;
import java.util.List;

public class GraphicsContext3D {
    private GraphicsContext graphicsContext2D;
    private Camera3D camera;
    private List<Vector4> triangles2;
    private double[] depthBuffer;
    private int width;
    private int height;

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

        graphicsContext2D.strokeLine(start4.getX(), start4.getY(), end4.getX(), end4.getY());
    }

    public void fillPolygon(Vector3... points){
        int l = points.length;
        Vector4[] projectedPoints = new Vector4[l];
        for(int i = 0; i < l; i++){
            projectedPoints[i] = camera.project(points[i]);
            triangles2.add(projectedPoints[i]);
        }


        double[] projectedX = new double[l];
        double[] projectedY = new double[l];
        for(int i = 0; i < l; i++){
            projectedX[i] = projectedPoints[i].getX();
            projectedY[i] = projectedPoints[i].getY();
        }


        graphicsContext2D.fillPolygon(projectedX, projectedY, l);
    }

    public void fillSphere(Vector position, double radius){

    }


    public void clearPolygons(){
        width = (int)CanvasRenderer3D.getCanvasWidth();
        height = (int) CanvasRenderer3D.getCanvasHeight();
        this.triangles2 = new ArrayList<>();
        this.depthBuffer = new double[width*height];
    }

    public void drawPolygons(){

    }

    public void setAlpha(double alpha){
        graphicsContext2D.setGlobalAlpha(alpha);
    }
    public void restoreAlpha(){
        graphicsContext2D.setGlobalAlpha(1);
    }



    public void setFill(Paint paint){
        graphicsContext2D.setFill(paint);
    }
    public void setFill(String color){
        graphicsContext2D.setFill(Paint.valueOf(color));
    }
    public void setFill(Color color){
        graphicsContext2D.setFill(Paint.valueOf(color.toString()));
    }
    public void setStroke(Paint paint){
        graphicsContext2D.setStroke(paint);
    }
    public void setStroke(String color){
        graphicsContext2D.setStroke(Paint.valueOf(color));
    }
    public void setColor(Color color){
        graphicsContext2D.setStroke(Paint.valueOf(color.toString()));
    }


    public void clearRect(double x, double y, double w, double h){
        graphicsContext2D.clearRect(x, y, w, h);
    }
}
