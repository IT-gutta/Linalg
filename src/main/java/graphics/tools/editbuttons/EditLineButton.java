package graphics.tools.editbuttons;

import graphics.Variable;
import graphics.tools.MenuItems;
import math.Line;

public class EditLineButton extends GenericEditButton {
    private Variable<Line> variable;
    public EditLineButton(Variable<Line> variable) {
        super(variable);
        this.variable = variable;

        addMenuItem(MenuItems.transformMenuItem(variable));
    }
}
