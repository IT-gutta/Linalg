package graphics.tools;

import javafx.scene.layout.HBox;


public class ToolBar extends HBox {
    public ToolBar(){
        getChildren().addAll(new NewMenuButton());
    }
}