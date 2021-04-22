package org.canvas3d;

import org.math.Editable;
import org.math.Points;
import org.math.Vector;
import org.math.Vectors;
import org.math3d.Vector3;

/**
 * Simple point which renders a low poly sphere mesh
 */
public class Point3D extends Mesh implements Editable {
    private final Vector3 point3;
    private static final double scale = 0.1;
    public Point3D(double x, double y, double z){
        super("lowpoly_sphere.obj", Vector3.ZERO(), scale);
        this.point3 = new Vector3(x, y, z);
        setPosition(this.point3);
    }
    public Point3D(Vector3 p){
        super("lowpoly_sphere.obj", Vector3.ZERO(), scale);
        this.point3 = p;
        setPosition(this.point3);
    }

    @Override
    public Object getMath() {
        return Vectors.toPoint(point3).toPoint3();
    }

    @Override
    public String toString(){
        return Points.fromVector(point3).toString();
    }

    @Override
    public String writeString(){
        return "org.canvas3d.Point3D---"+ point3;
    }
    //from file
    public Point3D(String fileString){
        this(Vector3.valueOf(fileString));
    }

    @Override
    public double[] getCopy() {
        return point3.getCopy();
    }

    @Override
    public void set(double[] doubles) {
        point3.set(doubles);
        setPosition(point3);
    }
}
