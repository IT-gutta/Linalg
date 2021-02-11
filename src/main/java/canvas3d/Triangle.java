package canvas3d;

import math.Point;

public class Triangle {
    private Point[] vertices;
    public Triangle(Point p1, Point p2, Point p3){
        this.vertices = new Point[]{p1, p2, p3};
    }
    public Triangle(double... coords){
        if(coords.length != 9)
            throw new IllegalArgumentException("Illegal number of points, must be 9");
        this.vertices = new Point[3];
        for(int i = 0; i < 3; i++){
            this.vertices[i] = new Point(coords[i], coords[i+1], coords[i+2]);
        }
    }
}
