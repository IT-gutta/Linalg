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
    private boolean isPositive = true;

    private HashMap<String, BiFunction<Double, Double, Double>> dddOps = new HashMap<>();
    private HashMap<String, Function<Double, Double>> ddOps = new HashMap<>();
    private HashMap<String, Double> constants = new HashMap<>();


    public Expression(String input) throws IllegalArgumentException{
        if(!checkInput(input))
            throw new IllegalArgumentException();
        this.expression = input;

        dddOps.put("+", Expressions::sum);dddOps.put("-", Expressions::difference); dddOps.put("*", Expressions::product); dddOps.put("/", Expressions::division); dddOps.put("^", Math::pow);
        ddOps.put("cos", Math::cos); ddOps.put("sin", Math::sin); ddOps.put("tan", Math::tan); ddOps.put("abs", Math::abs); ddOps.put("log",Math::log);
        constants.put("pi", Math.PI); constants.put("e", Math.E);

        findChildren();
    }

    public String getOperator(){
        return operator;
    }

    public void setLeftChild(Expression expression){
        leftChild = expression;
    }

    public Expression getLeftChild(){
        return leftChild;
    }

    public Expression getRightChild(){
        return rightChild;
    }

    public void setRightChild(Expression expression){
        rightChild = expression;
    }

    public void setOperator(String operator){
        this.operator = operator;
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
        if(Pattern.matches(".*\\([\\.+*/^].*", input))
            return false;
        if(Pattern.matches(".*[^0-9e(pi)x]\\..*", input))
            return false;
        if(Pattern.matches(".*\\.[^0-9e(pi)x].*", input))
            return false;
        if(Pattern.matches(".*(e|pi|x)(\\d|e|pi|x)", input))
            return false;
        if(Pattern.matches(".*(\\d|e|pi|x)(e|pi|x)", input))
            return false;
        if(Pattern.matches("[.+*/^].*", input))
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
        if(isPositive)
            return expression;
        return "-"+expression;
    }

    private void findChildren(){
        removeBrackets();
        if(expression.charAt(0)=='-'){
            isPositive = false;
            expression = expression.substring(1);
            expression = flipSign('-', expression);
        }
        else if(expression.charAt(0)=='+')
            expression = expression.substring(1);
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
        HashSet<String> functions = new HashSet<>();
        functions.add("cos");functions.add("sin");functions.add("abs");functions.add("log");functions.add("tan");
        String f = "(cos|sin|abs|log|tan|)";
        Matcher m = Pattern.compile(f+"\\((.+)\\)").matcher(expression);
        if(m.find()){
            operator = m.group(1);
            return m.group(2);
        }
        throw new IllegalArgumentException();
    }

    public String debugToString(){
        return toString(0);
    }
    public String toString(){
        if(rightChild==null){
            if(leftChild==null)
                return expression;
            return "("+operator + "("+leftChild+"))";
        }
        return "("+leftChild+operator+rightChild+")";
    }

    public double evaluate(double x){
        int c = 1;
        if(!isPositive)
            c = -1;
        if(leftChild!=null && rightChild!=null){
            return dddOps.get(operator).apply(leftChild.evaluate(x), rightChild.evaluate(x))*c;
        }
        else if(leftChild!=null){
            return ddOps.get(operator).apply(leftChild.evaluate(x))*c;
        }
        else{
            if(Pattern.matches("e|pi", expression))
                return constants.get(expression)*c;
            else if(Pattern.matches("x", expression))
                return x*c;
            else
                return Double.parseDouble(expression)*c;
        }

    }

    private String toString(int depth){
        String indent = new String(new char[depth]).replace("\0", "\t");
        String sign = "";
        if(!isPositive)
            sign = "-";
        String result = indent + sign + getExpression() + operator + indent + sign + "\n";
        if(!(leftChild==null))
            result += leftChild.toString(depth+1);
        if(!(rightChild==null)){
            result += rightChild.toString(depth+1);
        }
        return result;
    }

    public static void main(String[] args) {
        Expression root = new Expression("-cos(x)+7*x");
        System.out.println(root.toString());
        System.out.println(root.evaluate(1));
    }
}

