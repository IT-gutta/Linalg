package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.spi.CalendarNameProvider;

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
        updateLines();
    }

    public void updateLines(){

        double midX = CanvasRenderer.fromCanvasX(CanvasRenderer.getCanvasWidth() / 2);
        double midY = CanvasRenderer.fromCanvasY(CanvasRenderer.getCanvasHeight() / 2);

        double dX = Math.abs(midX) - Math.floor(Math.abs(midX));
        double dY = Math.abs(midY) - Math.floor(Math.abs(midY));

        int halfDiagonalInUnits = (int) (Math.sqrt(Math.pow(CanvasRenderer.getCanvasWidth(), 2) + Math.pow(CanvasRenderer.getCanvasHeight(), 2)) / 2 / CanvasRenderer.getUnitSize()) + 1;

        lines = new Line[halfDiagonalInUnits*4];

        int i = 0;

        int count = 0;

        double x = midX - dX;
        while(count < halfDiagonalInUnits){
            lines[i] = new Line(new Point(x, midY), jHat);
            x--;
            count++;
            i++;
        }

        count = 0;
        x = midX - dX + 1;
        while(count < halfDiagonalInUnits){
            lines[i] = new Line(new Point(x, midY), jHat);
            x++;
            count++;
            i++;
        }

        count = 0;
        double y = midY - dY + 1;
        while(count < halfDiagonalInUnits){
            lines[i] = new Line(new Point(midX, y), iHat);
            y++;
            count++;
            i++;
        }

        count = 0;
        y = midY - dY;
        while(count < halfDiagonalInUnits){
            lines[i] = new Line(new Point(midX, y), iHat);
            y--;
            count++;
            i++;
        }

        //fikse på alle linjer
        for(Line line : lines){
            line.updateCanvasPoints();
        }
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
