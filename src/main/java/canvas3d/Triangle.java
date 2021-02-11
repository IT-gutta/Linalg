package canvas3d;

import math3d.Vector3;

public class Triangle {
    private Vector3[] vertices;
    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3){
        this.vertices = new Vector3[]{p1, p2, p3};
    }
    public Triangle(double... coords){
        if(coords.length != 9)
            throw new IllegalArgumentException("Illegal number of points, must be 9");
        this.vertices = new Vector3[3];
        for(int i = 0; i < 3; i++){
            this.vertices[i] = new Vector3(coords[i], coords[i+1], coords[i+2]);
        }
    }

    public void render(GraphicsContext3D gc){
        gc.strokeLine(vertices[0], vertices[1]);
        gc.strokeLine(vertices[1], vertices[2]);
        gc.strokeLine(vertices[2], vertices[0]);
    }
}
