package org.graphics;

import org.graphics.editbuttons.*;
import javafx.scene.layout.HBox;

/**
 * Creates the toolbar seen in the GUI
 */
public class ToolBar extends HBox {
    public ToolBar(){
        try {
            getChildren().addAll(new AddButton2D(), new AddButton3D(), new SaveButton(), new LoadButton(), new CameraModeButton());
        }catch (Exception e){
            System.out.println("bjartemann");
        }
    }
}
