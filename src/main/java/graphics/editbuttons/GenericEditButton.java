package graphics.editbuttons;

import canvas2d.Grid2D;
import canvas2d.Line2D;
import canvas2d.LineSegment2D;
import canvas2d.Point2D;
import graphics.Icons;
import graphics.Interpolatable;
import graphics.SimpleDialog;
import graphics.VariableContainer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import math.*;
import regex.RegexUtils;

/**
 * Generic menubutton which has basic editing functionality for all DefinedVariables, which include editing name and deleting element
 * This class is extended for all other types of editButtons that require this basic functionality
 */
public class GenericEditButton extends MenuButton {
    private final VariableContainer variableContainer;

    public GenericEditButton(VariableContainer variableContainer){
        super("");
        setGraphic(Icons.of("settings.png", 20));
        getStyleClass().add("transparent-button");

        this.variableContainer = variableContainer;

        MenuItem deleteButton = new MenuItem("Delete", Icons.of("delete.png", 20));
        deleteButton.setOnAction(ev ->{
            delete();
        });

        MenuItem changeNameButton = new MenuItem("Edit Name", Icons.of("changename.png", 20));
        changeNameButton.setOnAction(ev ->{
            handleChangeName(false);
        });

        getItems().addAll(deleteButton, changeNameButton);


        //if transformable
        if(variableContainer.getVariable() instanceof Interpolatable)
            addMenuItem(MenuItems.interpolateMenuItem(variableContainer));
    }

    /**
     * Adds a MenuItem to the list of pickable menuitems (appears in the dropdown menu when the MenuButton is pressed)
     */
    public void addMenuItem(MenuItem menuItem){
        getItems().add(menuItem);
    }

    /**
     * Deletes the variables which is associated with this GenericEditButton
     */
    public void delete(){
        hide();
        variableContainer.delete();
    }


    /**
     * Handles the process of changing a name based on input from user
     */
    protected void handleChangeName(boolean isRetry){
        SimpleDialog dialog;
        if(isRetry)
            dialog = new SimpleDialog("Illegal name. Try again.");
        else
            dialog = new SimpleDialog("Change name.");
        dialog.showAndWait().ifPresent(response ->{
            try{
                String name = dialog.getEditor().getText();

                if(!RegexUtils.isValidName(name))
                    throw new IllegalArgumentException("Illegal name.");

                if(getContainer().getName().equals(name))
                    return;

                variableContainer.setName(name);
            }
            catch (IllegalArgumentException e){
                handleChangeName(true);
            }
        });
    }


    public VariableContainer getContainer(){
        return variableContainer;
    }

    /**
     * Static function for returning a specialized editButton based on what type of variable is in the input
     */
    public static <T> GenericEditButton getEditButton(VariableContainer<T> variableContainer){
        if(variableContainer.getVariable() instanceof Vector)
            return new EditVectorButton((VariableContainer<Vector>) variableContainer);

        if(variableContainer.getVariable() instanceof Matrix)
            return new EditMatrixButton((VariableContainer<Matrix>) variableContainer);

        if(variableContainer.getVariable() instanceof Line2D)
            return new EditLineButton((VariableContainer<Line2D>) variableContainer);

        if(variableContainer.getVariable() instanceof Grid2D)
            return new EditGridButton((VariableContainer<Grid2D>) variableContainer);

        if(variableContainer.getVariable() instanceof LineSegment2D)
            return new EditLineSegmentButton((VariableContainer<LineSegment2D>) variableContainer);

        if(variableContainer.getVariable() instanceof Point2D)
            return new EditPointButton((VariableContainer<Point2D>) variableContainer);

        return new GenericEditButton(variableContainer);
    }
}
