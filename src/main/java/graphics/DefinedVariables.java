package graphics;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class DefinedVariables {
    private static VBox vbox = new VBox();
    private static ScrollPane scrollPane = new ScrollPane(vbox);
    private static Map<String, Variable> map = new HashMap<>();


    public static boolean contains(Variable variable){
        return vbox.getChildren().stream().anyMatch(other -> variable.equals(other));
    }

    public static boolean contains(Renderable renderable){
        return CanvasRenderer.contains(renderable);
    }

    public static boolean contains(String name){
        return map.containsKey(name);
    }


    public static boolean remove(String name){
        if(map.containsKey(name)){
            Variable variable = map.remove(name);
            vbox.getChildren().remove(variable);

            if(variable.getVariable() instanceof Renderable)
                CanvasRenderer.remove((Renderable) variable.getVariable());
            return true;
        }
        return false;
    }

    public static boolean remove(Variable variable){
        return remove(variable.getName());
    }




    public static boolean removeAnonymous(Renderable renderable){
        return CanvasRenderer.remove(renderable);
    }

    public static void addAnonymous(Renderable renderable){
        CanvasRenderer.add(renderable);
    }


    public static void removeAllAnonymousVariables(){
        CanvasRenderer.getList().stream().filter(renderable -> !(renderable instanceof CoordinateSystem));
    }

    public static void addAll(Variable... variables){
        for(Variable v : variables){
            add(v);
        }
    }

    public static void addAll(Renderable... renderables){
        for(Renderable r : renderables){
            addAnonymous(r);
        }
    }

    public static void add(Variable variable){
        if(contains(variable) || map.containsKey(variable.getName()))
            return;
        vbox.getChildren().add(variable);
        map.put(variable.getName(), variable);

        if(variable.getVariable() instanceof Renderable)
            CanvasRenderer.add((Renderable) variable.getVariable());
    }

    public static void add(Renderable r, String name){
        add(new Variable<>(r, name));
    }

    public static Variable get(String name){
        return map.get(name);
    }

    public static void set(String name, Variable v){
        map.put(name, v);
    }

    public static VBox getVBox(){
        return vbox;
    }

    public static ScrollPane getScrollPane() {
        return scrollPane;
    }
}
