package graphics.editbuttons;

import canvas2d.Grid2D;
import graphics.VariableContainer;

public class EditGridButton extends GenericEditButton {
    private final VariableContainer<Grid2D> variableContainer;
    public EditGridButton(VariableContainer<Grid2D> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
