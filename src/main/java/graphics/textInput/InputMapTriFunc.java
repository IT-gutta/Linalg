package graphics.textInput;

import math.TriFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

public class InputMapTriFunc<A,B,C,D>{
    private final HashMap<String, TriFunction<A, B, C, D>> map;
    private final A input1;
    private final B input2;
    private final C input3;
    private final D output;
    public InputMapTriFunc(A a, B b, C c, D d){
        map = new HashMap<>();
        input1 = a;
        input2 = b;
        input3 = c;
        output = d;
    }
    public void put(String name, TriFunction<A,B,C,D> function){
        map.put(name, function);
    }
    public HashMap<String,TriFunction<A,B,C,D>> getMap(){
        return map;
    }
    public A getInput1(){
        return input1;
    }
    public B getInput2(){
        return input2;
    }
    public C getInput3(){
        return input3;
    }
    public D getOutput(){
        return output;
    }
    public D apply(String s, A inp1, B inp2, C inp3){
        return map.get(s).apply(inp1, inp2, inp3);
    }
}
