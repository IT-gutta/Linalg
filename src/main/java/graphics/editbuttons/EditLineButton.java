package graphics.editbuttons;

import graphics.VariableContainer;
import canvas2d.Line2D;

public class EditLineButton extends GenericEditButton {
    private final VariableContainer<Line2D> variableContainer;
    public EditLineButton(VariableContainer<Line2D> variableContainer) {
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
