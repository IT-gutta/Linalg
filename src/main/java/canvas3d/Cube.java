package canvas3d;

import math3d.Vector3;
/**
 * A simple cube, which loads a mesh from an obj file
 */
public class Cube extends Mesh{
    public Cube(Vector3 position, double scale){
        super("cube.obj", position, scale);
    }

    @Override
    public void beforeRender() {

    }
}
