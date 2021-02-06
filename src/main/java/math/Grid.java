package math;

import graphics.Renderable;
import graphics.Variable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Grid implements Renderable, Transformable {
    private Variable<Grid> wrapper;
    private Vector iHat;
    private Vector jHat;
    private LineSegment[] lineSegments;
    private boolean isHidden = false;


    public Grid(double startX, double startY, int h, int w, double sizeX, double sizeY){
        iHat = new Vector(1,0);
        jHat = new Vector(0,1);

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

    public Vector getJ(){
        return jHat;
    }



    @Override
    public String toString(){
        return "iHat: " + iHat + ", jHat: " + jHat;
    }


    @Override
    public void render(GraphicsContext gc, String name, Paint paint) {
        gc.setStroke(paint);
        //gc.setLineWidth(1);
        for(LineSegment line : lineSegments) {
            line.handleLerp();
            line.render(gc, name, paint);
        }
    }


    @Override
    public void transform(Matrix matrix){
        iHat.transform(matrix);
        jHat.transform(matrix);
        for(LineSegment line : lineSegments)
            line.transform(matrix, 2000);
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
