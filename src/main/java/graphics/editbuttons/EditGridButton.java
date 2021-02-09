package graphics.editbuttons;

import math2d.Grid2;
import graphics.VariableContainer;

public class EditGridButton extends GenericEditButton {
    private final VariableContainer<Grid2> variableContainer;
    public EditGridButton(VariableContainer<Grid2> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
