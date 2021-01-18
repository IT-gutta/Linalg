package graphics;

import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public abstract class DefinedVariables {
    private static VBox vbox = new VBox();
    private static Map<String, Variable> map = new HashMap<>();

    public static boolean contains(Variable variable){
        return vbox.getChildren().stream().anyMatch(other -> variable.equals(other));
    }

    public static boolean remove(Variable variable){
        boolean didRemove = vbox.getChildren().remove(variable);
        if(didRemove){
            map.remove(variable.getName());
            return true;
        }
        return false;
    }



    public static void addVariables(Variable... variables){
        for(Variable v : variables){
            if(contains(v))
                continue;
            vbox.getChildren().add(v);
            map.put(v.getName(), v);
        }
    }

    public static void add(Variable variable){
        if(contains(variable))
            return;
        vbox.getChildren().add(variable);
        map.put(variable.getName(), variable);
    }

    public static VBox getVBox(){
        return vbox;
    }
}
