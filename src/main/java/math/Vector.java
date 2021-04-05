package math;

import graphics.*;

import java.io.Serializable;
import java.util.stream.DoubleStream;

public class Vector implements Transformable, Serializable {

    private double[] vector;
    private Interpolator interpolator;

    private final double arrowTipLength = 12;
    private final double arrowSideLength = 7;

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

    /**
     * Returns a given element
     */
    public double getElement(int index){
        return vector[index];
    }

    /**
     * Sets a given element
     */
    public void setElement(int index, double value){
        vector[index] = value;
    }

    /**
     * Returns the magnitude of the Vector
     */
    public double getMagnitude(){
        double ans = 0;
        for(double element:vector){
            ans+=Math.pow(element,2);
        }
        return Math.sqrt(ans);
    }

    /**
     * Sets the magnitude of the Vector
     */
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

    /**
     * Returns the number of dimensions of the Vector
     */
    public int getDimensions(){
        return vector.length;
    }

    /**
     * Scales the Vector by a double
     */
    public void scale(double s){
        for(int i = 0; i<vector.length; i++){
            vector[i]*=s;
        }
    }

    /**
     * Returns the sum of the Vector and a given Vector
     */
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

    /**
     * Returns the difference of the Vector and a given Vector
     */
    public Vector sub(Vector v) throws IllegalArgumentException {
        if(v.getDimensions()!=this.getDimensions())
            throw new IllegalArgumentException("The number of dimensions must be equal");

        for(int i = 0; i<vector.length; i++){
            vector[i]-=v.getElement(i);
        }
        return this;
    }

    /**
     * Returns gcd of the elements of the vector
     */
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

    /**
     * Returns the dot product of the Vector and a given Vector
     */
    public double dot(Vector v) throws IllegalArgumentException{
        if(this.getDimensions()!=v.getDimensions())
            throw new IllegalArgumentException("Vectors must have the same dimensions");

        double dot = 0;
        for(int i = 0; i< getDimensions(); i++){
            dot += getElement(i)*v.getElement(i);
        }
        return dot;
    }

    /**
     * Returns the cross product of the Vector and a given Vector
     */
    public Vector cross(Vector v) throws IllegalArgumentException{
        if(!(v.getDimensions()==3 && this.getDimensions()==3)){
            throw new IllegalArgumentException("Both vectors must be of dimension 3");
        }
        double[] u = {vector[1]*v.getElement(2)-vector[2]*v.getElement(1),vector[2]*v.getElement(0)-vector[0]*v.getElement(2),vector[0]*v.getElement(1)-vector[1]*v.getElement(0)};
        return new Vector(u);
    }

    /**
     * Returns the Vector as an array
     */
    public double[] getVector(){
        return vector;
    }

    /**
     * Appends elements onto the Vector
     */
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

    /**
     * Checks if the Vector and a given vector are of the same dimension
     */
    public boolean hasSameDimensions(Vector v){
        return this.getDimensions()==v.getDimensions();
    }

    /**
     * Returns the angle between the Vector and a given vector
     */
    public double angle(Vector v){
        return Math.acos(this.dot(v)/(this.getMagnitude()*v.getMagnitude()));
    }

    /**
     * Returns true if the Vector is equivalent to another vector, else false
     */
    public boolean equals(Vector v){
        if(this.getDimensions()!=v.getDimensions())
            return false;

        for(int i = 0; i<vector.length; i++){
            if(vector[i]!=v.getElement(i))
                return false;
        }
        return true;
    }

    /**
     * Returns true if the Vector is parallel to a given Vector, else false
     */
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

    /**
     * Returns the Vector transformed by a Matrix
     */
    public Vector getTransformed(Matrix matrix){
        return matrix.transform(this);
    }

    /**
     * Returns a Point from the Vector
     */
    public Point toPoint(){
        return Points.fromVector(this);
    }


    /**
     * Transforms the Vector with a Matrix
     */
    @Override
    public void transform(Matrix m){
        //skriv kode her
    }




}
