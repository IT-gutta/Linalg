package canvas3d;

import graphics.DefinedVariables;
import javafx.scene.paint.Color;
import math.Matrix;
import math3d.Vector3;
import math3d.Vector4;

public class Camera3D extends Render3D{
    //TODO fix cameraMovement
    //TODO implement some sort of clipping of the triangles (when they are at the edge of canvas)
    private final double fov = Math.PI/2;
    private final double zFar = 40; //vet egt ikke hva denne gjør, men den må være høyere enn zNear for at det skal funke hehe
    private final double zNear = 0.1; //bestemmer rendering distance

    private LightSource lightSource;

    private Matrix projectionMatrix;
    private Matrix lookAtMatrix;
    public Camera3D(){
        super(new Vector3(0, 0, -6), Vector3.FORWARD(), Vector3.UP());
        lightSource = new LightSource(new Vector3(10, 4, -20));
        DefinedVariables.add(lightSource, "LightBulb");
    }

    @Override
    public void beforeRender() {

    }

//    //lightsource på camera
//    @Override
//    public void setPosition(Vector3 position){
//        lightSource.setPosition(position);
//        super.setPosition(position);
//    }


    @Override
    public Object getMath() {
        return null;
    }

    public void updateMatrix(){
        double f = 1d/(Math.tan(fov / 2));
        double a = CanvasRenderer3D.getCanvasHeight() / CanvasRenderer3D.getCanvasWidth();
        double q = 1/(zFar-zNear);
        double[][] matrix = {
                {a*f, 0, 0, 0},
                {0, -f, 0, 0},
                {0, 0, q, -q*zNear},
                {0, 0, 1, 0}
        };
        projectionMatrix = new Matrix(matrix);


//        Matrix pointAtMatrix = new Matrix(new double[][]{
//                {right.getX(), up.getX(), forward.getX(), position.getX()},
//                {right.getY(), up.getY(), forward.getY(), position.getY()},
//                {right.getZ(), up.getZ(), forward.getZ(), position.getZ()},
//                {0, 0, 0, 1}
//        });
        //lookAtMatrix = pointAtMatrix.getInverted();

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
        //System.out.println("z:"+out[2]);

        if(out[2] < 0 || out[2] > 1)
            return null;

        out[0] /= out[3];
        out[1] /= out[3];

        //Translate to screen
        out[0] += 1d;
        out[1] += 1d;
        out[0] *= CanvasRenderer3D.getCanvasWidth() / 2;
        out[1] *= CanvasRenderer3D.getCanvasHeight() / 2;

        //System.out.println("width" + CanvasRenderer3D.getCanvasWidth());

        return new Vector4(out);
    }

    public LightSource getLightSource(){return lightSource;}

    private double cos(double angle){
        return Math.cos(angle);
    }
    private double sin(double angle){
        return Math.sin(angle);
    }


    @Override
    public String toString(){
        return position.toString();
    }
}
