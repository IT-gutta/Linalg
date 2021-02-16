package graphics.textInput;

import java.util.HashMap;
import java.util.function.Function;

public class InputMapFunc<A,B> {
    private HashMap<String, Function<A, B>> map;
    private A input;
    private B output;
    public InputMapFunc(A a, B b){
        map = new HashMap<>();
        input = a;
        output = b;
    }
    public void put(String name, Function<A,B> function){
        map.put(name, function);
    }
    public HashMap<String,Function<A,B>> getMap(){
        return map;
    }
    public A getInput(){
        return input;
    }
    public B getOutput(){
        return output;
    }
    public B apply(String s, A inp1){
        return map.get(s).apply(inp1);
    }
}
