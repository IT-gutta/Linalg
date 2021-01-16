package math;

public class Line {
    private double[] point;
    private double[] vector;
    public Line(Point p, Vector v){
        point = p.getPoint();
        vector = v.getVector();
    }
    private boolean isParallel(Line l){
        return true;
    }
}
