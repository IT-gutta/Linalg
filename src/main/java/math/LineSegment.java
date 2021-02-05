package math;

import graphics.CanvasRenderer;
import graphics.Lerper;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class LineSegment implements Renderable, Transformable {
    private Point start;
    private Point end;

    private boolean isHidden = false;
    private Lerper lerper;

    public LineSegment(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    public LineSegment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }


    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setLineWidth(1);
        //linear interpolation for the points
        handleLerp();

        gc.strokeLine(CanvasRenderer.toCanvasX(start.getElement(0)), CanvasRenderer.toCanvasY(start.getElement(1)), CanvasRenderer.toCanvasX(end.getElement(0)), CanvasRenderer.toCanvasY(end.getElement(1)));
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

    @Override
    public void transform(Matrix m){
        transform(m, 1000);
    }

    public void transform(Matrix m, int millis){
        start.transform(m, millis);
        end.transform(m, millis);
    }

    public void handleLerp(){
        start.handleLerp();
        end.handleLerp();
    }

    @Override
    public String toString(){
        return "Start: " + start.toString() + "\tEnd: " + end.toString();
    }
}
