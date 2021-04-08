package graphics.editbuttons;

import graphics.VariableContainer;
import canvas2d.Line2D;
/**
 * MenuButton with MenuItems (options) for editing 2D lines
 */
public class EditLineButton extends GenericEditButton {
    private final VariableContainer<Line2D> variableContainer;
    public EditLineButton(VariableContainer<Line2D> variableContainer) {
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.interpolateMenuItem(variableContainer));
    }
}
