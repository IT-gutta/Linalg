package graphics.tools;

import graphics.DefinedVariables;
import graphics.Variable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.ImageInput;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import math.Matrix;
import math.Vector;
import regex.RegexUtils;

import java.util.regex.Pattern;

public class EditVectorButton extends GenericEditButton {
    private Variable<Vector> variable;
    public EditVectorButton(Variable<Vector> variable){
        super(variable);
        this.variable = variable;

        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(actionEvent -> {
            clearDialog();

            var vectorInput = new HBox();
            TextField xInput = DoubleFormatter.getTextField(variable.getVariable().getElement(0));
            TextField yInput = DoubleFormatter.getTextField(variable.getVariable().getElement(1));

            dialog.getEditor().setText(getOwner().getName());

            vectorInput.getChildren().addAll(new Text("Enter x, y :   "), xInput, yInput);
            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("Edit vector");
            dialog.setContentText("Enter name:");


            dialog.showAndWait().ifPresent(response ->{
                variable.getVariable().setElement(0, (double) xInput.getTextFormatter().getValue());
                variable.getVariable().setElement(1, (double) yInput.getTextFormatter().getValue());
                getOwner().updateText();



                String name = dialog.getEditor().getText();
                if(name.equals(getOwner().getName()))
                    return;

                try{
                    if(!RegexUtils.isValidName(name))
                        throw new IllegalArgumentException("Illegal name.");

                    getOwner().setName(name);
                }
                catch (IllegalArgumentException e){
                    handleChangeName(true);
                }
            });
        });


        MenuItem transform = new MenuItem("Transform");
        transform.setOnAction(actionEvent ->{
            clearDialog();

            dialog.setHeaderText("Transform vector with a matrix");
            dialog.setContentText("Enter name of matrix");

            dialog.showAndWait().ifPresent(response ->{
                String name = dialog.getEditor().getText();
                if(DefinedVariables.contains(name)){
                    Variable v = DefinedVariables.get(name);
                    if(v.getVariable() instanceof Matrix)
                        variable.getVariable().applyTransformation((Matrix) v.getVariable());
                }
            });
        });



        addMenuItem(edit);
        addMenuItem(transform);
    }
}
