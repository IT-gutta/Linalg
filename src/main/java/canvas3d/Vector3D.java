package canvas3d;

import graphics.Interpolator;
import math.Matrix;
import math3d.Vector3;

public class Vector3D extends Mesh {
    private Interpolator interpolator;
    private Vector3 vector3;


    public Vector3D(double x, double y, double z){
        super("vectorfix.obj", Vector3.ZERO(), 1);
        vector3 = new Vector3(x, y, z);
        setScale(vector3.getMagnitude());
        setForward(vector3);
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {
        double[] start = vector3.getVector();
        double[] end = m.transform(vector3.getVector());
        interpolator = new Interpolator(millis, start, end);
        System.out.println("started interpolatoin");
    }

    @Override
    public void handleInterpolation() {

        if(interpolator != null){
            interpolator.handle();
            vector3.setX(interpolator.get(0));
            vector3.setY(interpolator.get(1));
            vector3.setZ(interpolator.get(2));

            setForward(vector3);

            if(interpolator.isFinished())
                interpolator = null;
        }
    }

    @Override
    public void beforeRender() {
        handleInterpolation();
    }

    @Override
    public void render(GraphicsContext3D gc){
        beforeRender();

        for(Triangle triangle : triangles){
            triangle.render(gc, position, forward, up, right);
        }
        /*gc.strokeLine(0, 0, 0, forward.getX(), forward.getY(), forward.getZ());
        gc.strokeLine(0, 0, 0, right.getX(), right.getY(), right.getZ());
        gc.strokeLine(0, 0, 0, up.getX(), up.getY(), up.getZ());*/

        gc.fillText(name, Vector3.add(vector3, Vector3.scale(forward, 0.1)));
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
