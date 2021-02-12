package math;

import graphics.*;

import java.util.stream.DoubleStream;

public class Vector implements Transformable {

    private double[] vector;
    private Interpolator interpolator;

    private final double arrowTipLength = 12;
    private final double arrowSideLength = 7;

//    public static void main(String[] args) {
//        Vector v1 = new Vector("a",1,2,3);
//        Vector v2 = new Vector("b",5,-1,3);
//        Vector v3 = new Vector("c",-5,6,1);
//        System.out.println(Vectors.add(v1,v2,v3).toString());
//    }

    public Vector(double... args){
        vector = args;
    }

    public Vector(int length){
        this(DoubleStream.generate(() -> 0).limit(length).toArray());
    }

    @Override
    public String toString(){
        String s = "";
        for(double element:vector){
            s+=Double.toString(element)+", ";
        }
        return "["+s.substring(0,s.length()-2)+"]";
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

    public Vector add(Vector v) throws IllegalArgumentException {
        if(v.getDimensions()!=this.getDimensions())
            throw new IllegalArgumentException("The number of dimensions must be 2");
        else{
            for(int i = 0; i<vector.length; i++){
                vector[i]+=v.getElement(i);
            }
        }
        return this;
    }

    public Vector sub(Vector v) throws IllegalArgumentException {
        if(v.getDimensions()!=this.getDimensions())
            throw new IllegalArgumentException("The number of dimensions must be equal");

        for(int i = 0; i<vector.length; i++){
            vector[i]-=v.getElement(i);
        }
        return this;
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
        if(this.getDimensions()!=v.getDimensions())
            throw new IllegalArgumentException("Vectors must have the same dimensions");

        double dot = 0;
        for(int i = 0; i< getDimensions(); i++){
            dot += getElement(i)*v.getElement(i);
        }
//        if(Double.isNaN(dot)){
//            System.out.println("1: " + this);
//            System.out.println("2: " + v);
//        }
        return dot;
    }

    public Vector cross(Vector v) throws IllegalArgumentException{
        if(!(v.getDimensions()==3 && this.getDimensions()==3)){
            throw new IllegalArgumentException("Both vectors must be of dimension 3");
        }
        double[] u = {vector[1]*v.getElement(2)-vector[2]*v.getElement(1),vector[2]*v.getElement(0)-vector[0]*v.getElement(2),vector[0]*v.getElement(1)-vector[1]*v.getElement(0)};
        return new Vector(u);
    }

    public double[] getVector(){
        return vector;
    }

    public void addDimensions(double... args){
        double[] v = new double[this.getDimensions()+args.length];
        for(int i = 0; i<this.getDimensions(); i++){
            v[i] = vector[i];
        }
        for(int i = 0; i<args.length; i++){
            v[i+this.getDimensions()] = args[i];
        }
        vector = v;
    }

    public boolean hasSameDimensions(Vector v){
        return this.getDimensions()==v.getDimensions();
    }

    public double angle(Vector v){
        return Math.acos(this.dot(v)/(this.getMagnitude()*v.getMagnitude()));
    }
    public boolean equals(Vector v) throws IllegalArgumentException{
        if(this.getDimensions()!=v.getDimensions())
            throw new IllegalArgumentException("Vectors must have the same dimensions");
        for(int i = 0; i<vector.length; i++){
            if(vector[i]!=v.getElement(i)) return false;
        }
        return true;
    }

    public boolean isParallel(Vector v) throws IllegalArgumentException{
        if(!this.hasSameDimensions(v))
            throw new IllegalArgumentException("Vectors must have same dimensions");

        double x = v.getElement(0);
        int j = 0;
        while(x == 0d){
            j++;
            if(j >= this.getDimensions())
                return true;

            x = v.getElement(j);
        }
        double scale = vector[j]/x;

        for(int i = 1; i<this.getDimensions(); i++){
            if(scale*v.getElement(i)==vector[i]) return false;
        }
        return true;
    }


    public Vector getTransformed(Matrix matrix){
        return matrix.transform(this);
    }

    public Point toPoint(){
        return Points.fromVector(this);
    }


    @Override
    public void transform(Matrix m){
        //skriv kode her
    }

}
