package graphics;


import javafx.scene.text.Text;

public class Variable<T> extends Text {
    private T variable;
    private String name;
    public Variable(T variable, String name){
        this.variable = variable;
        this.name = name;
        setText(toString());
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
}
