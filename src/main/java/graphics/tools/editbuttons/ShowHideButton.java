package graphics.tools.editbuttons;


import graphics.Renderable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.linalgfx.App;

public class ShowHideButton extends ImageView {
    private final Renderable renderable;
    private final Image visible = new Image(App.resourceURL("images/visible.png"));
    private final Image hidden = new Image(App.resourceURL("images/hidden.png"));
    public ShowHideButton(Renderable renderable){
        super();
        setImage(visible);
        System.out.println(visible.isError());
        setFitHeight(30);

        this.renderable = renderable;

        setOnMousePressed(ev ->{
            if(renderable.isHidden())
                show();
            else
                hide();
        });
    }
    public void show(){
        renderable.show();
        setImage(visible);
    }
    public void hide(){
        renderable.hide();
        setImage(hidden);
    }
}
