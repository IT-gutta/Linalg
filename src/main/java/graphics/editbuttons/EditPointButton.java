package graphics.editbuttons;

import graphics.VariableContainer;
import math2d.Point2;

public class EditPointButton extends GenericEditButton{
    private final VariableContainer<Point2> variableContainer;
    public EditPointButton(VariableContainer<Point2> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
