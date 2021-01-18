package math;

import exceptions.RenderException;
import graphics.CoordinateSystem;
import javafx.scene.canvas.GraphicsContext;

public class Line {
    private double[] point;
    private double[] vector;
    public Line(Point p, Vector v){
        point = p.getPoint();
        vector = v.getVector();
    }
    private boolean isParallel(Line l){
        return true;
    }

    public void transform(Matrix m){
        point = Vectors.fromPoint(new Point(point)).transform(m).getVector();
        vector = new Vector(vector).transform(m).getVector();
    }

    public Point getPoint(double parameter){
        return new Point(point[0]+parameter*vector[0],point[1]+parameter*vector[1]);
    }

    public void render(GraphicsContext gc){
        Point[] points = getEndPoints( CoordinateSystem.getCanvasWidth(), CoordinateSystem.getCanvasHeight(), CoordinateSystem.getUnitSize());
        gc.strokeLine(points[0].getElement(0), points[0].getElement(1), points[1].getElement(0), points[1].getElement(1));
    }

    private Point[] getEndPoints(double canvasW, double canvasH, int unitSize){
        Point[] points = new Point[2];
        Point p; Point q;
        int t = 0;
        while(true){
            t++;
            p = CoordinateSystem.toCanvasPoint(getPoint(t)); q = CoordinateSystem.toCanvasPoint(getPoint(-t));
            if((p.getElement(0)<0 || p.getElement(0)>canvasW) && (p.getElement(1)<0 || p.getElement(1)>canvasH)){
                if((q.getElement(0)<0 || q.getElement(0)>canvasW) && (q.getElement(1)<0 || q.getElement(1)>canvasH)){
                    points[0] = p; points[1] = q;
                    return points;
                }
            }
        }
    }
}
