package graphics.toolbar;

import graphics.DefinedVariables;
import graphics.Variable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import math.Matrix;
import math.Vector;

public class GenericEditButton<T> extends MenuButton {
    private Variable<T> variable;
    private TextInputDialog dialog = new TextInputDialog("");

    private MenuItem deleteButton = new MenuItem("Delete");
    private MenuItem changeNameButton = new MenuItem("Edit Name");

    public GenericEditButton(Variable<T> variable){
        super("Edit");
        dialog.initModality(Modality.APPLICATION_MODAL);

        this.variable = variable;

        deleteButton.setOnAction(ev ->{
            delete();
        });

        changeNameButton.setOnAction(ev ->{
            dialog.setHeaderText("Change Name");
            dialog.setContentText("Name: ");

            dialog.showAndWait().ifPresent(response ->{
                variable.update(dialog.getEditor().getText());
            });
        });



        getItems().addAll(deleteButton, changeNameButton);
    }


    public void addMenuItem(MenuItem menuItem){
        getItems().add(menuItem);
    }

    public void delete(){
        variable.delete();
    }


    public static <T> GenericEditButton getEditButton(Variable<T> variable){
        if(variable.getVariable() instanceof Vector)
            return new EditVectorButton((Variable<Vector>) variable);

        if(variable.getVariable() instanceof Matrix)
            return new EditMatrixButton((Variable<Matrix>) variable);

        return null;
    }
}
