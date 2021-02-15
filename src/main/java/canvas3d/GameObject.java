package canvas3d;

import math3d.Vector3;

public abstract class GameObject{
    protected Vector3 position;
    protected Vector3 forward;
    protected Vector3 up;
    protected Vector3 right;
    protected Triangle[] triangles;

    public GameObject(Triangle[] triangles, Vector3 position, Vector3 forward, Vector3 up){
        this(position, forward, up);
        this.triangles = triangles;
    }

    public GameObject(Vector3 position, Vector3 forward, Vector3 up) {
        this.position = position;
        this.forward = forward.normalized();
        this.up = up.normalized();
        this.right = Vector3.cross(up, forward);
        this.right = this.right.normalized();
    }

    public GameObject(Vector3 position){
        this(position, Vector3.FORWARD(), Vector3.UP());
    }

    public GameObject(){
        this(Vector3.ZERO());
    }

    public GameObject(Triangle[] triangles, Vector3 position){
        this(position);
        this.triangles = triangles;
    }


    public void setForward(Vector3 forward){
        this.forward = forward.normalized();
        this.up = Vector3.subtract(up, Vector3.scale(forward, forward.dot(up))).normalized();
        this.right = Vector3.cross(up, forward).normalized();
    }

    public void setPosition(Vector3 position){
        this.position = position;
    }

    public void pointAt(Vector3 target){
        setForward(Vector3.subtract(target, position));
    }

    public void render(GraphicsContext3D gc){
        update();

        if(triangles == null) //ingenting som skal renderes
            return;

        for(Triangle tri : triangles)
            tri.render(gc, position, forward, up, right);
    }


    public abstract void update();


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
}
