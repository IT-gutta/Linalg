package org.graphics.textInput;

/**
 * Stores some regexes used in parsing
 */
public class Regexes {
    public static final String flNum = "(-?[0-9]+(\\.?[0-9]+)?)";
    public static final String posFlNum = "([0-9]+(\\.?[0-9]+)?)";
    public static final String varName = "(\\w[0-9a-zA-Z_]*)";
    public static final String varDec = "(\\w[0-9a-zA-Z_]*)=";
    public static final String funDec = "(\\w[0-9a-zA-Z_]*)\\(x\\)=";

    private static String list(int n, String s){
        String r = s;
        for(int i = 0; i<n-1; i++)
            r+=","+s;
        return r;
    }

    public static String vector(int n){
        return "\\["+list(n, flNum)+"\\]";
    }

    public static String point(int n){
        return "\\("+list(n, flNum)+"\\)";
    }

    public static String vectorN(){
        return "\\[("+flNum+"(,("+flNum+"))*)\\]";
    }

    public static String pointN(){
        return "\\(("+flNum+"(,("+flNum+"))*)\\)";
    }

    public static String matrix(){
        return "\\[("+flNum+"(,("+flNum+"))*(;"+flNum+"(,("+flNum+"))*)+)\\]";
    }

    public static String scalar(){
        return flNum;
    }

    public static String complex(){
        return "(("+flNum+"([+-]"+posFlNum+"?)i)|((-?"+posFlNum+"?)i([+-]"+posFlNum+"))|((-?"+posFlNum+"?)i))";
    }

    public static void main(String[] args) {

    }

}
