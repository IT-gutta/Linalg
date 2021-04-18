package org.graphics;

import org.canvas2d.Render2D;
import org.canvas3d.Render3D;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Keeps track of all defined variables in the program
 */
public abstract class DefinedVariables {
    private static final VBox vbox = new VBox();
    private static final ScrollPane scrollPane = new ScrollPane(vbox);
    private static Map<String, VariableContainer> map = new HashMap<>();

    /**
     * Returns true if a VariableContainer is contained in vBox, else false
     */
    public static boolean contains(VariableContainer variableContainer){
        return vbox.getChildren().stream().anyMatch(variableContainer::equals);
    }

    /**
     * Returns true if a name is already defined, else false
     */
    public static boolean contains(String name){
        return map.containsKey(name);
    }

    /**
     * Removes a variable with the given name
     */
    public static boolean remove(String name){
        if(map.containsKey(name)){
            VariableContainer variableContainer = map.remove(name);
            vbox.getChildren().remove(variableContainer);
            return true;
        }
        return false;
    }

    /**
     * Removes the given variable
     */
    public static boolean remove(VariableContainer variableContainer){
        return remove(variableContainer.getName());
    }

    /**
     * Adds an array of VariableContainers
     */
    public static void addAll(VariableContainer... variableContainers){
        for(VariableContainer v : variableContainers){
            add(v);
        }
    }

    /**
     * Adds a VariableContainer
     */
    public static void add(VariableContainer variableContainer){
        if(contains(variableContainer) || map.containsKey(variableContainer.getName()))
            return;
        vbox.getChildren().add(variableContainer);
        map.put(variableContainer.getName(), variableContainer);
    }

    /**
     * Adds a VariableContainer given an instance of Render2D and a name
     */
    public static void add(Render2D r, String name){
        add(new VariableContainer<>(r, name));
    }

    /**
     * Adds a VariableContainer given an instance Render3D and a name
     */
    public static void add(Render3D r, String name){
        add(new VariableContainer<>(r, name));
    }

    /**
     * Returns a VariableContainer with the given name if defined
     */
    public static VariableContainer get(String name){
        return map.get(name);
    }

    /**
     * Sets the name of an already defined VariableContainer
     */
    public static void set(String name, VariableContainer v){
        map.put(name, v);
    }

    /**
     * Returns the VBox
     */
    public static VBox getVBox(){
        return vbox;
    }

    /**
     * Returns the ScrollPane
     */
    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Returns all VariableContainers with a variable which is an instance of Render2D
     */
    public static List<VariableContainer<Render2D>> get2DRenderables(){
        return vbox.getChildren().stream().map(node -> (VariableContainer) node).filter(v -> v.getVariable() instanceof Render2D && !((Render2D) v.getVariable()).isHidden()).map(v -> (VariableContainer<Render2D>) v).collect(Collectors.toList());
    }

    /**
     * Returns all VariableContainers with a variable which is an instance of Render3D
     */
    public static List<VariableContainer<Render3D>> get3DRenderables(){
        return vbox.getChildren().stream().map(node -> (VariableContainer) node).filter(v -> v.getVariable() instanceof Render3D && !((Render3D) v.getVariable()).isHidden()).map(v -> (VariableContainer<Render3D>) v).collect(Collectors.toList());
    }

    /**
     * Ensures that the text visible on screen is updated with the underlying mathematical objects state
     */
    public static void updateText(){
       vbox.getChildren().forEach(n -> ((VariableContainer) n).updateContentText());
    }



    public static void addFromFile(String name, String className, String variable){
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(String.class);

            add(new VariableContainer<>(constructor.newInstance(variable), name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void clear(){
        vbox.getChildren().clear();
        map.clear();
    }
}
