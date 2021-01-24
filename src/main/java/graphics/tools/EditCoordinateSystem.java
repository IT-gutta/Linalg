package graphics.tools;

import graphics.CoordinateSystem;
import graphics.DefinedVariables;
import graphics.Variable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import math.Matrix;
import math.Vector;

public class EditCoordinateSystem extends GenericEditButton{
    private Variable<CoordinateSystem> variable;
    public EditCoordinateSystem(Variable<CoordinateSystem> variable){
        super(variable);
        this.variable = variable;

        addMenuItem(MenuItems.transformMenuButton(variable));
    }

}
