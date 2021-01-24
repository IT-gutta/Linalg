package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import math.*;

import java.util.ArrayList;
import java.util.List;

public class CoordinateSystem implements Renderable, Transformable {
    private Vector iHat;
    private Vector jHat;
    private Line[] lines;
    private int verticalLines;
    private int horizontalLines;
    private boolean isHidden = false;



    public CoordinateSystem(){
        iHat = new Vector(1,0);
        jHat = new Vector(0,1);
        CanvasRenderer.unitSize = 40;
        updateLines();
    }

    public void updateLines(){
//        verticalLines = (int) CanvasRenderer.getCanvasWidth() / CanvasRenderer.getUnitSize() / 2 + 1;
//        horizontalLines = (int) CanvasRenderer.getCanvasHeight() / CanvasRenderer.getUnitSize() / 2 + 1;

        int halfDiagonal = (int) Math.sqrt(Math.pow(CanvasRenderer.getCanvasWidth() / CanvasRenderer.getUnitSize() / 2, 2) + Math.pow(CanvasRenderer.getCanvasHeight() / CanvasRenderer.getUnitSize() / 2, 2)) + 1;

        lines = new Line[4*halfDiagonal];
        int i = 0;
        int x = 0;
        while(x > -halfDiagonal){
            lines[i] = new Line(new Point(x, 0), jHat);
            x--;
            i++;
        }
        x = 0;
        while(x < halfDiagonal){
            lines[i] = new Line(new Point(x, 0), jHat);
            x++;
            i++;
        }

        int y = 0;
        while(y > -halfDiagonal){
            lines[i] = new Line(new Point(0, y), iHat);
            y--;
            i++;
        }
        y = 0;
        while(y < halfDiagonal){
            lines[i] = new Line(new Point(0, y), iHat);
            y++;
            i++;
        }




        verticalLines = 25;
        horizontalLines = 15;
//        int j = 0;
//        lines = new Line[verticalLines*2+horizontalLines*2-2];
//        for(int i = -verticalLines+1; i<verticalLines; i++){
//            lines[j] = new Line(new Point(iHat.getElement(0)*i,iHat.getElement(1)*i),jHat);
//            j++;
//        }
//
//        for(int i = -horizontalLines+1; i<horizontalLines; i++){
//            lines[j] = new Line(new Point(jHat.getElement(0)*i,jHat.getElement(1)*i),iHat);
//            j++;
//        }
    }

    public void transform(Matrix matrix){
        iHat.transform(matrix);
        jHat.transform(matrix);
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

    public static boolean insideCanvas(Point point){
        Point actual = CanvasRenderer.toCanvasPoint(point);
        if(actual.getElement(0) < 0 || actual.getElement(0) > CanvasRenderer.getCanvasWidth() || actual.getElement(1) < 0 || actual.getElement(1) > CanvasRenderer.getCanvasHeight())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "iHat: " + iHat + ", jHat: " + jHat;
    }


    @Override
    public void render(GraphicsContext gc) {
        if(isHidden())
            return;
        gc.setLineWidth(0.5);
        for(Line line : lines){
            if(line != null)
                line.render(gc);
        }
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
