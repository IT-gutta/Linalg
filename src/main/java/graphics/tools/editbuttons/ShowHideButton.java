package graphics.tools.editbuttons;


import graphics.Renderable;
import graphics.Variable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.linalgfx.App;

public class ShowHideButton extends Region {
    private final Renderable renderable;
    private final Image visible = new Image(App.resourceURL("images/visible.png"));
    private final Image hidden = new Image(App.resourceURL("images/hidden.png"));
    private final ImageView imageView = new ImageView();
    public <T> ShowHideButton(T variable){
        super();
        if(variable instanceof Renderable) {
            this.renderable = (Renderable) variable;


            imageView.setImage(visible);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);

            getStyleClass().add("showhide");


            setOnMouseClicked(ev -> {
                if (renderable.isHidden())
                    show();
                else
                    hide();
            });
        }
        else {
            renderable = null;
            imageView.setImage(hidden);
            System.out.println("hei");
        }

        getChildren().add(imageView);
    }
    public void show(){
        renderable.show();
        imageView.setImage(visible);
    }
    public void hide(){
        renderable.hide();
        imageView.setImage(hidden);
    }
}
