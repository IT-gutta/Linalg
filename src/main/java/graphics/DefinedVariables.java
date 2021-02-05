package graphics;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import math.Grid;

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


    public static boolean contains(String name){
        return map.containsKey(name);
    }


    public static boolean remove(String name){
        if(map.containsKey(name)){
            Variable variable = map.remove(name);
            vbox.getChildren().remove(variable);
            return true;
        }
        return false;
    }

    public static boolean remove(Variable variable){
        return remove(variable.getName());
    }


    public static void addAll(Variable... variables){
        for(Variable v : variables){
            add(v);
        }
    }

    public static void add(Variable variable){
        if(contains(variable) || map.containsKey(variable.getName()))
            return;
        vbox.getChildren().add(variable);
        map.put(variable.getName(), variable);
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

    public static List<Variable<Renderable>> getRenderableVariables(){
        return vbox.getChildren().stream().map(node -> (Variable) node).filter(v -> v.getRenderable()!=null && !v.getRenderable().isHidden()).map(v -> (Variable<Renderable>) v).collect(Collectors.toList());
    }

    public static void updateText(){
       vbox.getChildren().forEach(n -> ((Variable) n).updateText());
    }
}
