package graphics.tools.editbuttons;

import math.Grid;
import graphics.Variable;
import graphics.tools.MenuItems;

public class EditGridButton extends GenericEditButton {
    private Variable<Grid> variable;
    public EditGridButton(Variable<Grid> variable){
        super(variable);
        this.variable = variable;

        addMenuItem(MenuItems.transformMenuItem(variable));
    }
}
