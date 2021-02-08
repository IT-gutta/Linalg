package math;

public class Complex{
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
}

