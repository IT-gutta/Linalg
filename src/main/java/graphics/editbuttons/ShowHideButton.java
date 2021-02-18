package graphics.editbuttons;


import canvas2d.Render2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.linalgfx.App;

public class ShowHideButton extends Region {
    private final Render2D render2D;
    private final Image visible = new Image(App.resourceURL("images/visible.png"));
    private final Image hidden = new Image(App.resourceURL("images/hidden.png"));
    private final ImageView imageView = new ImageView();
    public <T> ShowHideButton(T variable){
        super();
        if(variable instanceof Render2D) {
            this.render2D = (Render2D) variable;


            imageView.setImage(visible);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);

            getStyleClass().add("showhide");


            setOnMouseClicked(ev -> {
                if (render2D.isHidden())
                    show();
                else
                    hide();
            });
        }
        else {
            render2D = null;
            imageView.setImage(hidden);
        }

        getChildren().add(imageView);
    }
    public void show(){
        render2D.show();
        imageView.setImage(visible);
    }
    public void hide(){
        render2D.hide();
        imageView.setImage(hidden);
    }
}
