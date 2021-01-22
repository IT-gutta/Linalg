package graphics;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class DefinedVariables {
    private static VBox vbox = new VBox();
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
        boolean didRemove = vbox.getChildren().stream()
                .filter(n -> {
                    if(((Variable) n).getName().equals(name)){
                        vbox.getChildren().remove(n);
                        return true;
                    }
                    return false;
                })
                .count() > 0;

        if(didRemove){
            map.remove(name);
            return true;
        }
        return false;
    }


    public static boolean removeAnonymous(Renderable renderable){
        List<Variable> removedVariables = new ArrayList<>();
        vbox.getChildren().stream()
                .forEach(n -> {
                    if(((Variable) n).getVariable().equals(renderable)){
                        vbox.getChildren().remove(n);
                        removedVariables.add((Variable) n);
                    }
                });

        for(Variable v : removedVariables){
            map.remove(v.getName());
        }

        if(removedVariables.stream()
                .filter(v -> v instanceof Renderable)
                .anyMatch(v -> CanvasRenderer.remove((Renderable) v.getVariable())))
            {return true;}


        removedVariables.stream()
                .filter(v -> v instanceof Renderable)
                .forEach(v ->{
                    CanvasRenderer.remove((Renderable) v.getVariable());
        });


        return removedVariables.size() > 0;
    }


    public static boolean remove(Variable variable){
        boolean didRemove = vbox.getChildren().remove(variable);
        if(didRemove){
            map.remove(variable.getName());
            return true;
        }
        return false;
    }

    public static void addAnonymous(Renderable renderable){
        CanvasRenderer.add(renderable);
    }


    public static void removeAllAnonymousVariables(){
        CanvasRenderer.getList().clear();
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
}
