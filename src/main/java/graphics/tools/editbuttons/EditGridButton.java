package graphics.tools.editbuttons;

import graphics.math2d.Grid2;
import graphics.VariableContainer;
import graphics.tools.MenuItems;

public class EditGridButton extends GenericEditButton {
    private VariableContainer<Grid2> variableContainer;
    public EditGridButton(VariableContainer<Grid2> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
