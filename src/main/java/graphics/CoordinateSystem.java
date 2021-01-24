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
        CanvasRenderer.unitSize = 40;
        updateLines();
    }

    public void updateLines(){




//        verticalLines = (int) CanvasRenderer.getCanvasWidth() / CanvasRenderer.getUnitSize() / 2 + 1;
//        horizontalLines = (int) CanvasRenderer.getCanvasHeight() / CanvasRenderer.getUnitSize() / 2 + 1;
        double width = CanvasRenderer.getCanvasWidth();
        double height = CanvasRenderer.getCanvasHeight();
        int unitSize = CanvasRenderer.getUnitSize();

        double offsetX = (CanvasRenderer.getOffsetX() % unitSize) / unitSize;
        double offsetY = (CanvasRenderer.getOffsetY() % unitSize) / unitSize;

        double midScreenX = CanvasRenderer.fromScreenToCanvasX(width/2) / unitSize;
        double midScreenY = CanvasRenderer.fromScreenToCanvasY(height/2) / unitSize;

        int halfDiagonal = (int) Math.sqrt(Math.pow(width / unitSize / 2, 2) + Math.pow(height / unitSize / 2, 2)) + 1;

        int flipX = 1;
        int flipY = 1;

        if(offsetX > 0)
            flipX = -1;

        if(offsetY > 0)
            flipY = -1;




        lines = new Line[4*halfDiagonal];
        int i = 0;
        double x = midScreenX;
        while(x > -halfDiagonal + midScreenX){
            lines[i] = new Line(new Point(x + flipX * offsetX, midScreenY), jHat);
            x--;
            i++;
        }


        x = midScreenX;
        while(x < halfDiagonal + midScreenX){
            lines[i] = new Line(new Point(x + flipX * offsetX + 1, midScreenY), jHat);
            x++;
            i++;
        }

        double y = midScreenY;
        while(y > -halfDiagonal + midScreenY){
            lines[i] = new Line(new Point(midScreenX, y + flipY * offsetY), iHat);
            y--;
            i++;
        }


        y = midScreenY;
        while(y < halfDiagonal + midScreenY){
            lines[i] = new Line(new Point(midScreenX, y + flipY * offsetY + 1), iHat);
            y++;
            i++;
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
