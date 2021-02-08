package graphics.editbuttons;


import graphics.Renderer2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.linalgfx.App;

public class ShowHideButton extends Region {
    private final Renderer2D renderer2D;
    private final Image visible = new Image(App.resourceURL("images/visible.png"));
    private final Image hidden = new Image(App.resourceURL("images/hidden.png"));
    private final ImageView imageView = new ImageView();
    public <T> ShowHideButton(T variable){
        super();
        if(variable instanceof Renderer2D) {
            this.renderer2D = (Renderer2D) variable;


            imageView.setImage(visible);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);

            getStyleClass().add("showhide");


            setOnMouseClicked(ev -> {
                if (renderer2D.isHidden())
                    show();
                else
                    hide();
            });
        }
        else {
            renderer2D = null;
            imageView.setImage(hidden);
            System.out.println("hei");
        }

        getChildren().add(imageView);
    }
    public void show(){
        renderer2D.show();
        imageView.setImage(visible);
    }
    public void hide(){
        renderer2D.hide();
        imageView.setImage(hidden);
    }
}
