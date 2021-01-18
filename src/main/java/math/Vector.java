package math;

import exceptions.IllegalNumberOfDimensionsException;
import exceptions.RenderException;
import graphics.CoordinateSystem;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.stream.DoubleStream;

public class Vector implements Renderable {
    private double[] vector;
    public static void main(String[] args) {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(5,-1,3);
        Vector v3 = new Vector(-5,6,1);
        System.out.println(Vectors.add(v1,v2,v3).toString());
    }

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

    public void add(Vector v) throws IllegalNumberOfDimensionsException {
        if(v.getDimensions()!=this.getDimensions())
            throw new IllegalNumberOfDimensionsException("The number of dimensions must be 2");
        else{
            for(int i = 0; i<vector.length; i++){
                vector[i]+=v.getElement(i);
            }
        }
    }

    public int factorize() throws IllegalNumberOfDimensionsException{
        for(double element:vector){
            if(!Utils.isWhole(element)) throw new IllegalNumberOfDimensionsException("Vector must contain integers");
        }
        int gcd = Utils.gcd((int)vector[0],(int)vector[1]);
        for(int i = 2; i< vector.length; i++){
            gcd = Utils.gcd(gcd, (int)vector[i]);
        }
        return gcd;
    }

    public double dot(Vector v) throws IllegalNumberOfDimensionsException{
        if(this.getDimensions()!=v.getDimensions()) throw new IllegalNumberOfDimensionsException("Vectors must have the same dimensions");
        double dot = 0;
        for(int i = 0; i< vector.length; i++){
            dot+=(vector[i]*v.getElement(i));
        }
        return dot;
    }

    public Vector cross(Vector v)throws IllegalNumberOfDimensionsException{
        if(!(v.getDimensions()==3 && this.getDimensions()==3)){
            throw new IllegalNumberOfDimensionsException("Both vectors must be of dimension 3");
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

    public boolean isParallel(Vector v) throws IllegalNumberOfDimensionsException{
        if(!this.hasSameDimensions(v))
            throw new IllegalNumberOfDimensionsException("Vectors must have same dimensions");
        double scale = vector[0]/v.getElement(0);
        for(int i = 1; i<this.getDimensions(); i++){
            if(scale*v.getElement(i)==vector[i]) return false;
        }
        return true;
    }

    public void applyTransformation(Matrix matrix){
        vector = matrix.transformVector(this).getVector();
        System.out.println(matrix.transformVector(this).toString());
    }

    public Vector transform(Matrix matrix){
        return matrix.transformVector(this);
    }

    public double getCanvasX(){
       return getCanvasPoint().getElement(0);
    }

    public double getCanvasY(){
        return getCanvasPoint().getElement(1);
    }

    public Point getCanvasPoint(){
        return CoordinateSystem.toCanvasPoint(Points.fromVector(this));
    }

    @Override
    public void render(GraphicsContext gc) throws RenderException {
        if(getDimensions() != 2)
            throw new RenderException("Has to be a 2-dimensional vector to render");
        gc.strokeLine(CoordinateSystem.getCanvasWidth() / 2, CoordinateSystem.getCanvasHeight() / 2, getCanvasX(), getCanvasY());
        gc.fillOval(getCanvasX(), getCanvasY(), 10, 10);
    }
}
