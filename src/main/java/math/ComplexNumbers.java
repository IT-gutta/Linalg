package math;

public class ComplexNumbers {

    public static Complex fromVector(Vector v) throws IllegalArgumentException{
        if(v.getDimensions() != 2)
            throw new IllegalArgumentException("The number of dimensions must be 2");

        return new Complex(v.getElement(0), v.getElement(1));
    }

    public static Complex fromPolar(double length, double angle){
        var c = new Complex(0,0);
        c.setLength(length);
        c.setAngle(angle);
        c.setStandardFromPolar();
        return c;
    }

    public static Complex multiply(Complex c1, Complex c2){
        return new Complex(c1.getRe()*c2.getRe() - c1.getIm()*c2.getIm(), c1.getRe()*c2.getIm() + c1.getIm()*c2.getRe());
    }

    public static Complex add(Complex c1, Complex c2){
        return new Complex(c1.getRe() + c2.getRe(), c1.getIm() + c2.getIm());
    }

    public static Complex copyOf(Complex z){
        return new Complex(z.getRe(), z.getIm());
    }

    public static Complex pow(Complex z, double exponent){
        var c = copyOf(z);
        c.pow(exponent);
        return c;
    }
}
