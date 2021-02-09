package canvas3d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Renderer3D<T> {
    protected T math;
    private boolean isHidden = false;
    //TODO Add the name of an object to the object in the graphics window (as 3D text?)
    public Renderer3D(T mathVariable){
        this.math = mathVariable;
    }

    public T getMath(){
        return math;
    }
    public abstract void render(GraphicsContext gc, String name, Paint paint);
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
