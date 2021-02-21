package math3d;

import java.io.Serializable;

public class Plane implements Serializable {
    private Vector3 point;
    private Vector3 normal;
    public Plane(Vector3 point, Vector3 normal){
        this.point = point;
        this.normal = normal;
    }

    public Plane(Vector3 p1, Vector3 p2, Vector3 p3){
        Vector3 normal = Vector3.cross(Vector3.subtract(p2, p1), Vector3.subtract(p3, p1));
        this.point = p1;
        this.normal = normal;
    }

    public Vector3 lineIntersection(Line3 line){
        //TODO fix line intersection with plane for clipping
        return null;
    }
}
