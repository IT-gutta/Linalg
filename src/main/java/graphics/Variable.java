package graphics;


import graphics.tools.GenericEditButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Variable<T> extends HBox {
    private T variable;
    private String name;
    private Text text;
    private GenericEditButton editButton;


    public Variable(T variable, String name){
        this.variable = variable;
        this.name = name;
        text = new Text(toString());
        editButton = GenericEditButton.getEditButton(this);

        getChildren().add(text);

        if(editButton != null)
            getChildren().add(editButton);
    }





    public void delete(){
        DefinedVariables.remove(getName());
    }


    @Override
    public String toString(){
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
