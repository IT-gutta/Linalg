package graphics.toolbar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;


public class ToolBar extends HBox {
    public ToolBar(){
        getChildren().addAll(new NewMenuButton());
    }
}
