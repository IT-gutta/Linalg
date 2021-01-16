package math;

public class Point {
    private double[] point;
    public Point(double... args){
        point = args;
    }
    public double[] getPoint(){
        return point;
    }
    public double getElement(int i){
        return point[i];
    }
}
