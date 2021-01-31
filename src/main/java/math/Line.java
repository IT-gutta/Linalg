package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Line implements Renderable {
    private Point start;
    private Point end;
    private Vector direction;
    private boolean isHidden = false;
    private boolean isInsideCanvas;

    private double canvasStartX = 0;
    private double canvasStartY = 0;
    private double canvasEndX = 0;
    private double canvasEndY = 0;

    public Line(Point p, Vector v){
        start = p;
        direction = v;
        updateCanvasPoints();
    }


    public Line(Point start, Point end){

        this.start = start;
        this.end = end;
        direction = Vectors.fromPoints(start,end);
        updateCanvasPoints();
    }

//    private boolean isParallel(Line l){
//        return true;
//    }

    public void transform(Matrix m){
        start = start.transform(m);
        direction.transform(m);
    }

    public Point getPoint(double parameter){
        return new Point(start.getElement(0) + parameter * direction.getElement(0), start.getElement(1) + parameter * direction.getElement(1));
    }



    public Point getStart(){
        return start;
    }

    public Point getEnd(){
            return end;
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
        if(direction.getElement(0) <= 0.0001){
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
        return "Start: " + start + ", End: " + end + ", Direction: " + direction;
    }

    @Override
    public void render(GraphicsContext gc){
        //System.out.println(isInsideCanvas);
        if(isHidden || !isInsideCanvas)
            return;

        gc.strokeLine(canvasStartX, canvasStartY, canvasEndX, canvasEndY);
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
        Line line = new Line(new Point(10, 2), new Vector(2, 2));
        System.out.println(line.getYFromX(10));
    }
}
