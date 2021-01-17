package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import math.Matrix;
import math.Line;
import math.Vector;

public class CoordinateSystem implements Renderable {
    private Vector iHat;
    private Vector jHat;
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

    public CoordinateSystem(){
        this.iHat = new Vector(0,1);
        this.jHat = new Vector(1,0);
    }
        }
    }

    public void updateLines(){

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

    @Override
    public void render(CoordinateSystem cs) {
        //...
    }

}
