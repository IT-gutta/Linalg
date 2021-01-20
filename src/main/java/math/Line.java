package math;

import graphics.CoordinateSystem;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class Line implements Renderable {
    private Point start;
    private Point end;
    private Vector direction;

    public Line(Point p, Vector v){
        start = p;
        direction = v;
        calculatAbsoluteEndpoint();
    }
    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
//        direction = Points.toVector(Points.subtract(end, start));
    }

    private boolean isParallel(Line l){
        return true;
    }

    public void transform(Matrix m){
        start = start.transform(m);
        direction.applyTransformation(m);
    }

    public Point getPoint(double parameter){
        return new Point(start.getElement(0) + parameter * direction.getElement(0), start.getElement(1) + parameter * direction.getElement(1));
    }



    public Point getAbsoluteStart(){
        return CoordinateSystem.toCanvasPoint(start);
    }
    public Point getAbsoluteEnd(){
        return CoordinateSystem.toCanvasPoint(end);
    }

    private void calculatAbsoluteEndpoint(){
        end = getPoint(20);
//        int t = 1;
//        while(true){
//            end = getPoint(t);
//            if(CoordinateSystem.insideCanvas(end))
//                break;
//            t+=100;
//        }
    }

    public Point getStart(){
        return start;
    }

    public Point getEnd(){
            return end;
    }

    @Override
    public String toString(){
        return "Start: " + start + ", End: " + end + ", Direction: " + direction;
    }

    @Override
    public void render(GraphicsContext gc){
        //end = getPoint(-100);
        //start = getPoint(100);
        gc.strokeLine(getAbsoluteStart().getElement(0), getAbsoluteStart().getElement(1), getAbsoluteEnd().getElement(0), getAbsoluteEnd().getElement(1));
    }
}
