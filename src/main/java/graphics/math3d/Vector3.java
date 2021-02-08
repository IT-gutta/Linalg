package graphics.math3d;

import graphics.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Matrix;
import math.Vector;
import math.Vectors;

public class Vector3 extends Renderer3D implements Interpolatable {
    private Vector vector;
    private Interpolator interpolator;

    public Vector3(double x, double y, double z){
        this.vector = new Vector(x, y, z);
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {
        /*double[] vec = vector.getVector();
        double[] endPos = m.transform(vec);
        double startAngle = Math.atan2(vec[1], vec[0]);
        double endAngle = startAngle + Vectors.angle2(vec, endPos);
        double startLength = Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
        double endLength = Math.sqrt(Math.pow(endPos[0], 2) + Math.pow(endPos[1], 2));
        interpolator = new Interpolator(millis, new double[]{startLength, startAngle}, new double[]{endLength, endAngle});*/
    }

    @Override
    public void handleInterpolation() {

        if(interpolator != null){
            interpolator.handle();
            /*
            setX(interpolator.get(0) * Math.cos(interpolator.get(1)));
            setY(interpolator.get(0) * Math.sin(interpolator.get(1)));
            setZ(interpolator.get(0) * Math.sin(interpolator.get(1)));*/
            if(interpolator.isFinished())
                interpolator = null;
        }
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        gc.setFill(paint);
        gc.fillOval(CanvasRenderer3D.getCanvasWidth() / 2, CanvasRenderer3D.getCanvasHeight() / 2, 100, 100);
    }


    @Override
    public String toString(){
        return vector.toString();
    }

    public double getX(){
        return vector.getElement(0);
    }
    public double getY(){
        return vector.getElement(1);
    }
    public double getZ(){
        return vector.getElement(2);
    }
    public void setX(double x){
        vector.setElement(0, x);
    }
    public void setY(double y){
        vector.setElement(1, y);
    }
    public void setZ(double z){
        vector.setElement(2, z);
    }
}
