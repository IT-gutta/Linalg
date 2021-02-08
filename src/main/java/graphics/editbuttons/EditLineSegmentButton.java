package graphics.editbuttons;

import graphics.VariableContainer;
import math2d.LineSegment2;

public class EditLineSegmentButton extends GenericEditButton{
    private final VariableContainer<LineSegment2> variableContainer;
    public EditLineSegmentButton(VariableContainer<LineSegment2> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
