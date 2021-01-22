package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import math.Matrix;
import math.Point;
import math.Vector;
import math.Vectors;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;

public class TextInputEvent implements EventHandler<ActionEvent> {
    private TextField inputField;
    private Matcher m;

    private final String varDec = "(\\w[0-9a-zA-z])*\\s*=\\s*";
    private final String vecCon =  "\\[\\s*([0-9]+(\\.?[0-9+]+)?)(\\s*,\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*])";

    private final Pattern vector = Pattern.compile("(\\w[0-9a-zA-Z_]*)(\\s*=\\s*\\[\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*,\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*])");
    private final Pattern point = Pattern.compile("(\\w[0-9a-zA-Z_]*)(\\s*=\\s*\\(\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*,\\s*)([0-9]+(\\.?[0-9+]+)?)(\\s*\\))");

    private static HashMap<String, BiFunction<Vector, Vector, Vector>> vvvOps = new HashMap<>();
    private static HashMap<String, BiFunction<Vector, Vector, Double>> vvdOps = new HashMap<>();
    private static HashMap<String, BiFunction<Vector, Double, Vector>> vdvOps = new HashMap<>();

    public TextInputEvent(TextField inputField) {
        this.inputField = inputField;
    }

    public static void fillOpMaps(){
        vvvOps.put("add", Vectors::add);
        vvdOps.put("dot", Vectors::dot); vvdOps.put("angle", Vectors::angle);
        vdvOps.put("scale", Vectors::scale);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String inp = inputField.getText().replace(" ", "");
        //if(Pattern.matches("\\w[a-zA-Z_]*\\s*=.*", inp))
        if(Pattern.matches(varDec+vecCon, inp)){
            m = Pattern.compile(varDec+vecCon).matcher(inp);
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
