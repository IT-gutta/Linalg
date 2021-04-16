package graphics.editbuttons;

import graphics.Icons;
import graphics.ModalWindow;
import graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import math.Matrix;
/**
 * MenuButton with MenuItems (options) for editing 2x2 matrices
 */
public class EditMatrixButton extends GenericEditButton {
    private final VariableContainer<Matrix> variableContainer;
    public EditMatrixButton(VariableContainer<Matrix> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;
        MenuItem invert = new MenuItem("Invert", Icons.of("inverse.png", 20));
        invert.setOnAction(actionEvent ->{
            try{
                variableContainer.getVariable().invert();
            }
            catch (Exception e){
                ModalWindow.alert("Matrix cannot be inverted. The determinant is 0.", javafx.scene.control.Alert.AlertType.ERROR);
            }
        });

        addMenuItem(invert);
    }
}
