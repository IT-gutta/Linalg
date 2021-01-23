package graphics.tools;

import graphics.Variable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import math.Matrix;
import math.Vector;

public class GenericEditButton extends MenuButton {
    private Variable variable;
    protected TextInputDialog dialog;

    private MenuItem deleteButton = new MenuItem("Delete");
    private MenuItem changeNameButton = new MenuItem("Edit Name");

    public GenericEditButton(Variable variable){
        super("Edit");
        this.variable = variable;

        deleteButton.setOnAction(ev ->{
            delete();
        });

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
                if(dialog.getEditor().getText().equals(""))
                    throw new IllegalArgumentException("Name cant be empty");

                if(getOwner().getName().equals(dialog.getEditor().getText()))
                    return;

                variable.setName(dialog.getEditor().getText());
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

        return new GenericEditButton(variable);
    }
}
