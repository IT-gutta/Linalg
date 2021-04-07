package graphics.editbuttons;

import javafx.scene.control.Button;
import org.linalgfx.App;

/**
 * Represents the button used save the state of the program
 */
public class SaveButton extends Button {
    public SaveButton(){
        super("Save to file");
        setOnAction( a ->{
            App.saveToFile();
        });
    }
}
