package org.graphics.editbuttons;

import org.utils.DoubleFormatter;
import org.graphics.SimpleDialog;
import org.graphics.VariableContainer;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.math.Vector;
import org.linalgfx.App;
import org.utils.RegexUtils;
/**
 * MenuButton with MenuItems (options) for editing mathematical or 2D vectors
 */
public class EditVectorButton extends GenericEditButton {
    private final VariableContainer<Vector> variableContainer;
    public EditVectorButton(VariableContainer<Vector> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        MenuItem edit = new MenuItem("Edit", new ImageView(new Image(App.resourceURL("images/hammer.png"))));
        edit.setOnAction(actionEvent -> {

            TextField[] coordInputs = new TextField[variableContainer.getVariable().getDimensions()];
            for(int i = 0; i < coordInputs.length; i++){
                coordInputs[i] = DoubleFormatter.getTextField(variableContainer.getVariable().getElement(i));
            }

            SimpleDialog dialog = new SimpleDialog("Edit vector name and entries", coordInputs);

            dialog.getEditor().setText(getContainer().getName());

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
