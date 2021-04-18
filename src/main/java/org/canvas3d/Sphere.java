package org.canvas3d;

import org.math3d.Vector3;
/**
 * A sphere which renders a more detailed sphere as a mesh
 */
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
