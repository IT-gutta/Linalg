package graphics.tools.editbuttons;

import graphics.Variable;
import graphics.tools.MenuItems;
import math.LineSegment;
import math.Point;

public class EditPointButton extends GenericEditButton{
    private Variable<Point> variable;
    public EditPointButton(Variable<Point> variable){
        super(variable);
        this.variable = variable;

        addMenuItem(MenuItems.transformMenuItem(variable));
    }
}
