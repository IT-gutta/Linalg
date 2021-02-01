package graphics;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
    //TODO Add the ability for scalars to be rendered along the real axis
    //TODO Add the name of an object to the object in the graphics window
    void render(GraphicsContext gc);
    boolean isHidden();
    void show();
    void hide();
}
