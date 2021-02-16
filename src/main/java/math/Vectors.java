package math;

import graphics.VariableContainer;

import java.util.stream.Stream;

public class Vectors {
    public static Vector add(Vector... args) throws IllegalArgumentException{
        int d = args[0].getDimensions();
        double[] u = new double[d];
        for(Vector v:args){
            if(d!=v.getDimensions())
                throw new IllegalArgumentException("Vectors must have same dimensions");
            for(int i = 0; i<d; i++){
                u[i]+=v.getElement(i);
            }
        }
        return new Vector(u);
    }

    public static Vector subtract(Vector v1, Vector v2) throws IllegalArgumentException{
        if(v1.getDimensions() != v2.getDimensions())
            throw new IllegalArgumentException("Vectors must have same dimensions");

        double[] u = new double[v1.getDimensions()];

        for(int i = 0; i<u.length; i++){
            u[i] = v1.getElement(i) - v2.getElement(i);
        }
        return new Vector(u);
    }

    public static double dot(Vector v, Vector u) throws IllegalArgumentException{
        if(u.getDimensions()!=v.getDimensions()) throw new IllegalArgumentException("Vectors must have the same dimensions");
        double dot = 0;
        for(int i = 0; i< u.getDimensions(); i++){
            dot+=(u.getElement(i)*v.getElement(i));
        }
        return dot;
    }

    public static double angle(Vector v, Vector u){
        return Math.acos(u.dot(v)/(u.getMagnitude()*v.getMagnitude()));
    }
    public static double angle2(double[] v1, double[] v2){
        return Math.atan2(v1[0]*v2[1]-v1[1]*v2[0],v1[0]*v2[0]+v1[1]*v2[1]);
    }

    public static Vector scale(Vector v, double s){
        double[] d = new double[v.getDimensions()];
        for(int i = 0; i<v.getDimensions(); i++){
            d[i] = v.getElement(i)*s;
        }
        return new Vector(d);
    }

    public static Vector transform(Vector v, Matrix matrix){
        return matrix.transform(v);
    }

    public static double getMagnitude(Vector v){
        double ans = 0;
        for(double element:v.getVector()){
            ans+=Math.pow(element,2);
        }
        return Math.sqrt(ans);
    }

    public static Vector fromPoint(Point p){
        return new Vector(p.getPoint());
    }

    public static Vector fromPoints(Point p, Point q) throws IllegalArgumentException{
        if(p.getDimensions()!=q.getDimensions()) throw new IllegalArgumentException("Illegal number of dimensions");
        double[] d = new double[p.getDimensions()];
        for(int i = 0; i<p.getDimensions(); i++){
            d[i] = p.getElement(i)-q.getElement(i);
        }
        return new Vector(d);
    }

    public static Point toPoint(Vector v){
        return new Point(v.getVector());
    }

    public static Complex toComplex(Vector v) throws IllegalArgumentException{
        if(v.getDimensions()!=2)
            throw new IllegalArgumentException("Vector must be two dimensional");
        return new Complex(v.getElement(0),v.getElement(1));
    }

    public static Vector fromComplex(Complex c){
        return new Vector(c.getRe(), c.getIm());
    }

    public static Vector parseVector(String... args){
        return new Vector(Stream.of(args).mapToDouble(Double::parseDouble).toArray());
    }

    public static VariableContainer<Vector> of(String name, double... args){
        return new VariableContainer<Vector>(new Vector(args), name);
    }
}
