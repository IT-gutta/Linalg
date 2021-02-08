package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Renderer3D {
    private boolean isHidden = false;
    //TODO Add the name of an object to the object in the graphics window (as 3D text?)
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
