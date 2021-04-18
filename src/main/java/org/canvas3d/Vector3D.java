package org.canvas3d;

import org.graphics.Interpolator;
import org.math.Matrix;
import org.math3d.Vector3;
/**
 * A 3D vector which is rendered to the canvas as a Mesh which looks like an arrow, and is scaled according to the
 * magnitude of the vector
 */
public class Vector3D extends Mesh {
    private Interpolator interpolator;
    private Vector3 vector3;


    public Vector3D(double x, double y, double z){
        super("vectorfix.obj", Vector3.ZERO(), 1);
        vector3 = new Vector3(x, y, z);
        setScale(vector3.getMagnitude()/2);
        setForward(vector3);
    }

    public Vector3D(Vector3 v){
        super("vectorfix.obj", Vector3.ZERO(), 1);
        this.vector3 = v;
        setScale(vector3.getMagnitude()/2);
        setForward(vector3);
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {
        double[] start = vector3.getVector();
        double[] end = m.transform(vector3.getVector());
        interpolator = new Interpolator(millis, start, end);
    }

    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();
            vector3.setX(interpolator.get(0));
            vector3.setY(interpolator.get(1));
            vector3.setZ(interpolator.get(2));

            setScale(vector3.getMagnitude()/2);
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
