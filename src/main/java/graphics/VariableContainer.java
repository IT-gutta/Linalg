package graphics;


import canvas2d.Renderer2D;
import canvas3d.Renderer3D;
import graphics.editbuttons.GenericEditButton;
import graphics.editbuttons.ShowHideButton;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import math2d.Mapping;

public class VariableContainer<T> extends HBox {
    private T variable;
    private String name;
    private final Text contentField;
    private final Text nameField;
    private final ColorPicker colorPicker;
    //private final Pane spacer = new Pane();


    public VariableContainer(T variable, String name){
        this.variable = variable;
        this.name = name;
        contentField = new Text();
        nameField = new Text();
        updateContentText();
        updateNameText();
        nameField.getStyleClass().add("variable-name");
        contentField.getStyleClass().add("variable-content");
        HBox nameWrapper = new HBox(nameField, contentField);
        nameWrapper.getStyleClass().add("variable-text");

        GenericEditButton editButton = GenericEditButton.getEditButton(this);
        ShowHideButton showHideButton = new ShowHideButton(variable);

        colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.getStyleClass().add("transparent-button");
        colorPicker.setPrefWidth(32.5);

        //DefinedVariables.getVBox().getChildren().add(nameWrapper);


        getChildren().addAll(showHideButton, colorPicker, editButton, nameWrapper);

        getStyleClass().add("variable");
    }


    public void delete(){
        DefinedVariables.remove(getName());
    }

    public Paint getPaint(){
        return Paint.valueOf(colorPicker.getValue().toString());
    }
    @Override
    public String toString(){
        if(variable instanceof Mapping)
            return name + "(x) = " + variable.toString();

        return name + " = " + variable.toString();
    }

    public boolean equals(VariableContainer other){
        return other.variable.equals(variable);
    }

    public String getName(){
        return name;
    }

    public T getVariable(){
        return variable;
    }

    public Object getMath(){
        if(variable instanceof Renderer3D)
            return ((Renderer3D<?>) variable).getMath();
        else if(variable instanceof Renderer2D)
            return ((Renderer2D<?>) variable).getMath();
        else
            return getVariable();
    }

    public void setName(String name) throws IllegalArgumentException{
        if(DefinedVariables.contains(name))
            throw new IllegalArgumentException("Name already exist!");

        DefinedVariables.remove(this);

        this.name = name;
        updateNameText();
        updateContentText();

        DefinedVariables.add(this);
    }

    public void updateContentText(){
        contentField.setText(variable.toString());
    }

    private void updateNameText(){
        String[] classPath = variable.getClass().getName().split("\\.");
        nameField.setText("(" + classPath[classPath.length-1] + ")\t" + name);
    }

    public void setVariable(T variable){
        this.variable = variable;
        contentField.setText(variable.toString());
    }
}
