package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.Function;

public class Line implements Renderable {
    private Point start;
    private Point end;
    private Vector direction;
    private boolean isHidden = false;

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


    private double getEdgeParameter(){
        return Math.sqrt(Math.pow(CanvasRenderer.getCanvasWidth(), 2) + Math.pow(CanvasRenderer.getCanvasHeight(), 2)) / (2 * direction.getMagnitude());
    }
    public Point getAbsoluteStart(){
        return CanvasRenderer.toCanvasPoint(getPoint(getEdgeParameter()));
    }
    public Point getAbsoluteEnd(){
        return CanvasRenderer.toCanvasPoint(getPoint(- getEdgeParameter()));
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
        if(isHidden)
            return;
        gc.strokeLine(getAbsoluteStart().getElement(0), getAbsoluteStart().getElement(1), getAbsoluteEnd().getElement(0), getAbsoluteEnd().getElement(1));
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
