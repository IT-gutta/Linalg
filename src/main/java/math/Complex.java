package math;

public class Complex {
    private double re;
    private double im;
    private double length;
    private double angle;

    public Complex(double re, double im){
        this.re = re;
        this.im = im;
    }

    public Complex(boolean fromAngle, double length, double angle){
        this.length = length;
        this.angle = angle;
    }

    public String asPolar(){
        return re + "e^i*" + angle;
    }

    private void setPolar(){
        length = Math.sqrt(Math.abs(re) + Math.abs(im));
        if(re >= 0)
            angle = Math.atan(im / re);
        else
            angle = Math.atan(im / re) + Math.PI;
    }

    private void setStandard(){
        re = length * Math.cos(angle);
        im = length * Math.sin(angle);
    }


    @Override
    public String toString(){
        return re + " + " + im + "i";
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

