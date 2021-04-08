package graphics.editbuttons;

import graphics.Icons;
import graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
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
                //TODO add alert message that the matrix is not invertible here
            }
        });

        addMenuItem(invert);
    }
}
