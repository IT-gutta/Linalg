package graphics.tools;

import graphics.DefinedVariables;
import graphics.Icons;
import graphics.Interpolatable;
import graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import math.Matrix;
import math.Transformable;

public abstract class MenuItems {

    public static MenuItem transformMenuItem(VariableContainer<? extends Interpolatable> variableContainer){
        MenuItem transform = new MenuItem("Transform", Icons.of("transform.png", 20));
        transform.setOnAction(actionEvent ->{
            TextInputDialog dialog = new TextInputDialog();

            dialog.setHeaderText("Transform vector with a matrix");
            dialog.setContentText("Enter name of matrix");

            dialog.showAndWait().ifPresent(response ->{
                String name = dialog.getEditor().getText();
                if(DefinedVariables.contains(name)){
                    VariableContainer v = DefinedVariables.get(name);
                    if(v.getVariable() instanceof Matrix)
                        variableContainer.getVariable().startInterpolation((Matrix) v.getVariable(), 1000);
                }
            });
        });

        return transform;
    }
}
