package canvas3d;

import graphics.Interpolatable;
import graphics.Interpolator;
import javafx.scene.paint.Color;
import math.Matrix;
import math3d.Vector3;

public class Vector3D extends Mesh implements Interpolatable {
    private Interpolator interpolator;
    private Vector3 vector3;


    public Vector3D(double x, double y, double z){
        super("vector.obj", Vector3.ZERO(), 1);
        vector3 = new Vector3(x, y, z);
        scale(vector3.getMagnitude());

        for(Triangle triangle : triangles)
            triangle.setInterpolateColors(false);
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
    public void update(String name, Color color) {
        for(Triangle triangle : triangles)
            triangle.setColor(color);
    }

    @Override
    public Object getMath() {
        return vector3;
    }

    @Override
    public String toString(){
        return vector3.toString();
    }
}
