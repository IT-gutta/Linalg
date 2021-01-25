package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Line implements Renderable {
    private Point start;
    private Point end;
    private Vector direction;
    private boolean isHidden = false;

    private double canvasStartX = 0;
    private double canvasStartY = 0;
    private double canvasEndX = 0;
    private double canvasEndY = 0;

    public Line(Point p, Vector v){
        start = p;
        direction = v;
    }


    public Line(Point start, Point end){

        this.start = start;
        this.end = end;
        direction = Vectors.fromPoints(start,end);
    }

    private boolean isParallel(Line l){
        return true;
    }

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
        //calculate intersection with canvas
        List<Point> startEndPoints = new ArrayList<>();

        double u = CanvasRenderer.getUnitSize();
        double w = CanvasRenderer.getCanvasWidth();
        double h = CanvasRenderer.getCanvasHeight();
        //offset
        double offX = CanvasRenderer.getOffsetX();
        double offY = CanvasRenderer.getOffsetY();

        //x for canvas vertical lines
        double l1 = -w/(2*u) - offX/u;
        double l3 = w/(2*u) - offX/u;

        //y for canvas horizontal lines
        double l2 = -h/(2*u) - offY/u;
        double l4 = h/(2*u) - offY/u;

        //intersect left vertical
        double y1 = getYFromX(l1);

        if(y1 <= l4 && y1 >= l2) //intersection!
            startEndPoints.add(new Point(l1, y1));


        //intersect right vertical
        double y3 = getYFromX(l3);
        if(y3 <= l4 && y3 >= l2) //intersection!
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
            return;
        }


        canvasStartX = CanvasRenderer.toCanvasX(startEndPoints.get(0).getElement(0));
        canvasStartY = CanvasRenderer.toCanvasY(startEndPoints.get(0).getElement(1));
        canvasEndX = CanvasRenderer.toCanvasX(startEndPoints.get(1).getElement(0));
        canvasEndY = CanvasRenderer.toCanvasY(startEndPoints.get(1).getElement(1));
    }


    @Override
    public String toString(){
        return "Start: " + start + ", End: " + end + ", Direction: " + direction;
    }

    @Override
    public void render(GraphicsContext gc){
        if(isHidden)
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
}
