package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import math.Matrix;
import math.Vector;

public class CoordinateSystem {
    private Vector iHat;
    private Vector jHat;

    public CoordinateSystem(){
        this.iHat = new Vector(0,1);
        this.jHat = new Vector(1,0);
    }

    public CoordinateSystem(Vector iHat, Vector jHat){
        this.iHat = iHat;
        this.jHat = jHat;
    }

    public Vector getI(){
        return iHat;
    }

    public void setI(Vector v) throws IllegalNumberOfDimensionsException{
        if(v.getDimensions()!=2)
            throw new IllegalNumberOfDimensionsException("Basis vector must be two dimensional");
        iHat = v;
    }

    public Vector getJ(){
        return jHat;
    }

    public void setJ(Vector v) throws IllegalNumberOfDimensionsException {
        if(v.getDimensions()!=2)
            throw new IllegalNumberOfDimensionsException("Basis vector must be two dimensional");
        jHat = v;
    }

    public void transform(Matrix matrix) throws IllegalNumberOfDimensionsException{
        iHat.applyTransformation(matrix);
        jHat.applyTransformation(matrix);
    }
}
