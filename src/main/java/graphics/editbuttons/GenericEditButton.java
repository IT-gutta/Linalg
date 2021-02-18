package graphics.editbuttons;

import canvas2d.Grid2D;
import canvas2d.Line2D;
import canvas2d.LineSegment2D;
import canvas2d.Point2D;
import graphics.Icons;
import graphics.Interpolatable;
import graphics.VariableContainer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import math.*;
import regex.RegexUtils;

public class GenericEditButton extends MenuButton {
    private final VariableContainer variableContainer;
    protected TextInputDialog dialog;

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
            addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }


    public void addMenuItem(MenuItem menuItem){
        getItems().add(menuItem);
    }

    public void delete(){
        variableContainer.delete();
    }

    protected void clearDialog(){
        dialog = new TextInputDialog("");
        dialog.initModality(Modality.APPLICATION_MODAL);
    }

    protected void handleChangeName(boolean isRetry){
        clearDialog();


        if(isRetry)
            dialog.setHeaderText("Illegal name. Try again.");
        else
            dialog.setHeaderText("Change Name");

        dialog.setContentText("Name: ");

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
