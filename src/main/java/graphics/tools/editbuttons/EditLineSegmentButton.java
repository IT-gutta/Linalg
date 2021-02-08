package graphics.tools.editbuttons;

import graphics.VariableContainer;
import graphics.math2d.LineSegment2;
import graphics.tools.MenuItems;

public class EditLineSegmentButton extends GenericEditButton{
    private VariableContainer<LineSegment2> variableContainer;
    public EditLineSegmentButton(VariableContainer<LineSegment2> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
