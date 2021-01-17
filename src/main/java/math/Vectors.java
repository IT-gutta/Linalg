package math;

public class Vectors {
    public Vector fromPoint(Point p){
        return new Vector(p.getPoint());
    }
    public Point toPoint(Vector v){
        return new Point(v.getVector());
    }
    public static Vector add(Vector... args) throws IllegalArgumentException{
        int d = args[0].getDimensions();
        double[] u = new double[d];
        for(Vector v:args){
            if(d!=v.getDimensions())
                throw new IllegalArgumentException("Vectors must have same dimensions");
            for(int i = 0; i<d; i++){
                u[i]+=v.getElement(i);
            }
        }
        return new Vector(u);
    }
    public Complex toComplex(Vector v) throws IllegalArgumentException{
        if(v.getDimensions()!=2)
            throw new IllegalArgumentException("Vector must be two dimensional");
        return new Complex(v.getElement(0),v.getElement(1));
    }
    public Vector fromComplex(Complex c){
        return new Vector(c.getRe(), c.getIm());
    }
}
