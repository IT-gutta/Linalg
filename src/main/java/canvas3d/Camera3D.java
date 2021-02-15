package canvas3d;

import math.Matrix;
import math3d.Vector3;
import math3d.Vector4;

public class Camera3D extends GameObject{
    //TODO fix cameraMovement
    //TODO implement some sort of clipping of the triangles (when they are at the edge of canvas)
    private final double fov = Math.PI/2;
    private final double zFar = 1000;
    private final double zNear = 10;

    private LightSource lightSource = new LightSource(new Vector3(1, 2, -4));

    private Matrix projectionMatrix;
    private Matrix lookAtMatrix;
    public Camera3D(){
        super(new Vector3(0, 0, -6), Vector3.FORWARD(), Vector3.UP());
    }

    @Override
    public void update() {

    }

    public void updateMatrix(){
        double f = 1d/(Math.tan(fov / 2));
        double a = CanvasRenderer3D.getCanvasHeight() / CanvasRenderer3D.getCanvasWidth();
        double q = zFar/(zFar-zNear);
        double[][] matrix = {
                {a*f, 0, 0, 0},
                {0, -f, 0, 0},
                {0, 0, q, -q*zNear},
                {0, 0, 1, 0}
        };
        projectionMatrix = new Matrix(matrix);


        Matrix pointAtMatrix = new Matrix(new double[][]{
                {right.getX(), up.getX(), forward.getX(), position.getX()},
                {right.getY(), up.getY(), forward.getY(), position.getY()},
                {right.getZ(), up.getZ(), forward.getZ(), position.getZ()},
                {0, 0, 0, 1}
        });
        //lookAtMatrix = pointAt.getInverted();

        //denne er helt lik pointAt.getIverted();
        Matrix lookAt = new Matrix(new double[][]{
                {right.getX(), right.getY(), right.getZ(), -position.dot(right)},
                {up.getX(), up.getY(), up.getZ(),  -position.dot(up)},
                {forward.getX(), forward.getY(), forward.getZ(),  -position.dot(forward)},
                {0, 0, 0, 1}
        });

        lookAtMatrix = lookAt;

        //System.out.println(lookAtMatrix);
    }


    public Vector4 project(Vector3 vector3){
        Vector4 input = new Vector4(vector3.getX(), vector3.getY(), vector3.getZ(), 1);
        double[] cameraView = lookAtMatrix.transform(input.getVector());
        double[] out = projectionMatrix.transform(cameraView);
        out[0] /= out[3];
        out[1] /= out[3];

        //Translate to screen
        out[0] += 1d;
        out[1] += 1d;
        out[0] *= CanvasRenderer3D.getCanvasWidth() / 2;
        out[1] *= CanvasRenderer3D.getCanvasHeight() / 2;

        return new Vector4(out);
    }

    public LightSource getLightSource(){return lightSource;}

    private double cos(double angle){
        return Math.cos(angle);
    }
    private double sin(double angle){
        return Math.sin(angle);
    }
}
