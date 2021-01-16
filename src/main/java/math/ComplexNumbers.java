package math;

public class ComplexNumbers {

    public Complex fromVector(Vector v) throws IllegalArgumentException{
        if(v.getDimensions() != 2)
            throw new IllegalArgumentException("The number of dimensions must be 2");

        return new Complex(v.getElement(0), v.getElement(1));
    }
}
