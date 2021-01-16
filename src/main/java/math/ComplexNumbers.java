package math;

public class ComplexNumbers {

    public Complex fromVector(Vector v) throws IllegalArgumentException{
        if(v.getDimensions() != 2)
            throw new IllegalArgumentException("The number of dimensions must be 2");

        return new Complex(v.getElement(0), v.getElement(1));
    }

    public Complex fromPolar(double length, double angle){
        var c = new Complex(0,0);
        c.setLength(length);
        c.setAngle(angle);
        c.setStandardFromPolar();

        return c;
    }

    public Complex add(Complex c1, Complex c2){
        return new Complex(c1.getRe() + c2.getRe(), c1.getIm() + c2.getIm());
    }
}
