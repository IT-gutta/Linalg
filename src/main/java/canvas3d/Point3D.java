package canvas3d;

import math3d.Point3;
import math3d.Vector3;

public class Point3D extends Mesh{
    private Vector3 point;
    private static final double scale = 0.1;
    public Point3D(double x, double y, double z){
        super("lowpoly_sphere.obj", Vector3.ZERO(), scale);
        this.point = new Vector3(x, y, z);
        setPosition(this.point);
    }
}
