package graphics.textInput;

import java.util.HashMap;
import java.util.function.BiFunction;

public class InputMapBiFunc<A, B, C>{
    private HashMap<String, BiFunction<A, B, C>> map;
    private A input1;
    private B input2;
    private C output;
    public InputMapBiFunc(A a, B b, C c){
        map = new HashMap<>();
        input1 = a;
        input2 = b;
        output = c;
    }
    public void put(String name, BiFunction<A,B,C> function){
        map.put(name, function);
    }
    public HashMap<String,BiFunction<A,B,C>> getMap(){
        return map;
    }
    public A getInput1(){
        return input1;
    }
    public B getInput2(){
        return input2;
    }
    public C getOutput(){
        return output;
    }
    public C apply(String s, A inp1, B inp2){
        return map.get(s).apply(inp1, inp2);
    }

}
