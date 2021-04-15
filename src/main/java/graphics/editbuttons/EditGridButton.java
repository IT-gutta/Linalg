package graphics.editbuttons;

import canvas2d.Grid2D;
import graphics.VariableContainer;
/**
 * MenuButton with MenuItems (options) for editing 2D grids
 */
public class EditGridButton extends GenericEditButton {
    private final VariableContainer<Grid2D> variableContainer;
    public EditGridButton(VariableContainer<Grid2D> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;
    }
}
