package math;

import exceptions.IllegalNumberOfDimensionsException;
import exceptions.RenderException;
import graphics.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import graphics.CanvasRenderer;

import java.util.stream.DoubleStream;

public class Vector implements Renderable, Transformable {

    private double[] vector;
    private boolean isHidden = false;
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

    public Vector add(Vector v) throws IllegalNumberOfDimensionsException {
        if(v.getDimensions()!=this.getDimensions())
            throw new IllegalNumberOfDimensionsException("The number of dimensions must be 2");
        else{
            for(int i = 0; i<vector.length; i++){
                vector[i]+=v.getElement(i);
            }
        }

        return this;
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


    public Vector getTransformed(Matrix matrix){
        return matrix.transform(this);
    }

    public Point toPoint(){
        return Points.fromVector(this);
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        //linear interpolation
        handleLerp();


        if(isHidden())
            return;

        gc.setFill(paint);
        gc.setStroke(paint);

        Vector distance = Vectors.scale(this, 1/getMagnitude()/3);
        gc.fillText(name, CanvasRenderer.toCanvasX(getElement(0)+distance.getElement(0)), CanvasRenderer.toCanvasY(getElement(1)+distance.getElement(1)));


        gc.setLineWidth(1.5);
        gc.strokeLine(CanvasRenderer.toCanvasX(0), CanvasRenderer.toCanvasY(0), CanvasRenderer.toCanvasX(getElement(0)), CanvasRenderer.toCanvasY(getElement(1)));


        //fill arrow
        double angle = Math.atan2(getElement(1), getElement(0));

        double startX = CanvasRenderer.toCanvasX(getElement(0)) - arrowTipLength * Math.cos(angle); //move back so tip can be at exact location
        double startY = CanvasRenderer.toCanvasY(getElement(1)) + arrowTipLength * Math.sin(angle); //move back so tip can be at exact location

        double[] xCoords = {
                CanvasRenderer.toCanvasX(getElement(0)), //tipX
                startX + arrowSideLength * Math.sin(angle), //rightX
                startX - arrowSideLength * Math.sin(angle) //leftX
        };

        double[] yCoords = {
                CanvasRenderer.toCanvasY(getElement(1)), //tipY
                startY + arrowSideLength * Math.cos(angle), //rightY
                startY - arrowSideLength * Math.cos(angle), //leftY
        };

        gc.fillPolygon(xCoords, yCoords, 3);
    }

    @Override
    public void transform(Matrix m){
        transform(m, 1000);
    }

    public void transform(Matrix m, int millis){
        double[] endPos = m.transform(getVector());
        double startAngle = Math.atan2(vector[1], vector[0]);
        double endAngle = startAngle + Vectors.angle2(vector, endPos);
        double startLength = Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2));
        double endLength = Math.sqrt(Math.pow(endPos[0], 2) + Math.pow(endPos[1], 2));
        interpolator = new Interpolator(millis, new double[]{startLength, startAngle}, new double[]{endLength, endAngle});
    }

    public void handleLerp(){
        if(interpolator != null){
            interpolator.handle();
            //0 is the length, and 1 is the angle
            setElement(0, interpolator.get(0) * Math.cos(interpolator.get(1)));
            setElement(1, interpolator.get(0) * Math.sin(interpolator.get(1)));
            if(interpolator.isFinished())
                interpolator = null;
        }
    }


    @Override
    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public void show() {
        isHidden = false;
    }

    @Override
    public void hide() {
        isHidden = true;
    }

}
