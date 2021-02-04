package graphics.tools;

import graphics.DefinedVariables;
import graphics.Renderable;
import graphics.Variable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import math.Matrix;
import math.Transformable;

public abstract class MenuItems {

    public static MenuItem transformMenuItem(Variable<? extends Transformable> variable){
        MenuItem transform = new MenuItem("Transform");
        transform.setOnAction(actionEvent ->{
            TextInputDialog dialog = new TextInputDialog();

            dialog.setHeaderText("Transform vector with a matrix");
            dialog.setContentText("Enter name of matrix");

            dialog.showAndWait().ifPresent(response ->{
                String name = dialog.getEditor().getText();
                if(DefinedVariables.contains(name)){
                    Variable v = DefinedVariables.get(name);
                    if(v.getVariable() instanceof Matrix)
                        variable.getVariable().transform((Matrix) v.getVariable());
                }
            });
        });

        return transform;
    }
}
