package math;

public class Points {
    public Vector toVector(Point p){
        return new Vector(p.getPoint());
    }
    public Point fromVector(Vector v){
        return new Point(v.getVector());
    }
}
