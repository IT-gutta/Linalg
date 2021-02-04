package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

public class LineSegment implements Renderable, Transformable {
    private Point start;
    private Point end;

    private boolean isHidden = false;

    //lerping
    private double[] firstStart;
    private double[] firstEnd;
    private double[] otherStart;
    private double[] otherEnd;
    private float lerpProgress = 1;
    private float lerpAngle;
    private int lerpMillis = 2500;

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
        if(lerpProgress < 1)
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
        firstStart = start.getPoint();
        firstEnd = m.transform(start.getPoint());
        otherStart = end.getPoint();
        otherEnd = m.transform(end.getPoint());
        lerpProgress = 0f;
        lerpAngle = 0f;
    }

    public void transform(Matrix m, int millis){
        lerpMillis = millis;
        transform(m);
    }

    private void handleLerp(){
        lerpAngle += Math.PI/2 / lerpMillis * CanvasRenderer.deltaTime;
        lerpProgress = (float) Math.sin(lerpAngle);

        if(lerpAngle >= Math.PI/2) {
            //fix the line in the ending state
            lerpProgress = 1f;
            start.setElement(0, firstEnd[0]);
            start.setElement(1, firstEnd[1]);
            end.setElement(0, otherEnd[0]);
            end.setElement(1, otherEnd[1]);
        }

        start.setElement(0, firstStart[0] + lerpProgress * (firstEnd[0] - firstStart[0]));
        start.setElement(1, firstStart[1] + lerpProgress * (firstEnd[1] - firstStart[1]));

        end.setElement(0, otherStart[0] + lerpProgress * (otherEnd[0] - otherStart[0]));
        end.setElement(1, otherStart[1] + lerpProgress * (otherEnd[1] - otherStart[1]));
    }
}
