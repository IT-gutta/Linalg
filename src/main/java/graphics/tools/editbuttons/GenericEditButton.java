package graphics.tools.editbuttons;

import graphics.Icons;
import graphics.Renderable;
import graphics.Variable;
import javafx.scene.ImageCursor;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import math.*;
import org.linalgfx.App;
import regex.RegexUtils;

public class GenericEditButton extends MenuButton {
    private final Variable variable;
    protected TextInputDialog dialog;

    public GenericEditButton(Variable variable){
        super("");
        setGraphic(Icons.of("settings.png", 20));
        getStyleClass().add("transparent-button");

        this.variable = variable;

        MenuItem deleteButton = new MenuItem("Delete", Icons.of("delete.png", 20));
        deleteButton.setOnAction(ev ->{
            delete();
        });

        MenuItem changeNameButton = new MenuItem("Edit Name", Icons.of("changename.png", 20));
        changeNameButton.setOnAction(ev ->{
            handleChangeName(false);
        });

        getItems().addAll(deleteButton, changeNameButton);
    }


    public void addMenuItem(MenuItem menuItem){
        getItems().add(menuItem);
    }

    public void delete(){
        variable.delete();
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

                if(getOwner().getName().equals(name))
                    return;

                variable.setName(name);
            }
            catch (IllegalArgumentException e){
                handleChangeName(true);
            }
        });
    }


    public Variable getOwner(){
        return variable;
    }


    public static <T> GenericEditButton getEditButton(Variable<T> variable){
        if(variable.getVariable() instanceof Vector)
            return new EditVectorButton((Variable<Vector>) variable);

        if(variable.getVariable() instanceof Matrix)
            return new EditMatrixButton((Variable<Matrix>) variable);

        if(variable.getVariable() instanceof Line)
            return new EditLineButton((Variable<Line>) variable);

        if(variable.getVariable() instanceof Grid)
            return new EditGridButton((Variable<Grid>) variable);

        if(variable.getVariable() instanceof LineSegment)
            return new EditLineSegmentButton((Variable<LineSegment>) variable);

        if(variable.getVariable() instanceof Point)
            return new EditPointButton((Variable<Point>) variable);

        return new GenericEditButton(variable);
    }
}
