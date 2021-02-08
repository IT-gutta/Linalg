package math;

public class Point implements Transformable {
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
    public void setElement(int i, double d){
        point[i] = d;
    }


    public int getDimensions(){
        return point.length;
    }

    @Override
    public String toString(){
        String s = "";
        for(double element:point){
            s+=Double.toString(element)+", ";
        }
        return "("+s.substring(0,s.length()-2)+")";
    }

    public Vector toVector(){
        return Vectors.fromPoint(this);
    }


    @Override
    public void transform(Matrix m){
        //skriv kode her
    }
}
