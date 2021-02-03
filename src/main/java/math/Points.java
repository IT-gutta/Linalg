package math;

public class Points {
    //vær forsiktig her med entanglement!!
    public static Vector toVector(Point p){
        return new Vector(p.getPoint());
    }
    public static Point fromVector(Vector v){
        return new Point(v.getVector());
    }

    public static Point subtract(Point p1, Point p2){
        return add(p1, scale(p2, -1));
    }

    public static Point transform(Point p, Matrix matrix){
        return matrix.transform(p.toVector()).toPoint();
    }

    public static Point add(Point p1, Point p2){
        return fromVector(Vectors.add(toVector(p1), toVector(p2)));
    }


    public static Point scale(Point point, double s) {
        Vector newVector = toVector(point);
        newVector.scale(s);
        return fromVector(newVector);
    }
}
