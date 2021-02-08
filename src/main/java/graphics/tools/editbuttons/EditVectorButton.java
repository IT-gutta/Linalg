package graphics.tools.editbuttons;

import graphics.VariableContainer;
import graphics.math2d.Vector2;
import graphics.tools.DoubleFormatter;
import graphics.tools.MenuItems;
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
    private VariableContainer<Vector2> variableContainer;
    public EditVectorButton(VariableContainer<Vector2> variableContainer){
        super(variableContainer);
        this.variableContainer = variableContainer;

        MenuItem edit = new MenuItem("Edit", new ImageView(new Image(App.resourceURL("images/hammer.png"))));
        edit.setOnAction(actionEvent -> {
            clearDialog();

            var vectorInput = new HBox();
            TextField xInput = DoubleFormatter.getTextField(variableContainer.getVariable().getX());
            TextField yInput = DoubleFormatter.getTextField(variableContainer.getVariable().getY());

            dialog.getEditor().setText(getOwner().getName());

            vectorInput.getChildren().addAll(new Text("Enter x, y :   "), xInput, yInput);
            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("Edit vector");
            dialog.setContentText("Enter name:");


            dialog.showAndWait().ifPresent(response ->{
                variableContainer.getVariable().setX((double) xInput.getTextFormatter().getValue());
                variableContainer.getVariable().setY((double) yInput.getTextFormatter().getValue());
                getOwner().updateContentText();



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


        addMenuItem(edit);
        addMenuItem(MenuItems.transformMenuItem(variableContainer));
    }
}
