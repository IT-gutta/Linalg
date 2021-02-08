package graphics.tools.editbuttons;

import graphics.VariableContainer;
import graphics.tools.MenuItems;
import math.Point;

public class EditPointButton extends GenericEditButton{
    private VariableContainer<Point> variableContainer;
    public EditPointButton(VariableContainer<Point> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
