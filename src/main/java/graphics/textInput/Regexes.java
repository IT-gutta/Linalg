package graphics.textInput;

import java.util.regex.Pattern;

public class Regexes {
    private static final String flNum = "(-?[0-9]+(\\.?[0-9]+)?)";
    private static final String posFlNum = "([0-9]+(\\.?[0-9]+)?)";
    private static final String varName = "(\\w[0-9a-zA-Z_]*)";
    private static final String varDec = "(\\w[0-9a-zA-Z_]*)=";
    private static final String funDec = "(\\w[0-9a-zA-Z_]*)\\(x\\)=";

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
        return "\\["+flNum+"(,("+flNum+"))*\\]";
    }

    public static String pointN(){
        return "\\("+flNum+"(,("+flNum+"))*\\)";
    }

    public static String matrix(){
        return "\\["+flNum+"(,("+flNum+"))*(;"+flNum+"(,("+flNum+"))*)+\\]";
    }

    public static String scalar(){
        return flNum;
    }

    public static String complexReIm(){
        return "("+flNum+"[+]?)?"+"("+flNum+")?i";
    }

    public static void main(String[] args) {
        System.out.println(Pattern.matches(complexReIm(),"-2.0-i"));
    }
}
