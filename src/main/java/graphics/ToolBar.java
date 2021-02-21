package graphics;

import graphics.editbuttons.AddButton2D;
import graphics.editbuttons.AddButton3D;
import graphics.editbuttons.LoadButton;
import graphics.editbuttons.SaveButton;
import javafx.scene.layout.HBox;


public class ToolBar extends HBox {
    public ToolBar(){
        getChildren().addAll(new AddButton2D(), new AddButton3D(), new SaveButton(), new LoadButton());
    }
}
