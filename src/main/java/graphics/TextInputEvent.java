package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import math.Matrix;
import math.Point;
import math.Vector;
import math.Vectors;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;

public class TextInputEvent implements EventHandler<ActionEvent> {
    private TextField inputField;
    private Matcher m;
    private final Pattern vector = Pattern.compile("(\\w[0-9a-zA-Z_]*)(\\s*=\\s*\\[\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*,\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*])");
    private final Pattern point = Pattern.compile("(\\w[0-9a-zA-Z_]*)(\\s*=\\s*\\(\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*,\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*\\))");
    public TextInputEvent(TextField inputField) {
        this.inputField = inputField;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String inp = inputField.getText().replace(" ", "");
        if(Pattern.matches(vector.toString(), inp)){
            m = vector.matcher(inp);
            if(m.find()){
                System.out.println("found vector");
                DefinedVariables.add(new Vector(Double.parseDouble(m.group(3)), Double.parseDouble(m.group(6))), m.group(1));
            }
        }
        if(Pattern.matches(point.toString(), inp)){
            m = point.matcher(inp);
            if(m.find()){
                DefinedVariables.add(new Point(Double.parseDouble(m.group(3)), Double.parseDouble(m.group(6))), m.group(1));
            }
        }
        inputField.clear();
    }
}
