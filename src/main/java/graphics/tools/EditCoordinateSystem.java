package graphics.tools;

import graphics.Grid;
import graphics.Variable;

public class EditCoordinateSystem extends GenericEditButton{
    private Variable<Grid> variable;
    public EditCoordinateSystem(Variable<Grid> variable){
        super(variable);
        this.variable = variable;

        addMenuItem(MenuItems.transformMenuButton(variable));
    }

}
