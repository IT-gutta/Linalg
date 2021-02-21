package canvas2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public abstract class Render2D implements Serializable {
    protected String name;
    protected Paint paint;
    private boolean isHidden = false;
    //TODO Add the ability for scalars to be rendered along the real axis
    //TODO Add the name of an object to the object in the graphics window
    public void setName(String name){
        this.name = name;
    }
    public void setPaint(Paint paint){
        this.paint = paint;
    }

    public abstract Object getMath();
    public abstract void render(GraphicsContext gc);
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
