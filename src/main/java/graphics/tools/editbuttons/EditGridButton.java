package graphics.tools.editbuttons;

import math.Grid;
import graphics.VariableContainer;
import graphics.tools.MenuItems;

public class EditGridButton extends GenericEditButton {
    private VariableContainer<Grid> variableContainer;
    public EditGridButton(VariableContainer<Grid> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
