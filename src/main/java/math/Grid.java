package math;

import exceptions.IllegalNumberOfDimensionsException;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Grid implements Renderable, Transformable {
    private Vector iHat;
    private Vector jHat;
    private LineSegment[] lineSegments;
    private boolean isHidden = false;
    private double startX;
    private double startY;
    private int h;
    private int w;
    private double sizeX;
    private double sizeY;



    public Grid(double startX, double startY, int h, int w, double sizeX, double sizeY){
        iHat = new Vector(1,0);
        jHat = new Vector(0,1);
        this.startX = startX;
        this.startY = startY;
        this.h = h;
        this.w = w;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        //add all the lines
        lineSegments = new LineSegment[h + w + 2];
        int i = 0;
        while(i < w + 1){
            lineSegments[i] = new LineSegment(startX + i*sizeX, startY, startX + i*sizeX, startY + sizeY*h);
            i++;
        }

        i = 0;
        while(i < h + 1){
            lineSegments[i + w + 1] = new LineSegment(startX, startY + i*sizeY, startX + w*sizeX, startY + i*sizeY);
            i++;
        }
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

    public void setJ(Vector v){
        jHat = v;
    }


    @Override
    public String toString(){
        return "iHat: " + iHat + ", jHat: " + jHat;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.setStroke(Paint.valueOf("blue"));
        gc.setLineWidth(0.8);
        for(LineSegment line : lineSegments)
            line.render(gc);
    }

    @Override
    public void transform(Matrix matrix){
        iHat.transform(matrix);
        jHat.transform(matrix);
        for(LineSegment line : lineSegments)
            line.transform(matrix);
    }

    @Override
    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public void show() {
        isHidden = false;
    }

    @Override
    public void hide() {
        isHidden = true;
    }
}
