package org.graphics.editbuttons;

import javafx.scene.control.Alert;
import org.graphics.ModalWindow;
import org.linalgfx.DefinedVariables;
import org.graphics.Icons;
import org.utils.Interpolatable;
import org.graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import org.math.Matrix;

/**
 * Abstract helper class for different MenuItems
 */
public abstract class MenuItems {
    /**
     * Returns a new MenuItem which has the functionality to interpolate the given variable
     * Takes in only variables which have underlying 2D or 3D objects which are interpolatable
     */
    public static MenuItem interpolateMenuItem(VariableContainer<? extends Interpolatable> variableContainer){
        MenuItem transform = new MenuItem("Transform", Icons.of("transform.png", 20));
        transform.setOnAction(actionEvent ->{
            TextInputDialog dialog = new TextInputDialog();

            dialog.setHeaderText("Transform vector with a matrix");
            dialog.setContentText("Enter name of matrix");

            dialog.showAndWait().ifPresent(response ->{
                String name = dialog.getEditor().getText();
                if(DefinedVariables.contains(name)){
                    VariableContainer v = DefinedVariables.get(name);
                    if(v.getVariable() instanceof Matrix) {
                        try {
                            variableContainer.getVariable().startInterpolation((Matrix) v.getVariable(), 2000);
                        }catch (IllegalArgumentException e){
                            ModalWindow.alert("Could not transform: "+ variableContainer.getName() + " using the matrix: " + name + "\nError message: "+e.getMessage(), Alert.AlertType.ERROR);
                        }

                    }
                }
            });
        });

        return transform;
    }
}
