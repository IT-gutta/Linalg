package org.graphics.editbuttons;

import javafx.scene.control.Alert;
import org.canvas2d.*;
import org.canvas3d.Point3D;
import org.canvas3d.Vector3D;
import org.graphics.Icons;
import org.graphics.ModalWindow;
import org.math3d.Vector3;
import org.utils.Interpolatable;
import org.graphics.SimpleDialog;
import org.graphics.VariableContainer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.math.*;
import org.utils.RegexUtils;

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
            handleChangeName();
        });

        getItems().addAll(deleteButton, changeNameButton);


        //if transformable
        if(variableContainer.getVariable() instanceof Interpolatable)
            addMenuItem(MenuItems.interpolateMenuItem(variableContainer));

        if(variableContainer.getVariable() instanceof Editable)
            addMenuItem(MenuItems.editDoubleArrayMenuItem(variableContainer ));
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
    protected void handleChangeName(){
        SimpleDialog dialog = new SimpleDialog("Change name");
        dialog.getEditor().setText(getContainer().getName());
        dialog.showAndWait().ifPresent(response ->{
            try{
                String name = dialog.getEditor().getText();

                if(getContainer().getName().equals(name))
                    return;

                if(!RegexUtils.isValidName(name)) {
                    ModalWindow.alert("The name is invalid! Name must start with a letter, and cant include any spaces.", Alert.AlertType.ERROR);
                    handleChangeName();
                }

                variableContainer.setName(name);
            }
            catch (IllegalArgumentException e){
                ModalWindow.alert("That name is already in use!", Alert.AlertType.ERROR);
                handleChangeName();
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

        if(variableContainer.getVariable() instanceof Matrix)
            return new EditMatrixButton((VariableContainer<Matrix>) variableContainer);

        if(variableContainer.getVariable() instanceof Line2D)
            return new EditLineButton((VariableContainer<Line2D>) variableContainer);

        if(variableContainer.getVariable() instanceof Grid2D)
            return new EditGridButton((VariableContainer<Grid2D>) variableContainer);

        if(variableContainer.getVariable() instanceof LineSegment2D)
            return new EditLineSegmentButton((VariableContainer<LineSegment2D>) variableContainer);


        return new GenericEditButton(variableContainer);
    }
}
