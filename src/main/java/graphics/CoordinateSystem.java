package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.scene.canvas.GraphicsContext;
import math.Matrix;
import math.Line;
import math.Point;
import math.Vector;

public class CoordinateSystem implements Renderable {
    private Vector iHat;
    private Vector jHat;
    private Line[] lines;
    private int verticalLines;
    private int horizontalLines;


    public CoordinateSystem(double width, double height, Vector iHat, Vector jHat, int verticalLines, int horizontalLines, int unitSize){
        this.iHat = iHat;
        this.jHat = jHat;
        this.verticalLines = verticalLines;
        this.horizontalLines = horizontalLines;
        CanvasRenderer.unitSize = unitSize;
        CanvasRenderer.canvasWidth = width;
        CanvasRenderer.canvasHeight = height;
        updateLines();
    }

    public CoordinateSystem(double width, double height){
        iHat = new Vector(1,0);
        jHat = new Vector(0,1);
        verticalLines = 20;
        horizontalLines = 15;
        CanvasRenderer.unitSize = 40;
        CanvasRenderer.canvasWidth = width;
        CanvasRenderer.canvasHeight = height;
        updateLines();
    }

    public void updateLines(){
        int j = 0;
        lines = new Line[verticalLines*2+horizontalLines*2-2];
        for(int i = -verticalLines+1; i<verticalLines; i++){
            lines[j] = new Line(new Point(iHat.getElement(0)*i,iHat.getElement(1)*i),jHat);
            j++;
        }
        for(int i = -horizontalLines+1; i<horizontalLines; i++){
            lines[j] = new Line(new Point(jHat.getElement(0)*i,jHat.getElement(1)*i),iHat);
            j++;
        }
    }

    public void transform(Matrix matrix) throws IllegalNumberOfDimensionsException{
        iHat.applyTransformation(matrix);
        jHat.applyTransformation(matrix);
        updateLines();
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
    public void render(GraphicsContext gc) {
        gc.setLineWidth(0.5);
        for(Line line : lines){
            line.render(gc);
        }
    }
}
