package math;

import graphics.VariableContainer;

import java.io.Serializable;

/**
 * Represents a mathematical line
 */
public class Line implements Transformable, Writable {
    //TODO fix toString
    private VariableContainer<Line> wrapper;
    private final Point start;
    private final Vector direction;

    public Line(Point p, Vector v){
        start = p;
        direction = v;
    }

    public Line(double pointX, double pointY, double directionX, double directionY){
        start = new Point(pointX, pointY);
        direction = new Vector(directionX, directionY);
    }

    /**
     * Returns the Point of intersection with a given line
     */
    public Point intersection(Line l){
        double a = start.getElement(0);double c = start.getElement(1);double e = l.getStart().getElement(0);double g = l.getStart().getElement(1);
        double b = direction.getElement(0);double d = direction.getElement(1); double f = l.getDirection().getElement(0); double h = l.getDirection().getElement(1);
        double t = (-a*h+c*f+e*h-f*g)/(b*h-d*f);
        return getPoint(t);
    }

    /**
     * Returns the Point corresponding to the Line evaluated at a parameter t
     */
    public Point getPoint(double parameter){
        return new Point(start.getElement(0) + parameter * direction.getElement(0), start.getElement(1) + parameter * direction.getElement(1));
    }

    /**
     * Returns the direction-Vector of the Line
     */
    public Vector getDirection(){
        return direction;
    }

    /**
     * Returns the starting-Point of the Line
     */
    public Point getStart(){
        return start;
    }



    @Override
    public String toString(){
        return "Start: " + start + ", Direction: " + direction;
    }

    /**
     * Transforms a Line given a Matrix
     */
    @Override
    public void transform(Matrix matrix) {
        //skriv kode her
    }
}
