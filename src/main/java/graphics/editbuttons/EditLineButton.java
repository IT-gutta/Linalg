package graphics.editbuttons;

import graphics.VariableContainer;
import math2d.Line2;

public class EditLineButton extends GenericEditButton {
    private final VariableContainer<Line2> variableContainer;
    public EditLineButton(VariableContainer<Line2> variableContainer) {
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
