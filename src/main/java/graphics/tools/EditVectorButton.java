package graphics.tools;

import graphics.DefinedVariables;
import graphics.Variable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import math.Matrix;
import math.Vector;

public class EditVectorButton extends GenericEditButton<Vector> {
    public EditVectorButton(Variable<Vector> variable){
        super(variable);

        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(actionEvent -> {
            clearDialog();

            var vectorInput = new HBox();
            TextField xInput = DoubleFormatter.getTextField(getOwner().getVariable().getElement(0));
            TextField yInput = DoubleFormatter.getTextField(getOwner().getVariable().getElement(1));

            dialog.getEditor().setText(getOwner().getName());

            vectorInput.getChildren().addAll(new Text("Enter x, y :   "), xInput, yInput);
            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("Edit vector");
            dialog.setContentText("Enter name:");


            dialog.showAndWait().ifPresent(response ->{
                getOwner().getVariable().setElement(0, (double) xInput.getTextFormatter().getValue());
                getOwner().getVariable().setElement(1, (double) yInput.getTextFormatter().getValue());
                getOwner().updateText();



                String name = dialog.getEditor().getText();
                if(!name.equals(getOwner().getName())){
                    try{
                        if(name.equals(""))
                            throw new IllegalArgumentException("Name cant be empty");

                        if(getOwner().getName().equals(name))
                            return;
                        getOwner().setName(name);
                    }
                    catch (IllegalArgumentException e){
                        handleChangeName(true);
                    }
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
                        getOwner().getVariable().applyTransformation((Matrix) v.getVariable());
                }
            });

        });




        addMenuItem(edit);
        addMenuItem(transform);
    }
}
