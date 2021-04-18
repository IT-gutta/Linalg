package org.canvas3d;

import org.math3d.Vector3;
/**
 * A light source which is used to calculate illumination of the 3D objects
 * necessary to make it look 3d
 */
public class LightSource extends Render3D{
    private Mesh lightBulb;
    public LightSource(Vector3 position){
        super(position);
        //lightBulb = Mesh.fromFile("light.obj", position);
    }

    /**
     * Returns the brightness of a point (surface) based on the normalvector (how much the surface is facing the light source
     */
    public double getBrightness(Vector3 point, Vector3 normalPolygon){
        Vector3 delta = Vector3.subtract(position, point);
        if(delta.getX() == 0d && delta.getY() == 0d && delta.getZ() == 0d)
            return 1;
        double dot = delta.normalized().dot(normalPolygon.normalized());

        //System.out.println(dot);
        if(dot <= 0)
            return 0;
        else return dot;
    }

    @Override
    public void beforeRender(){
        //position = Vector3.rotateY(position, 0.001*CanvasRenderer3D.deltaTime);
    }

    @Override
    public Object getMath() {
        return null;
    }

    @Override
    public String toString(){
        return position.toString();
    }
}
