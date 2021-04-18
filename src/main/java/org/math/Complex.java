package org.math;

import org.write.Writable;

/**
 * Represents a complex number and contains methods that correlate to operations on complex numbers
 */
public class Complex implements Writable {
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

    public Complex(){
        this.re = 0;
        this.im = 0;
        setPolarFromStandard();
    }


    /**
     * Returns the sum of the Complex the method is called on and the one given as input
     */
    public Complex add(Complex other){
        return ComplexNumbers.add(this, other);
    }

    /**
     * Returns the product of the Complex the method is called on and the one given as input
     */
    public Complex multiply(Complex other){
        return ComplexNumbers.multiply(this, other);
    }

    /**
     * Returns the the Complex to the power of the argument
     */
    public Complex pow(double exponent){
        double l = Math.pow(this.length, exponent);
        double a = this.angle * exponent;
        return ComplexNumbers.fromPolar(l, a);
    }

    /**
     * Return the polar form of the Complex as a string
     */
    public String asPolar(){
        return length + "e^i*" + angle;
    }

    /**
     * Sets the fields relevant to polar representation
     */
    public void setPolarFromStandard(){
        length = Math.sqrt(Math.abs(re) + Math.abs(im));
        if(re >= 0)
            angle = Math.atan(im / re);
        else
            angle = Math.atan(im / re) + Math.PI;
    }

    /**
     * Sets the fields relevant to standard representation
     */
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


    /**
     * Returns the real component
     */
    public double getRe() {
        return re;
    }

    /**
     * Sets the real component
     */
    public void setRe(double re) {
        this.re = re;
    }

    /**
     * Returns the imaginary component
     */
    public double getIm() {
        return im;
    }

    /**
     * Sets the imaginary component
     */
    public void setIm(double im) {
        this.im = im;
    }

    /**
     * Returns the length of the Complex
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the length of the Complex
     */
    public void setLength(double length) {
        if(length<0)
            throw new IllegalArgumentException("Length can not be negative");
        this.length = length;
    }

    /**
     * Returns the angle in relation to the positive re-axis
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the Complex
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public String writeString() {
        return "math.Complex---"+re+" "+im;
    }

    public Complex(String fileString){
        this.re = Double.parseDouble(fileString.split(" ")[0]);
        this.im = Double.parseDouble(fileString.split(" ")[1]);
        setPolarFromStandard();
    }

}

