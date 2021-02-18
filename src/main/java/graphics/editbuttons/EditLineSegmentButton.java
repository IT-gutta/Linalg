package graphics.editbuttons;

import graphics.VariableContainer;
import canvas2d.LineSegment2D;

public class EditLineSegmentButton extends GenericEditButton{
    private final VariableContainer<LineSegment2D> variableContainer;
    public EditLineSegmentButton(VariableContainer<LineSegment2D> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
