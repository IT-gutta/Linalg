package canvas2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Renderer2D<T> {
    protected T math;
    private boolean isHidden = false;
    //TODO Add the ability for scalars to be rendered along the real axis
    //TODO Add the name of an object to the object in the graphics window
    public Renderer2D(T mathVariable){
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
