package graphics;

import graphics.editbuttons.*;
import javafx.scene.layout.HBox;


public class ToolBar extends HBox {
    public ToolBar(){
        getChildren().addAll(new AddButton2D(), new AddButton3D(), new SaveButton(), new LoadButton(), new CameraModeButton());
    }
}
