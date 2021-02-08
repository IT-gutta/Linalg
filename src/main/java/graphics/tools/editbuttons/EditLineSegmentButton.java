package graphics.tools.editbuttons;

import graphics.VariableContainer;
import graphics.tools.MenuItems;
import math.LineSegment;

public class EditLineSegmentButton extends GenericEditButton{
    private VariableContainer<LineSegment> variableContainer;
    public EditLineSegmentButton(VariableContainer<LineSegment> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
