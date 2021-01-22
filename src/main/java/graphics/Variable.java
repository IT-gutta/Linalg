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
        this.text = new Text(toString());
        editButton = GenericEditButton.getEditButton(this);

        getChildren().addAll();
    }

    public void update(String name){
        this.name = name;
        text.setText(toString());
    }
    public void update(T variable){
        this.variable = variable;
        text.setText(toString());
    }
    public void update(T variable, String name){
        update(variable);
        update(name);
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
