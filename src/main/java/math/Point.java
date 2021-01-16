package math;

public class Point {
    private double[] point;
    public Point(double... args){
        point = args;
    }
    double[] getList(){
        return point;
    }
    double getElement(int i){
        return point[i];
    }
}
