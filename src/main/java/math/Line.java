package math;

import graphics.VariableContainer;

import java.io.Serializable;

public class Line implements Transformable, Serializable {
    //TODO fix toString
    private VariableContainer<Line> wrapper;
    private Point start;
    private Vector direction;


    public Line(Point p, Vector v){
        start = p;
        direction = v;
    }

    public Line(double pointX, double pointY, double directionX, double directionY){
        start = new Point(pointX, pointY);
        direction = new Vector(directionX, directionY);
    }

    public Point intersection(Line l){
        double a = start.getElement(0);double c = start.getElement(1);double e = l.getStart().getElement(0);double g = l.getStart().getElement(1);
        double b = direction.getElement(0);double d = direction.getElement(1); double f = l.getDirection().getElement(0); double h = l.getDirection().getElement(1);
        double t = (-a*h+c*f+e*h-f*g)/(b*h-d*f);
        return getPoint(t);
    }

    public Point getPoint(double parameter){
        return new Point(start.getElement(0) + parameter * direction.getElement(0), start.getElement(1) + parameter * direction.getElement(1));
    }

    public Vector getDirection(){
        return direction;
    }

    public Point getStart(){
        return start;
    }



    @Override
    public String toString(){
        return "Start: " + start + ", Direction: " + direction;
    }

    @Override
    public void transform(Matrix matrix) {
        //skriv kode her
    }
}
