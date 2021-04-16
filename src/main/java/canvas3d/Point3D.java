package canvas3d;

import math.Points;
import math3d.Point3;
import math3d.Vector3;

/**
 * Simple point which renders a low poly sphere mesh
 */
public class Point3D extends Mesh{
    private Vector3 point;
    private static final double scale = 0.1;
    public Point3D(double x, double y, double z){
        super("lowpoly_sphere.obj", Vector3.ZERO(), scale);
        this.point = new Vector3(x, y, z);
        setPosition(this.point);
    }
    public Point3D(Point3 p){
        super("lowpoly_sphere.obj", Vector3.ZERO(), scale);
        this.point = p.toVector().toVector3();
        setPosition(this.point);
    }

    @Override
    public String toString(){
        return Points.fromVector(point).toString();
    }
}
