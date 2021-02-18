package graphics.editbuttons;

import graphics.DoubleFormatter;
import graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import math.Vector;
import org.linalgfx.App;
import regex.RegexUtils;

public class EditVectorButton extends GenericEditButton {
    private final VariableContainer<Vector> variableContainer;
    public EditVectorButton(VariableContainer<Vector> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        MenuItem edit = new MenuItem("Edit", new ImageView(new Image(App.resourceURL("images/hammer.png"))));
        edit.setOnAction(actionEvent -> {
            clearDialog();

            var vectorInput = new HBox();
            TextField[] coordInputs = new TextField[variableContainer.getVariable().getDimensions()];
            for(int i = 0; i < coordInputs.length; i++){
                coordInputs[i] = DoubleFormatter.getTextField(variableContainer.getVariable().getElement(i));
            }

            dialog.getEditor().setText(getContainer().getName());

            vectorInput.getChildren().add(new Text("Enter x, y :   "));
            vectorInput.getChildren().addAll(coordInputs);

            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("Edit vector");
            dialog.setContentText("Enter name:");


            dialog.showAndWait().ifPresent(response ->{
                for(int i = 0; i < coordInputs.length; i++){
                    variableContainer.getVariable().setElement(i, (double) coordInputs[i].getTextFormatter().getValue());
                }
                getContainer().updateContentText();


                String name = dialog.getEditor().getText();
                if(name.equals(getContainer().getName()))
                    return;

                try{
                    if(!RegexUtils.isValidName(name))
                        throw new IllegalArgumentException("Illegal name.");

                    getContainer().setName(name);
                }
                catch (IllegalArgumentException e){
                    handleChangeName(true);
                }
            });
        });


        addMenuItem(edit);
    }
}
