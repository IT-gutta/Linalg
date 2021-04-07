package graphics.editbuttons;

import javafx.scene.control.Button;
import org.linalgfx.App;

/**
 * Represents the button used to load a file into the program
 */
public class LoadButton extends Button {
    public LoadButton(){
        super("Load from file");
        setOnAction(a ->{
            App.loadFromFile();
        });
    }
}
