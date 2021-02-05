package graphics.tools.editbuttons;

import graphics.Icons;
import graphics.Variable;
import javafx.scene.control.MenuItem;
import math.Matrix;

public class EditMatrixButton extends GenericEditButton {
    private Variable<Matrix> variable;
    public EditMatrixButton(Variable<Matrix> variable){
        super(variable);
        this.variable = variable;
        MenuItem invert = new MenuItem("Invert", Icons.of("inverse.png", 20));
        invert.setOnAction(actionEvent ->{
            variable.getVariable().invert2x2();
        });

        addMenuItem(invert);
    }
}
