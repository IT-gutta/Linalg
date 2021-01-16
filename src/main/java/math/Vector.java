package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector {
    private double[] vector;
    public static void main(String[] args) {
        Vector v1 = new Vector(1,2,3);
        v1.setMagnitude(1);
        System.out.println(v1.toString());
    }

    public Vector(double... args){
        vector = args;
    }

    public String toString(){
        String s = "";
        for(double element:vector){
            s+=Double.toString(element)+", ";
        }
        return "("+s.substring(0,s.length()-2)+")";
    }

    public double getElement(int index){
        return vector[index];
    }

    public void setElement(int index, double value){
        vector[index] = value;
    }

    public double getMagnitude(){
        double ans = 0;
        for(double element:vector){
            ans+=Math.pow(element,2);
        }
        return Math.sqrt(ans);
    }

    public void setMagnitude(double m){
        double sum = 0;
        for(double element:vector){
            sum+=Math.pow(element,2);
        }
        double scale = Math.sqrt(m/sum);
        for(int i = 0; i<vector.length; i++){
            vector[i]*=scale;
        }
    }

    public int getDimensions(){
        return vector.length;
    }

    public void scale(double s){
        for(int i = 0; i<vector.length; i++){
            vector[i]*=s;
        }
    }

    public void add(Vector v) throws IllegalArgumentException{
        if(v.getDimensions()!=this.getDimensions())
            throw new IllegalArgumentException("The number of dimensions must be 2");
        else{
            for(int i = 0; i<vector.length; i++){
                vector[i]+=v.getElement(i);
            }
        }
    }

    public int factorize() throws IllegalArgumentException{
        for(double element:vector){
            if(!Utils.isWhole(element)) throw new IllegalArgumentException("Vector must contain integers");
        }
        int gcd = Utils.gcd((int)vector[0],(int)vector[1]);
        for(int i = 2; i< vector.length; i++){
            gcd = Utils.gcd(gcd, (int)vector[i]);
        }
        return gcd;
    }

    public double dot(Vector v) throws IllegalArgumentException{
        if(this.getDimensions()!=v.getDimensions()) throw new IllegalArgumentException("Vectors must have the same dimensions");
        double dot = 0;
        for(int i = 0; i< vector.length; i++){
            dot+=(vector[i]*v.getElement(i));
        }
        return dot;
    }

    public double cross(Vector v){
        return 0;
    }

    public void addDimensions(double... args){
        double[] v = new double[this.getDimensions()+args.length];
        for(int i = 0; i<this.getDimensions(); i++){
            v[i] = vector[i];
        }
        for(int i = 0; i<args.length; i++){
            v[i+this.getDimensions()] = args[i];
        }
    }
}
