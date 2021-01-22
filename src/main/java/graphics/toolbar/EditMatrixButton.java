package graphics.toolbar;

import graphics.Variable;
import math.Matrix;

public class EditMatrixButton extends GenericEditButton<Matrix> {
    public EditMatrixButton(Variable<Matrix> variable){
        super(variable);
    }
}
