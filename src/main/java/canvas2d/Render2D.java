package canvas2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class Render2D {
    private boolean isHidden = false;
    //TODO Add the ability for scalars to be rendered along the real axis
    //TODO Add the name of an object to the object in the graphics window

    public abstract Object getMath();
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
