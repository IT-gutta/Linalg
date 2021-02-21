package math2d;

import math.Point;

import java.io.Serializable;

public class Point2 extends Point implements Serializable {
    public Point2(double x, double y){
        super(x, y);
    }




    public double getX(){
        return getElement(0);
    }
    public double getY(){
        return getElement(1);
    }
    public void setX(double x){
        setElement(0, x);
    }
    public void setY(double y){
        setElement(1, y);
    }
}
