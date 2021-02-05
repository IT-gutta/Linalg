package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class Line implements Renderable, Transformable {
    //TODO fix toString
    private Point start;
    private Vector direction;
    private boolean isHidden = false;
    private boolean isInsideCanvas;

    private double canvasStartX = 0;
    private double canvasStartY = 0;
    private double canvasEndX = 0;
    private double canvasEndY = 0;


    //lerping
    private double[] lerpStartPoint;
    private double[] lerpEndPoint;
    private double[] lerpStartDirection;
    private double[] lerpEndDirection;
    private float lerpProgress = 1;
    private float lerpAngle;
    private int lerpMillis;

    public Line(Point p, Vector v){
        start = p;
        direction = v;
        updateCanvasPoints();
    }
    public Line(double pointX, double pointY, double directionX, double directionY){
        start = new Point(pointX, pointY);
        direction = new Vector(directionX, directionY);
        updateCanvasPoints();
    }


//    private boolean isParallel(Line l){
//        return true;
//    }


    public Point intersection(Line l){
        double a = start.getElement(0);double c = start.getElement(1);double e = l.getStart().getElement(0);double g = l.getStart().getElement(1);
        double b = direction.getElement(0);double d = direction.getElement(1); double f = l.getDirection().getElement(0); double h = l.getDirection().getElement(1);
        double t = (-a*h+c*f+e*h-f*g)/(b*h-d*f);
        return getPoint(t);
    }

    public Point getPoint(double parameter){
        return new Point(start.getElement(0) + parameter * direction.getElement(0), start.getElement(1) + parameter * direction.getElement(1));
    }

    public Vector getDirection(){
        return direction;
    }

    public Point getStart(){
        return start;
    }

    private double getA(){
        return direction.getElement(1) / direction.getElement(0);
    }

    private double getB(){
        return start.getElement(1) - getA()*start.getElement(0);
    }

    private double getYFromX(double x){
        return getA() * x + getB();
    }

    private double getXFromY(double y){
        return (y - getB()) / getA();
    }

    public void updateCanvasPoints(){
        if(Math.abs(direction.getElement(0)) <= 0.001 / CanvasRenderer.unitSize){
            //vertical linje
            isInsideCanvas = true;
            if(start.getElement(0) <= CanvasRenderer.fromCanvasX(CanvasRenderer.getCanvasWidth()) && start.getElement(0) >= CanvasRenderer.fromCanvasX(0))
                isInsideCanvas = true;
            canvasStartX = CanvasRenderer.toCanvasX(getStart().getElement(0));
            canvasEndX = CanvasRenderer.toCanvasX(getStart().getElement(0));
            canvasStartY = 0;
            canvasEndY = CanvasRenderer.getCanvasHeight();
            return;
        }
        //calculate intersection with canvas
        List<Point> startEndPoints = new ArrayList<>();


        //x coords from origin for the vertical canvas borders
        double l1 = CanvasRenderer.fromCanvasX(0);
        double l3 = CanvasRenderer.fromCanvasX(CanvasRenderer.getCanvasWidth());

        //y coords from origin for the horizontal canvas borders
        double l2 = CanvasRenderer.fromCanvasY(0);
        double l4 = CanvasRenderer.fromCanvasY(CanvasRenderer.getCanvasHeight());

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



        canvasStartX = CanvasRenderer.toCanvasX(startEndPoints.get(0).getElement(0));
        canvasStartY = CanvasRenderer.toCanvasY(startEndPoints.get(0).getElement(1));
        canvasEndX = CanvasRenderer.toCanvasX(startEndPoints.get(1).getElement(0));
        canvasEndY = CanvasRenderer.toCanvasY(startEndPoints.get(1).getElement(1));

        //System.out.println("x1: " + canvasStartX + ", y1: " + canvasStartY + ", x2: " + canvasEndX + ", y2: " + canvasEndY);
    }


    @Override
    public String toString(){
        return "Start: " + start + ", Direction: " + direction;
    }

    @Override
    public void render(GraphicsContext gc){
        //linear interpolation of transformation
        handleLerp();


        //System.out.println(isInsideCanvas);
        if(!isInsideCanvas)
            return;

        gc.setStroke(Paint.valueOf("black"));
        gc.setLineWidth(1);
        gc.strokeLine(canvasStartX, canvasStartY, canvasEndX, canvasEndY);
    }

    @Override
    public void transform(Matrix m){
        transform(m, 1000);
    }

    public void transform(Matrix m, int millis){
        start.transform(m, millis);
        direction.transform(m, millis);
    }


    private void handleLerp() {
        //lerping
        direction.handleLerp();
        start.handleLerp();
        updateCanvasPoints();
    }

    @Override
    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public void show() {
        isHidden = false;
    }

    @Override
    public void hide() {
        isHidden = true;
    }


    public static void main(String[] args) {
        Line line1 = new Line(new Point(10, 2), new Vector(1, 0));
        Line line2 = new Line(new Point(-7,3), new Vector(0,1));
        System.out.println(line1.intersection(line2));
    }
}
