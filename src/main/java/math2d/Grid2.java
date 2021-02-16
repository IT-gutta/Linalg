package math2d;

import graphics.Interpolatable;
import canvas2d.Render2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Matrix;
import math.Vector;

public class Grid2 extends Render2D implements Interpolatable {
    private final Vector iHat;
    private final Vector jHat;
    private final LineSegment2[] lineSegments;


    public Grid2(double startX, double startY, int h, int w, double sizeX, double sizeY){
        super(new Object());
        iHat = new Vector(1,0);
        jHat = new Vector(0,1);

        //add all the lines
        lineSegments = new LineSegment2[h + w + 2];
        int i = 0;
        while(i < w + 1){
            lineSegments[i] = new LineSegment2(startX + i*sizeX, startY, startX + i*sizeX, startY + sizeY*h);
            i++;
        }

        i = 0;
        while(i < h + 1){
            lineSegments[i + w + 1] = new LineSegment2(startX, startY + i*sizeY, startX + w*sizeX, startY + i*sizeY);
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
        for(LineSegment2 line : lineSegments) {
            line.handleInterpolation();
            line.render(gc, name, paint);
        }
    }


    @Override
    public void startInterpolation(Matrix matrix, int millis){
        iHat.transform(matrix);
        jHat.transform(matrix);
        for(LineSegment2 line : lineSegments)
            line.startInterpolation(matrix, millis);
    }

    @Override
    public void handleInterpolation() {
        for(LineSegment2 line : lineSegments) {
            line.handleInterpolation();
        }
    }
}
