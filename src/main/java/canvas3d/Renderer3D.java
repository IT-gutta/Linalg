package canvas3d;

import javafx.scene.paint.Paint;

public abstract class Renderer3D {
    protected Object math;
    private boolean isHidden = false;
    //TODO Add the name of an object to the object in the graphics window (as 3D text?)

    public abstract Object getMath();
    public abstract void render(GraphicsContext3D gc, String name, Paint paint);
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
