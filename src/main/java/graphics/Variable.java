package graphics;


import javafx.scene.text.Text;

public class Variable<T> extends Text {
    private T variable;
    private String name;
    public Variable(T variable, String name){
        this.variable = variable;
        this.name = name;
    }


    @Override
    public String toString(){
        return name + " = " + variable.toString();
    }
}
