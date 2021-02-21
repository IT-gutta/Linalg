package canvas3d;

import graphics.Interpolatable;
import graphics.Interpolator;
import javafx.scene.paint.Color;
import math.Matrix;
import math.Transformable;
import math3d.Vector3;

import java.io.Serializable;

public abstract class Render3D implements Interpolatable, Serializable  {
    protected String name;
    protected Vector3 position;
    protected Vector3 forward;
    protected Vector3 up;
    protected Vector3 right;
    protected Triangle[] triangles;
    protected Vector3[] vertices;
    protected Interpolator interpolator;


    public Render3D(Vector3[] vertices, Triangle[] triangles, Vector3 position, Vector3 forward, Vector3 up){
        this(position, forward, up);
        this.triangles = triangles;
        this.vertices = vertices;
    }

    public Render3D(Vector3 position, Vector3 forward, Vector3 up) {
        this.position = position;
        this.forward = forward.normalized();
        this.up = up.normalized();
        this.right = Vector3.cross(up, forward);
        this.right = this.right.normalized();
    }

    public Render3D(Vector3 position){
        this(position, Vector3.FORWARD(), Vector3.UP());
    }

    public Render3D(){
        this(Vector3.ZERO());
    }

    public Render3D(Vector3[] vertices, Triangle[] triangles, Vector3 position){
        this(position);
        this.triangles = triangles;
        this.vertices = vertices;
    }


    public void setForward(Vector3 forward) {
        if(forward.equals(Vector3.ZERO()))
            throw new IllegalArgumentException("Forward cant be null vector");
        //TODO fix new up after setting forward
        /*if (forward.equals(this.up)) //roter 90 grader om en vilkårlig akse foreløpig??¨
            this.up = Vector3.scale(this.forward, -1);

        else
            this.up = Vector3.subtract(up, Vector3.scale(forward, forward.dot(up))).normalized();*/
        //setter en random up-vektor
        if(forward.getZ() == 0){
            if(forward.getY() == 0)
                this.up = new Vector3(0, 1, 0);
            else
                this.up = new Vector3(1, -forward.getX() / forward.getY(), 0).normalized();
        }
        else
            this.up = new Vector3(0, 1, -forward.getY() / forward.getZ()).normalized();


        this.forward = forward.normalized();
        this.right = Vector3.cross(up, forward).normalized();
        if(Math.abs(this.forward.dot(this.up)) > Math.pow(10, -10)){
            throw new Error("Det er en error i setforward funksjonen på Render3D, dot = " + this.forward.dot(this.up));
        }


        //System.out.println("dot: " + this.forward.dot(this.up));
        /*System.out.println("up: " + up);
        System.out.println("forward: " + this.forward);
        System.out.println("right" + right);*/
    }

    public void setPosition(Vector3 position){
        this.position = position;
    }

    public void pointAt(Vector3 target){
        setForward(Vector3.subtract(target, position));
    }

    public void rotateX(double angle){
        forward = Vector3.rotateX(forward, angle);
        up = Vector3.rotateX(up, angle);
        right = Vector3.rotateX(right, angle);
    }

    public void rotateY(double angle){
        forward = Vector3.rotateY(forward, angle);
        up = Vector3.rotateY(up, angle);
        right = Vector3.rotateY(right, angle);
    }

    public void rotateZ(double angle){
        forward = Vector3.rotateZ(forward, angle);
        up = Vector3.rotateZ(up, angle);
        right = Vector3.rotateZ(right, angle);
    }

    public void moveForward(double scalar){
        position = Vector3.add(position, Vector3.scale(forward, scalar));
    }
    public void moveRight(double scalar){
        position = Vector3.add(position, Vector3.scale(right, scalar));
    }
    public void moveUp(double scalar){
        position = Vector3.add(position, Vector3.scale(up, scalar));
    }


    public void render(GraphicsContext3D gc){
        beforeRender();
        handleInterpolation();

        if(triangles == null) //ingenting som skal renderes
            return;

        for(Triangle tri : triangles)
            tri.render(gc, position, forward, up, right);
    }



    public abstract void beforeRender();

    public void setName(String name){
        this.name = name;
    }

    public void setColor(Color color){
        for(Triangle triangle : triangles){
            triangle.setColor(color);
            triangle.setInterpolateColors(false);
        }
    }

    public void setColors(Color[] colors){
        for(Triangle triangle : triangles){
            triangle.setColors(colors);
            triangle.setInterpolateColors(true);
        }
    }

    public void setColors(Color c1, Color c2, Color c3){
        for(Triangle triangle : triangles){
            triangle.setColors(new Color[]{c1, c2, c3});
            triangle.setInterpolateColors(true);
        }
    }


    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getForward() {
        return forward;
    }

    public Vector3 getUp() {
        return up;
    }

    public Vector3 getRight() {
        return right;
    }





    //gamle ting som er viktig for geogebra-delen
    private boolean isHidden = false;
    //TODO Add the name of an object to the object in the graphics window (as 3D text?)

    public abstract Object getMath();
    public boolean isHidden(){
        return isHidden;
    };
    public void show(){
        isHidden = false;
    };
    public void hide(){
        isHidden = true;
    };


    @Override
    public void startInterpolation(Matrix m, int millis) {
//        Vector3[] transformedVertices = new Vector3[vertices.length];
//        for(int i = 0; i < vertices.length; i++){
//            transformedVertices[i] = new Vector3(m.transform(vertices[i].getVector()));
//        }
        double[] startForward = forward.getVector();
        double[] startRight = right.getVector();
        double[] startUp = up.getVector();
        double[] startPosition = position.getVector();

        double[] endForward = m.transform(startForward);
        double[] endRight = m.transform(startRight);
        double[] endUp = m.transform(startUp);
        double[] endPosition = m.transform(startPosition);

        double[] starts = new double[12];
        double[] ends = new double[12];

        for(int i = 0; i < 3; i++){
            starts[i] = startForward[i];
            starts[3 + i] = startRight[i];
            starts[6 + i] = startUp[i];
            starts[9 + i] = startPosition[i];

            ends[i] = endForward[i];
            ends[3 + i] = endRight[i];
            ends[6 + i] = endUp[i];
            ends[9 + i] = endPosition[i];
        }
        interpolator = new Interpolator(millis, starts, ends);
    }

    @Override
    public void handleInterpolation() {
        if(interpolator != null){
            interpolator.handle();

            forward.setX(interpolator.get(0));
            forward.setY(interpolator.get(1));
            forward.setZ(interpolator.get(2));
            right.setX(interpolator.get(3));
            right.setY(interpolator.get(4));
            right.setZ(interpolator.get(5));
            up.setX(interpolator.get(6));
            up.setY(interpolator.get(7));
            up.setZ(interpolator.get(8));
            position.setX(interpolator.get(9));
            position.setY(interpolator.get(10));
            position.setZ(interpolator.get(11));

            if(interpolator.isFinished())
                interpolator = null;
        }
    }
}
