package graphics.editbuttons;

import javafx.scene.control.Button;
import org.linalgfx.App;

public class SaveButton extends Button {
    public SaveButton(){
        super("Save to file");
        setOnAction( a ->{
            App.saveToFile();
        });
    }
}
