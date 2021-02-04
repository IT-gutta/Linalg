package graphics.tools.editbuttons;

import graphics.Variable;
import graphics.tools.MenuItems;
import math.Grid;
import math.LineSegment;

public class EditLineSegmentButton extends GenericEditButton{
    private Variable<LineSegment> variable;
    public EditLineSegmentButton(Variable<LineSegment> variable){
        super(variable);
        this.variable = variable;

        addMenuItem(MenuItems.transformMenuItem(variable));
    }
}
