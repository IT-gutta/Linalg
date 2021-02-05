package graphics;


import graphics.tools.editbuttons.GenericEditButton;
import graphics.tools.editbuttons.ShowHideButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import math.Mapping;

public class Variable<T> extends HBox {
    private T variable;
    private String name;
    private final Text text;
    private ShowHideButton showHideButton;
    private final GenericEditButton editButton;
    //private final Pane spacer = new Pane();


    public Variable(T variable, String name){
        this.variable = variable;
        this.name = name;
        text = new Text(toString());
        text.getStyleClass().add("variable-text");
        editButton = GenericEditButton.getEditButton(this);
        showHideButton = new ShowHideButton(variable);



        getChildren().addAll(showHideButton, editButton, text);

        getStyleClass().add("variable");
    }


    public void delete(){
        DefinedVariables.remove(getName());
    }


    @Override
    public String toString(){
        if(variable instanceof Mapping)
            return name + "(x) = " + variable.toString();

        return name + " = " + variable.toString();
    }

    public boolean equals(Variable other){
        return other.variable.equals(getVariable());
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
        text.setText(toString());

        DefinedVariables.add(this);
    }

    public void updateText(){
        text.setText(toString());
    }

    public void setVariable(T variable){
        this.variable = variable;
        text.setText(toString());
    }
}
