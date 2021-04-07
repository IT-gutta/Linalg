package canvas2d;

import graphics.Interpolatable;
import javafx.scene.canvas.GraphicsContext;
import math.Matrix;
import math.Vector;

/**
 * Represents a two dimensional grid
 */
public class Grid2D extends Render2D implements Interpolatable {
    private final Vector iHat;
    private final Vector jHat;
    private final LineSegment2D[] lineSegments;

    public Grid2D(double startX, double startY, int h, int w, double sizeX, double sizeY){
        iHat = new Vector(1,0);
        jHat = new Vector(0,1);

        //add all the lines
        lineSegments = new LineSegment2D[h + w + 2];
        int i = 0;
        while(i < w + 1){
            lineSegments[i] = new LineSegment2D(startX + i*sizeX, startY, startX + i*sizeX, startY + sizeY*h);
            i++;
        }

        i = 0;
        while(i < h + 1){
            lineSegments[i + w + 1] = new LineSegment2D(startX, startY + i*sizeY, startX + w*sizeX, startY + i*sizeY);
            i++;
        }
    }

    /**
     * Returns the first basis vector of the grid
     */
    public Vector getI(){
        return iHat;
    }

    /**
     * Returns the second basis vector of the grid
     */
    public Vector getJ(){
        return jHat;
    }

    @Override
    public String toString(){
        return "iHat: " + iHat + ", jHat: " + jHat;
    }

    /**
     * Returns null
     */
    @Override
    public Object getMath() {
        return null;
    }

    /**
     * Renders the grid onto the canvas
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setStroke(paint);
        //gc.setLineWidth(1);
        for(LineSegment2D line : lineSegments) {
            line.handleInterpolation();
            line.render(gc);
        }
    }

    /**
     * ??
     */
    @Override
    public void startInterpolation(Matrix matrix, int millis){
        iHat.transform(matrix);
        jHat.transform(matrix);
        for(LineSegment2D line : lineSegments)
            line.startInterpolation(matrix, millis);
    }

    /**
     * ??
     */
    @Override
    public void handleInterpolation() {
        for(LineSegment2D line : lineSegments) {
            line.handleInterpolation();
        }
    }
}
