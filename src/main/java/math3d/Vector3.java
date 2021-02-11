package math3d;

import canvas3d.CanvasRenderer3D;
import canvas3d.GraphicsContext3D;
import canvas3d.Renderer3D;
import graphics.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Matrix;
import math.Vector;

public class Vector3 extends Renderer3D<Vector> implements Interpolatable {
    private Interpolator interpolator;

    public Vector3(double x, double y, double z){
        super(new Vector(x, y, z));
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
    public void render(GraphicsContext3D gc, String name, Paint paint){
        gc.setFill(paint);
        gc.strokeLine(0, 0, 0, 1, 1, 1);
    }


    @Override
    public String toString(){
        return math.toString();
    }

    public double getX(){
        return math.getElement(0);
    }
    public double getY(){
        return math.getElement(1);
    }
    public double getZ(){
        return math.getElement(2);
    }
    public void setX(double x){
        math.setElement(0, x);
    }
    public void setY(double y){
        math.setElement(1, y);
    }
    public void setZ(double z){
        math.setElement(2, z);
    }
}
