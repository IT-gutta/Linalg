package graphics.tools.editbuttons;

import graphics.Variable;
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
    private Variable<Vector> variable;
    public EditVectorButton(Variable<Vector> variable){
        super(variable);
        this.variable = variable;

        MenuItem edit = new MenuItem("Edit", new ImageView(new Image(App.resourceURL("images/hammer.png"))));
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
        addMenuItem(MenuItems.transformMenuItem(variable));
    }
}
