package graphics;

import graphics.editbuttons.*;
import javafx.scene.layout.HBox;

/**
 * Creates the toolbar seen in the GUI
 */
public class ToolBar extends HBox {
    public ToolBar(){
        getChildren().addAll(new AddButton2D(), new AddButton3D(), new SaveButton(), new LoadButton(), new CameraModeButton());
    }
}
