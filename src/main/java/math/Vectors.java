package math;

import exceptions.IllegalNumberOfDimensionsException;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vectors {
    public static Vector fromPoint(Point p){
        return new Vector(p.getPoint());
    }
    public static Point toPoint(Vector v){
        return new Point(v.getVector());
    }
    public static Vector add(Vector... args) throws IllegalNumberOfDimensionsException{
        int d = args[0].getDimensions();
        double[] u = new double[d];
        for(Vector v:args){
            if(d!=v.getDimensions())
                throw new IllegalNumberOfDimensionsException("Vectors must have same dimensions");
            for(int i = 0; i<d; i++){
                u[i]+=v.getElement(i);
            }
        }
        return new Vector(u);
    }
    public static Complex toComplex(Vector v) throws IllegalNumberOfDimensionsException{
        if(v.getDimensions()!=2)
            throw new IllegalNumberOfDimensionsException("Vector must be two dimensional");
        return new Complex(v.getElement(0),v.getElement(1));
    }
    public static Vector fromComplex(Complex c){
        return new Vector(c.getRe(), c.getIm());
    }


    public static Vector parseVector(String string){
        return new Vector(Arrays.stream(string.replaceAll("^[,.0-9]", "").split(",")).mapToDouble(d -> Double.valueOf(d)).toArray());
    }
}
