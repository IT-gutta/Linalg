package canvas3d;

import javafx.scene.paint.Color;
import math3d.Vector3;

public class Sphere extends Mesh{
    public Sphere(Vector3 position, double radius){
        super("sphere.obj", position, radius);
    }

    @Override
    public void beforeRender() {

    }

    @Override
    public Object getMath() {
        return null;
    }

    @Override
    public String toString(){
        return "Center: " + position + ", Radius: " + currentScale;
    }
}
