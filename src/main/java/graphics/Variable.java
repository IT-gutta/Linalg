package graphics;


import graphics.toolbar.GenericEditButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Variable<T> extends HBox {
    private T variable;
    private String name;
    private Text text;
    private GenericEditButton<T> editButton;


    public Variable(T variable, String name){
        this.variable = variable;
        this.name = name;
        text = new Text(toString());
        editButton = (GenericEditButton<T>) GenericEditButton.getEditButton(this);

        getChildren().addAll(text, editButton);
    }

    public void update(String name){
        DefinedVariables.remove(this);
        this.name = name;
        text.setText(toString());

        DefinedVariables.add(this);
    }

    public void update(T variable){
        DefinedVariables.remove(this);
        this.variable = variable;
        text.setText(toString());

        DefinedVariables.add(this);
    }
    public void update(T variable, String name){
        update(variable);
        update(name);
    }

    public void delete(){
        DefinedVariables.remove(getName());
    }


    @Override
    public String toString(){
        return name + " = " + variable.toString();
    }

    public boolean equals(Variable other){
        return other.getName().equals(name);
    }

    public String getName(){
        return name;
    }

    public T getVariable(){
        return variable;
    }
}
