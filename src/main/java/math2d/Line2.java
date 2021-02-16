package math2d;

import canvas2d.CanvasRenderer2D;
import graphics.Interpolatable;
import canvas2d.Render2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Line;
import math.Matrix;
import math.Point;

import java.util.ArrayList;
import java.util.List;

public class Line2 extends Render2D<Line> implements Interpolatable {
    private Vector2 direction;
    private Point2 start;
    private boolean isInsideCanvas;

    private double canvasStartX = 0;
    private double canvasStartY = 0;
    private double canvasEndX = 0;
    private double canvasEndY = 0;

    public Line2(Point2 start, Vector2 direction){
        this(start.getX(), start.getY(), direction.getX(), direction.getY());
        this.start = start;
        this.direction = direction;
    }
    public Line2(double sx, double sy,double dx,double dy){
        super(new Line(sx, sy, dx, dy));
        this.start = new Point2(sx, sy);
        this.direction = new Vector2(dx, dy);
    }



    @Override
    public void startInterpolation(Matrix m, int millis) {
        start.startInterpolation(m, millis);
        direction.startInterpolation(m, millis);
    }

    @Override
    public void handleInterpolation() {
        direction.handleInterpolation();
        start.handleInterpolation();
        updateCanvasPoints();
    }


    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        //linear interpolation of transformation
        handleInterpolation();


        //System.out.println(isInsideCanvas);
        if(!isInsideCanvas)
            return;

        gc.setStroke(paint);
        gc.setLineWidth(1);
        gc.strokeLine(canvasStartX, canvasStartY, canvasEndX, canvasEndY);
    }


    public void updateCanvasPoints(){
        if(Math.abs(direction.getX()) <= 0.001 / CanvasRenderer2D.unitSize){
            //vertical linje
            isInsideCanvas = true;
            if(start.getX() <= CanvasRenderer2D.fromCanvasX(CanvasRenderer2D.getCanvasWidth()) && start.getX() >= CanvasRenderer2D.fromCanvasX(0))
                isInsideCanvas = true;
            canvasStartX = CanvasRenderer2D.toCanvasX(start.getX());
            canvasEndX = CanvasRenderer2D.toCanvasX(start.getX());
            canvasStartY = 0;
            canvasEndY = CanvasRenderer2D.getCanvasHeight();
            return;
        }
        //calculate intersection with canvas
        List<Point> startEndPoints = new ArrayList<>();


        //x coords from origin for the vertical canvas borders
        double l1 = CanvasRenderer2D.fromCanvasX(0);
        double l3 = CanvasRenderer2D.fromCanvasX(CanvasRenderer2D.getCanvasWidth());

        //y coords from origin for the horizontal canvas borders
        double l2 = CanvasRenderer2D.fromCanvasY(0);
        double l4 = CanvasRenderer2D.fromCanvasY(CanvasRenderer2D.getCanvasHeight());

        //intersect left vertical
        double y1 = getYFromX(l1);
        if(y1 >= l4 && y1 <= l2) //intersection!
            startEndPoints.add(new Point(l1, y1));


        //intersect right vertical
        double y3 = getYFromX(l3);
        if(y3 >= l4 && y3 <= l2)//intersection!
            startEndPoints.add(new Point(l3, y3));

        //intersect top horizontal
        double x2 = getXFromY(l2);
        if(x2 <= l3 && x2 >= l1) //intersection!
            startEndPoints.add(new Point(x2, l2));

        //intersect bottom horizontal
        double x4 = getXFromY(l4);
        if(x4 <= l3 && x4 >= l1) //intersection!
            startEndPoints.add(new Point(x4, l4));

        if(startEndPoints.size() != 2){
            isInsideCanvas = false;
            return;
        }
        isInsideCanvas = true;



        canvasStartX = CanvasRenderer2D.toCanvasX(startEndPoints.get(0).getElement(0));
        canvasStartY = CanvasRenderer2D.toCanvasY(startEndPoints.get(0).getElement(1));
        canvasEndX = CanvasRenderer2D.toCanvasX(startEndPoints.get(1).getElement(0));
        canvasEndY = CanvasRenderer2D.toCanvasY(startEndPoints.get(1).getElement(1));

        //System.out.println("x1: " + canvasStartX + ", y1: " + canvasStartY + ", x2: " + canvasEndX + ", y2: " + canvasEndY);
    }

    private double getA(){
        return direction.getY() / direction.getX();
    }

    private double getB(){
        return start.getY() - getA()*start.getX();
    }

    private double getYFromX(double x){
        return getA() * x + getB();
    }

    private double getXFromY(double y){
        return (y - getB()) / getA();
    }

    @Override
    public String toString(){
        return "Start: " + start + ", Direction: " + direction;
    }
}
