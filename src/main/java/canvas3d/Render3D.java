package canvas3d;

import javafx.scene.paint.Color;
import math3d.Vector3;

public abstract class Render3D {
    //TODO add spheres
    protected Vector3 position;
    protected Vector3 forward;
    protected Vector3 up;
    protected Vector3 right;
    protected Triangle[] triangles;
    protected Vector3[] vertices;


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


        System.out.println(this.up);
        this.forward = forward.normalized();
        this.right = Vector3.cross(up, forward).normalized();

        System.out.println("dot: " + this.forward.dot(this.up));
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


    public void render(GraphicsContext3D gc, String name, Color color){
        update(name, color);

        if(triangles == null) //ingenting som skal renderes
            return;

        for(Triangle tri : triangles)
            tri.render(gc, position, forward, up, right);
    }



    public abstract void update(String name, Color color);

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
}
