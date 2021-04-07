package graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.linalgfx.App;

/**
 * ??
 */
public abstract class Icons {
    public static ImageView of(String fileName, double size){
        try {
            ImageView imageView = new ImageView(new Image(App.resourceURL("images/" + fileName)));
            imageView.setFitHeight(size);
            imageView.setFitWidth(size);
            return imageView;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
