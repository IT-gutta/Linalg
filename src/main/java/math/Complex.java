package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import graphics.Variable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Complex implements Renderable {
    private double re;
    private double im;
    private double length;
    private double angle;
    private boolean isHidden = false;

    public Complex(double re, double im){
        this.re = re;
        this.im = im;
        setPolarFromStandard();
    }

    public Complex add(Complex other){
        return ComplexNumbers.add(this, other);
    }

    public Complex multiply(Complex other){
        return ComplexNumbers.multiply(this, other);
    }

    public Complex pow(double exponent){
        double l = Math.pow(this.length, exponent);
        double a = this.angle * exponent;
        return ComplexNumbers.fromPolar(l, a);
    }


    public String asPolar(){
        return length + "e^i*" + angle;
    }

    public void setPolarFromStandard(){
        length = Math.sqrt(Math.abs(re) + Math.abs(im));
        if(re >= 0)
            angle = Math.atan(im / re);
        else
            angle = Math.atan(im / re) + Math.PI;
    }

    public void setStandardFromPolar(){
        re = length * Math.cos(angle);
        im = length * Math.sin(angle);
    }


    @Override
    public String toString(){
        if(im>0)
            return re + " + " + im + "i";
        return re + " - " + Double.toString(im).replace("-","")+"i";
    }


    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public double getIm() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Point getCanvasPoint(){
        return CanvasRenderer.toCanvasPoint(new Point(re, im));
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint){
        gc.setFill(paint);
        Point p = getCanvasPoint();
        gc.fillRect(p.getElement(0) - 5, p.getElement(1)- 5, 10,10);
        if(name!=null){
            Vector d = Vectors.scale(Vectors.fromComplex(this), 1/length/3);
            gc.fillText(name, CanvasRenderer.toCanvasX(re+d.getElement(0)), CanvasRenderer.toCanvasY(im+d.getElement(1)));
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

