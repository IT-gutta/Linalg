package graphics;

import math.Line;
import math.Vector;

public class CoordinateSystem {
    private Vector iHat = new Vector(0,1);
    private Vector jHat = new Vector(1,0);
    private Line[] lines;
    int verticalLines;
    int horizontalLines;
    int unitSize;

    public CoordinateSystem(int verticalLines, int horizontalLines, int unitSize){
        this.verticalLines = verticalLines;
        this.horizontalLines = horizontalLines;
        this.unitSize = unitSize;
        lines = new Line[verticalLines*2+horizontalLines*2-2];
        for(int i = -verticalLines+1; i<verticalLines; i++){

        }
    }

    public void updateLines(){

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
