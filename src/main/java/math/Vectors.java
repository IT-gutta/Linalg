package math;

public class Vectors {
    public Vector fromPoint(Point p){
        return new Vector(p.getPoint());
    }
    public Point toPoint(Vector v){
        return new Point(v.getVector());
    }
}
