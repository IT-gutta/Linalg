package math3d;

import canvas3d.GraphicsContext3D;
import canvas3d.Renderer3D;
import graphics.*;
import javafx.scene.paint.Paint;
import math.Matrix;

public class Vector3Renderer extends Renderer3D implements Interpolatable {
    private Interpolator interpolator;
    private double theta1 = 0;
    private double theta2 = 0;
    private Vector3 vector3;


    public Vector3Renderer(double x, double y, double z){
        this.vector3 = new Vector3(x, y, z);
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
    public Object getMath() {
        return vector3;
    }

    @Override
    public void render(GraphicsContext3D gc, String name, Paint paint){
        theta1 += 0.005;
        theta2 += 0.01;
        /*Vector3 vec = Vector3.rotateZ(math, theta1);
        vec = Vector3.rotateX(vec, theta2);*/
        gc.setFill(paint);
        gc.strokeLine(0, 0, 0, vector3.getX(), vector3.getY(), vector3.getZ());
    }


    @Override
    public String toString(){
        return vector3.toString();
    }
}
