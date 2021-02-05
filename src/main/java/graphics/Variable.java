package graphics;


import graphics.tools.editbuttons.GenericEditButton;
import graphics.tools.editbuttons.ShowHideButton;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import math.Mapping;

public class Variable<T> extends HBox {
    private T variable;
    private String name;
    private final Text contentField;
    private final Text nameField;
    private final ColorPicker colorPicker;
    private final boolean isRenderable;
    //private final Pane spacer = new Pane();


    public Variable(T variable, String name){
        this.variable = variable;
        this.name = name;
        contentField = new Text(variable.toString());
        nameField = new Text(name + " =");
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

        isRenderable = variable instanceof Renderable;
    }

    public Renderable getRenderable(){
        return isRenderable ? (Renderable) variable : null;
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

    public boolean equals(Variable other){
        return other.variable.equals(variable);
    }

    public String getName(){
        return name;
    }

    public T getVariable(){
        return variable;
    }

    public void setName(String name) throws IllegalArgumentException{
        if(DefinedVariables.contains(name))
            throw new IllegalArgumentException("Name already exist!");

        DefinedVariables.remove(this);

        this.name = name;
        nameField.setText(name + " =");
        contentField.setText(variable.toString());

        DefinedVariables.add(this);
    }

    public void updateText(){
        contentField.setText(variable.toString());
    }

    public void setVariable(T variable){
        this.variable = variable;
        contentField.setText(variable.toString());
    }
}
