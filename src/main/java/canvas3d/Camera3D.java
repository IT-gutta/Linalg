package canvas3d;

import math.Matrix;
import math3d.Vector3;
import math3d.Vector4;

public class Camera3D{
    private Vector3 position;
    private Vector3 direction;
    private final double fov = Math.PI/2;
    private final double zFar = 1000;
    private final double zNear = 10;

    private LightSource lightSource = new LightSource(new Vector3(0, 0, -100));

    private Matrix projectionMatrix;
    public Camera3D(){
        this.position = new Vector3(0, 0, -10);
        this.direction = new Vector3(0, 0, 1);
    }

    public Vector4 project(Vector3 vector3){
        Vector4 input = new Vector4(vector3.getX(), vector3.getY(), vector3.getZ() + 10, 1);
        double[] out = projectionMatrix.transform(input.getVector().getVector());
        out[0] /= out[3];
        out[1] /= out[3];

        //Translate to screen
        out[0] += 1d;
        out[1] += 1d;
        out[0] *= CanvasRenderer3D.getCanvasWidth() / 2;
        out[1] *= CanvasRenderer3D.getCanvasHeight() / 2;

        return new Vector4(out);
    }

    public void updateMatrix(){
        double f = 1d/(Math.tan(fov / 2));
        double a = CanvasRenderer3D.getCanvasHeight() / CanvasRenderer3D.getCanvasWidth();
        double q = zFar/(zFar-zNear);
        double[][] matrix = {
                {a*f, 0, 0, 0},
                {0, f, 0, 0},
                {0, 0, q, -q*zNear},
                {0, 0, 1, 0}
        };
        projectionMatrix = new Matrix(matrix);
    }

    public Vector3 getDirection(){
        return direction;
    }
    public Vector3 getPosition(){
        return position;
    }
    public LightSource getLightSource(){return lightSource;}

    private double cos(double angle){
        return Math.cos(angle);
    }
    private double sin(double angle){
        return Math.sin(angle);
    }
}
