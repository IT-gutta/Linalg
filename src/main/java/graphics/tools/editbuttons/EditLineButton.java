package graphics.tools.editbuttons;

import graphics.VariableContainer;
import graphics.tools.MenuItems;
import math.Line;

public class EditLineButton extends GenericEditButton {
    private VariableContainer<Line> variableContainer;
    public EditLineButton(VariableContainer<Line> variableContainer) {
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
