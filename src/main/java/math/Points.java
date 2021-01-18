package math;

public class Points {
    public static Vector toVector(Point p){
        return new Vector(p.getPoint());
    }
    public static Point fromVector(Vector v){
        return new Point(v.getVector());
    }
}
