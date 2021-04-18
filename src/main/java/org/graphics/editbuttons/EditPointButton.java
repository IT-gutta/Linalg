package org.graphics.editbuttons;

import org.graphics.VariableContainer;
import org.canvas2d.Point2D;
/**
 * MenuButton with MenuItems (options) for editing 2D points
 */
public class EditPointButton extends GenericEditButton{
    private final VariableContainer<Point2D> variableContainer;
    public EditPointButton(VariableContainer<Point2D> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;
    }
}
