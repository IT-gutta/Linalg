package graphics;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import math.Vector;
import math.Vectors;
import org.linalgfx.App;

public class AddVariableEvent implements EventHandler<ActionEvent> {
    private TextField inputField;

    public AddVariableEvent(TextField inputField) {
        this.inputField = inputField;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String inp = inputField.getText();
        String name = inp.split("=")[0];
        Vector vector = Vectors.parseVector(inp.split("=")[1]);

        App.addVariables(new Variable<Vector>(vector, name));
    }
}
