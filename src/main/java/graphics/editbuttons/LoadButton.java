package graphics.editbuttons;

import javafx.scene.control.Button;
import org.linalgfx.App;

public class LoadButton extends Button {
    public LoadButton(){
        super("Load from file");
        setOnAction(a ->{
            App.loadFromFile();
        });
    }
}
