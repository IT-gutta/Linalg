package org.graphics.editbuttons;

import org.graphics.VariableContainer;
import org.canvas2d.LineSegment2D;
/**
 * MenuButton with MenuItems (options) for editing 2D linesegments
 */
public class EditLineSegmentButton extends GenericEditButton{
    private final VariableContainer<LineSegment2D> variableContainer;
    public EditLineSegmentButton(VariableContainer<LineSegment2D> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;
    }
}
