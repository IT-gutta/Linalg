package graphics.tools;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

import java.util.regex.Pattern;

public class ToolsButton extends MenuButton {
    private TextInputDialog dialog;


    public ToolsButton(){
        super("Tools");
        clearDialog();


    }



    private boolean validName(String name){
        return Pattern.matches("\\w[a-zA-Z0-9_]*", name);
    }

    private void clearDialog(){
        dialog = new TextInputDialog("");
        dialog.initModality(Modality.APPLICATION_MODAL);
    }
}
