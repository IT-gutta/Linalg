package graphics.tools.editbuttons;

import graphics.VariableContainer;
import graphics.math2d.Line2;
import graphics.tools.MenuItems;
import math.Line;

public class EditLineButton extends GenericEditButton {
    private VariableContainer<Line2> variableContainer;
    public EditLineButton(VariableContainer<Line2> variableContainer) {
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
