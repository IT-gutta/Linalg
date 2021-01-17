package graphics;

import math.Vector;

public class CoordinateSystem {
    private Vector iHat = new Vector(0,1);
    private Vector jHat = new Vector(1,0);

    public CoordinateSystem(){

    }

    public Vector getI(){
        return iHat;
    }

    public void setI(Vector v) throws IllegalArgumentException{
        if(v.getDimensions()!=2)
            throw new IllegalArgumentException("Basis vector must be two dimensional");
        iHat = v;
    }

    public Vector getJ(){
        return jHat;
    }

    public void setJ(Vector v) throws IllegalArgumentException{
        if(v.getDimensions()!=2)
            throw new IllegalArgumentException("Basis vector must be two dimensional");
        jHat = v;
    }
}
