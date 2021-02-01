package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.scene.canvas.GraphicsContext;
import math.*;

public class Grid implements Renderable, Transformable {
    private Vector iHat;
    private Vector jHat;
    private Line[] lines;
    private int verticalLines;
    private int horizontalLines;
    private boolean isHidden = false;
    private double spaceBetween;
    private double baseSpaceBetween;



    public Grid(){
        iHat = new Vector(1,0);
        jHat = new Vector(0,1);
        baseSpaceBetween = CanvasRenderer.getUnitSize();
        updateLines();
    }

    public void updateLines(){
        return;
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
        gc.fillOval(CanvasRenderer.toCanvasX(0) - 3, CanvasRenderer.toCanvasY(0) - 3, 6, 6);
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
