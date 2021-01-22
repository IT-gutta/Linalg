package graphics.toolbar;

import graphics.Variable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import math.Matrix;
import math.Vector;

public class GenericEditButton<T> extends MenuButton {
    private Variable<T> variable;


    public GenericEditButton(Variable<T> variable){
        super("Edit");
        this.variable = variable;
    }


    public void addMenuItem(MenuItem menuItem){
        getItems().add(menuItem);
    }


    public static <T> GenericEditButton getEditButton(Variable<T> variable){
        if(variable.getVariable() instanceof Vector)
            return new EditVectorButton((Variable<Vector>) variable);

        if(variable.getVariable() instanceof Matrix)
            return new EditMatrixButton((Variable<Matrix>) variable);

        return null;
    }
}
