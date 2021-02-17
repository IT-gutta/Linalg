package canvas3d;

import javafx.scene.paint.Color;
import math3d.Vector3;

import java.util.ArrayList;
import java.util.List;

public class PlaneSegment extends Render3D{
    private Vector3 p1;
    private Vector3 p2;
    private Vector3 p3;
    private Vector3 p4;
    private double width;
    private double height;
    private Vector3 v1;
    private Vector3 v2;
    private double wStep;
    private double hStep;
    private int numberOfVertices;
    private List<Triangle> tris = new ArrayList<>();
    private Vector3[] vertices;

    public PlaneSegment(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, int numberOfVertices){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;



        this.v1 = Vector3.subtract(p2, p1);
        this.v2 = Vector3.subtract(p3, p1);

        this.width = v1.getMagnitude();
        this.height = v2.getMagnitude();

        //normaliserer vektorene
        this.v1 = this.v1.normalized();
        this.v2 = this.v2.normalized();

        if(Vector3.subtract(p4, p2).getMagnitude() != height || Vector3.subtract(p4, p3).getMagnitude() != width)
            throw new IllegalArgumentException("The points have to form a parallellogram");


        double f = 1d / Math.sqrt(numberOfVertices);
        hStep = f*height;
        wStep = f*width;

        this.numberOfVertices = (int) (1d / Math.pow(f, 2)); //stemmer ca.

        vertices = new Vector3[this.numberOfVertices];


        for(double h = 0; h < height; h+=hStep){
            for(double w = 0; w < width; w+=wStep){
                vertices[(int) (w/wStep*h/hStep)] = Vector3.add(Vector3.scale(v1, w), Vector3.scale(v2, h));
            }
        }

        for(double h = 0; h < height - hStep; h+=hStep){
            for(double w = 0; w < width - wStep; w+=wStep){
                tris.add(new Triangle(vertices[(int) (w/wStep*h/hStep)], vertices[(int) (w/wStep*h/hStep) + 1], vertices[(int) (w/wStep* (h/hStep + 1)) + 1]));
                tris.add(new Triangle(vertices[(int) (w/wStep*h/hStep)], vertices[(int) (w/wStep* (h/hStep + 1)) + 1], vertices[(int) (w/wStep* (h/hStep + 1))]));
            }
        }
    }



    @Override
    public void update(String name, Color color) {

    }

    @Override
    public Object getMath() {
        return null;
    }
}
