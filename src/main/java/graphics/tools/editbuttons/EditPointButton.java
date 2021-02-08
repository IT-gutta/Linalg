package graphics.tools.editbuttons;

import graphics.VariableContainer;
import graphics.math2d.Point2;
import graphics.tools.MenuItems;
import math.Point;

public class EditPointButton extends GenericEditButton{
    private VariableContainer<Point2> variableContainer;
    public EditPointButton(VariableContainer<Point2> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
