package math2d;

import graphics.CanvasRenderer2D;
import graphics.Interpolatable;
import graphics.Interpolator;
import graphics.Renderer2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Matrix;
import math.Vector;
import math.Vectors;

public class Vector2 extends Renderer2D implements Interpolatable {
    private Vector vector;
    private Interpolator interpolator;

    public Vector2(Vector vector){
        this.vector = vector;
    }

    public Vector2(double x, double y){
        this.vector = new Vector(x, y);
    }

    public double getX(){
        return vector.getElement(0);
    }
    public double getY(){
        return vector.getElement(1);
    }
    public void setX(double x){
        vector.setElement(0, x);
    }
    public void setY(double y){
        vector.setElement(1, y);
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        //linear interpolation
        handleInterpolation();

        gc.setFill(paint);
        gc.setStroke(paint);

        Vector distance = Vectors.scale(vector, 1/vector.getMagnitude()/3);
        gc.fillText(name, CanvasRenderer2D.toCanvasX(vector.getElement(0)+distance.getElement(0)), CanvasRenderer2D.toCanvasY(vector.getElement(1)+distance.getElement(1)));


        gc.setLineWidth(1.5);
        gc.strokeLine(CanvasRenderer2D.toCanvasX(0), CanvasRenderer2D.toCanvasY(0), CanvasRenderer2D.toCanvasX(vector.getElement(0)), CanvasRenderer2D.toCanvasY(vector.getElement(1)));


        //fill arrow
        double angle = Math.atan2(vector.getElement(1), vector.getElement(0));

        double arrowTipLength = 12;
        double startX = CanvasRenderer2D.toCanvasX(vector.getElement(0)) - arrowTipLength * Math.cos(angle); //move back so tip can be at exact location
        double startY = CanvasRenderer2D.toCanvasY(vector.getElement(1)) + arrowTipLength * Math.sin(angle); //move back so tip can be at exact location

        double arrowSideLength = 7;
        double[] xCoords = {
                CanvasRenderer2D.toCanvasX(vector.getElement(0)), //tipX
                startX + arrowSideLength * Math.sin(angle), //rightX
                startX - arrowSideLength * Math.sin(angle) //leftX
        };

        double[] yCoords = {
                CanvasRenderer2D.toCanvasY(vector.getElement(1)), //tipY
                startY + arrowSideLength * Math.cos(angle), //rightY
                startY - arrowSideLength * Math.cos(angle), //leftY
        };

        gc.fillPolygon(xCoords, yCoords, 3);
    }

    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();
            //0 is the length, and 1 is the angle
            setX(interpolator.get(0) * Math.cos(interpolator.get(1)));
            setY(interpolator.get(0) * Math.sin(interpolator.get(1)));
            if(interpolator.isFinished())
                interpolator = null;
        }
    }

    @Override
    public String toString(){
        return vector.toString();
    }

    @Override
    public void startInterpolation(Matrix m, int millis){
        double[] vec = vector.getVector();
        double[] endPos = m.transform(vec);
        double startAngle = Math.atan2(vec[1], vec[0]);
        double endAngle = startAngle + Vectors.angle2(vec, endPos);
        double startLength = Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
        double endLength = Math.sqrt(Math.pow(endPos[0], 2) + Math.pow(endPos[1], 2));
        interpolator = new Interpolator(millis, new double[]{startLength, startAngle}, new double[]{endLength, endAngle});
    }
}
