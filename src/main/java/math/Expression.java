package math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private String expression;
    private Expression leftChild;
    private Expression rightChild;
    private String operator;

    public Expression(String input) throws IllegalArgumentException{
        if(!checkInput(input))
            throw new IllegalArgumentException();
        this.expression = input;
        findChildren();
    }

    private boolean checkInput(String input){
        String f = "[(cos)(sin)(abs)(tan)(log)]";
        if(input.length()==0)
            return false;
        int bracketCount = 0;
        for(int i = 0; i<input.length(); i++){
            if(input.charAt(i)=='(')
                bracketCount++;
            else if(input.charAt(i)==')')
                bracketCount--;
            if(bracketCount<0)
                return false;
        }
        if(bracketCount!=0)
            return false;
        if(Pattern.matches(".*[^0-9\\.e(pi)x\\+\\*\\-/\\^"+f+"].*", input))
            return false;
        if(Pattern.matches(".*[+\\-*/^]{2}.*", input))
            return false;
        if(Pattern.matches(".*\\([\\.+\\-*/^].*", input))
            return false;
        if(Pattern.matches(".*[^0-9e(pi)x]\\..*", input))
            return false;
        if(Pattern.matches(".*\\.[^0-9e(pi)x].*", input))
            return false;
        if(Pattern.matches(".*(e|pi|x)(\\d|e|pi|x)", input))
            return false;
        if(Pattern.matches(".*(\\d|e|pi|x)(e|pi|x)", input))
            return false;
        if(Pattern.matches("[.+\\-*/^].*", input))
            return false;
        if(Pattern.matches(".*[.+\\-*/^]", input))
            return false;
        if(Pattern.matches("\\(\\)", input))
            return false;
        if(Pattern.matches(".*\\)(\\d|e|(pi)|x).*", input))
            return false;
        return true;
    }

    public String getExpression() {
        return expression;
    }

    private void findChildren(){
        removeBrackets();
        String[] children = splitExpression();
        if(!children[0].equals("")){
            leftChild = new Expression(children[0]);
            rightChild = new Expression(children[1]);
        }
        else if(expression.contains("(")){
            leftChild = new Expression(parseComposition());;
        }

    }

    private void removeBrackets(){
        boolean run = true;
        while(run && expression.length()>1){
            int depth = 0;
            for (int i = 0; i < expression.length()-1; i++) {
                if(expression.charAt(i) == '(') depth++;
                else if(expression.charAt(i) == ')') depth--;
                if(depth==0){
                    run = false;
                    break;
                }
            }
            if(run) expression = expression.substring(1,expression.length()-1);
        }
    }

    private String flipSign(Character sign, String expression){
        int bracketDepth = 0;
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i)=='(')
                bracketDepth++;
            if(expression.charAt(i)==')')
                bracketDepth--;
            if(bracketDepth==0){
                if(sign=='-'){
                    if(expression.charAt(i)=='+')
                        expression = expression.substring(0,i)+"-"+expression.substring(i+1);
                    else if(expression.charAt(i)=='-')
                        expression = expression.substring(0,i)+"+"+expression.substring(i+1);
                }
                if(sign=='/'){
                    if(expression.charAt(i)=='*')
                        expression = expression.substring(0,i)+"/"+expression.substring(i+1);
                    else if(expression.charAt(i)=='/')
                        expression = expression.substring(0,i)+"*"+expression.substring(i+1);
                }
            }
        }
        return expression;
    }

    private String[] splitExpression(){
        ArrayList<Integer> plus = new ArrayList<>();
        ArrayList<Integer> minus = new ArrayList<>();
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> divide = new ArrayList<>();
        ArrayList<Integer> exponent = new ArrayList<>();
        String lc = "";
        String rc = "";
        int bracketDepth = 0;
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i)=='(')
                bracketDepth++;
            else if(expression.charAt(i)==')')
                bracketDepth--;
            if(bracketDepth==0){
                if(expression.charAt(i)=='+'){
                    lc = expression.substring(0,i);
                    rc = expression.substring(i+1);
                    operator = "+";
                }
                else if(expression.charAt(i)=='-'){
                    lc = expression.substring(0,i);
                    rc = flipSign('-',expression.substring(i+1));
                    operator = "-";
                }
                else if(expression.charAt(i)=='*')
                    times.add(i);
                else if(expression.charAt(i)=='/')
                    divide.add(i);
                else if(expression.charAt(i)=='^')
                    exponent.add(i);
            }
        }
        if(lc.equals("")){
            if(!times.isEmpty()){
                int i = times.get(0);
                lc = expression.substring(0,i);
                rc = expression.substring(i+1);
                operator = "*";
            }
            else if(!divide.isEmpty()){
                int i = divide.get(0);
                lc = expression.substring(0,i);
                rc = flipSign('/',expression.substring(i+1));
                operator = "/";
            }
            else if(!exponent.isEmpty()){
                int i = exponent.get(0);
                lc = expression.substring(0,i);
                rc = expression.substring(i+1);
                operator = "^";
            }
        }
        return new String[]{lc,rc};
    }

    private String parseComposition(){
        System.out.println(expression);
        HashSet<String> functions = new HashSet<>();
        functions.add("cos");functions.add("sin");functions.add("abs");functions.add("log");functions.add("tan");
        String f = "(cos|sin|abs|log|tan|)";
        Matcher m = Pattern.compile(f+"\\((.+)\\)").matcher(expression);
        if(m.find()){
            operator = m.group(1);
            return  m.group(2);
        }
        throw new IllegalArgumentException();
    }

    public String toString(){
        return toString(0);
    }

    public double evaluate(double x){
        HashMap<String, BiFunction<Double, Double, Double>> dddOps = new HashMap<>();
        HashMap<String, Function<Double, Double>> ddOps = new HashMap<>();
        HashMap<String, Double> constants = new HashMap<>();
        dddOps.put("+", Expressions::sum);dddOps.put("-", Expressions::difference); dddOps.put("*", Expressions::product); dddOps.put("/", Expressions::division); dddOps.put("^", Math::pow);
        ddOps.put("cos", Math::cos); ddOps.put("sin", Math::sin); ddOps.put("tan", Math::tan); ddOps.put("abs", Math::abs); ddOps.put("log",Math::log);
        constants.put("pi", Math.PI); constants.put("e", Math.E);

        if(leftChild!=null && rightChild!=null){
            return dddOps.get(operator).apply(leftChild.evaluate(x), rightChild.evaluate(x));
        }
        else if(leftChild!=null){
            return ddOps.get(operator).apply(leftChild.evaluate(x));
        }
        else{
            if(Pattern.matches("e|pi", expression))
                return constants.get(expression);
            else if(Pattern.matches("x", expression))
                return x;
            else
                return Double.parseDouble(expression);
        }

    }

    private String toString(int depth){
        String indent = new String(new char[depth]).replace("\0", "\t");
        String result = indent + getExpression() + operator + "\n";
        if(!(leftChild==null))
            result += leftChild.toString(depth+1);
        if(!(rightChild==null)){
            result += rightChild.toString(depth+1);
        }
        return result;
    }

    public static void main(String[] args) {
        Expression root = new Expression("cos(8*abs(77^9)+3*e^(x-2))");
        System.out.println(root.toString());
        System.out.println(root.evaluate(1));
    }
}
