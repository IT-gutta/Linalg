package graphics.tools.editbuttons;

import graphics.Icons;
import graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import math.Matrix;

public class EditMatrixButton extends GenericEditButton {
    private VariableContainer<Matrix> variableContainer;
    public EditMatrixButton(VariableContainer<Matrix> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;
        MenuItem invert = new MenuItem("Invert", Icons.of("inverse.png", 20));
        invert.setOnAction(actionEvent ->{
            variableContainer.getVariable().invert2x2();
        });

        addMenuItem(invert);
    }
}
