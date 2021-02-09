package graphics;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class DefinedVariables {
    private static final VBox vbox = new VBox();
    private static final ScrollPane scrollPane = new ScrollPane(vbox);
    private static Map<String, VariableContainer> map = new HashMap<>();


    public static boolean contains(VariableContainer variableContainer){
        return vbox.getChildren().stream().anyMatch(variableContainer::equals);
    }


    public static boolean contains(String name){
        return map.containsKey(name);
    }


    public static boolean remove(String name){
        if(map.containsKey(name)){
            VariableContainer variableContainer = map.remove(name);
            vbox.getChildren().remove(variableContainer);
            return true;
        }
        return false;
    }

    public static boolean remove(VariableContainer variableContainer){
        return remove(variableContainer.getName());
    }


    public static void addAll(VariableContainer... variableContainers){
        for(VariableContainer v : variableContainers){
            add(v);
        }
    }

    public static void add(VariableContainer variableContainer){
        if(contains(variableContainer) || map.containsKey(variableContainer.getName()))
            return;
        vbox.getChildren().add(variableContainer);
        map.put(variableContainer.getName(), variableContainer);
    }

    public static void add(Renderer2D r, String name){
        add(new VariableContainer<>(r, name));
    }

    public static VariableContainer get(String name){
        return map.get(name);
    }

    public static void set(String name, VariableContainer v){
        map.put(name, v);
    }

    public static VBox getVBox(){
        return vbox;
    }

    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    public static List<VariableContainer<Renderer2D>> get2DRenderables(){
        return vbox.getChildren().stream().map(node -> (VariableContainer) node).filter(v -> v.getVariable() instanceof Renderer2D && !((Renderer2D) v.getVariable()).isHidden()).map(v -> (VariableContainer<Renderer2D>) v).collect(Collectors.toList());
    }
    public static List<VariableContainer<Renderer3D>> get3DRenderables(){
        return vbox.getChildren().stream().map(node -> (VariableContainer) node).filter(v -> v.getVariable() instanceof Renderer3D && !((Renderer3D) v.getVariable()).isHidden()).map(v -> (VariableContainer<Renderer3D>) v).collect(Collectors.toList());
    }

    public static void updateText(){
       vbox.getChildren().forEach(n -> ((VariableContainer) n).updateContentText());
    }
}
