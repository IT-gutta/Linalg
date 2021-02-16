package graphics.editbuttons;

import graphics.Icons;
import graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import math.Matrix;

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

            }
        });

        addMenuItem(invert);
    }
}
