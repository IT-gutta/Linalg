package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import math.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;

public class TextInputEvent implements EventHandler<ActionEvent> {
    //TODO Add more functions
    //TODO Add derivatives

    private TextField inputField;
    private Matcher m;

    private final String flNum = "(-?[0-9]+(\\.?[0-9+]+)?)";
    private final String posFlNum = "([0-9]+(\\.?[0-9+]+)?)";
    private final String varName = "(\\w[0-9a-zA-Z_]*)";
    private final String varDec = "(\\w[0-9a-zA-Z_]*)=";
    private final String funDec = "(\\w[0-9a-zA-Z_]*)\\(x\\)=";
    private final String vecCon = "\\["+flNum+","+flNum+"]";
    private final String poiCon = "\\("+flNum+","+flNum+"\\)";
    private final String matCon = "\\["+flNum+","+flNum+";"+flNum+","+flNum+"\\]";
    private final String linCon = "line\\("+varName+","+varName+"\\)";
    private final String comCon = "("+flNum+"([\\+-])"+posFlNum+"i"+")|("+flNum+"i([\\+-])"+posFlNum+")";

    private static HashMap<String, BiFunction<Vector, Vector, Vector>> vvvOps = new HashMap<>();
    private static HashMap<String, BiFunction<Vector, Vector, Double>> vvdOps = new HashMap<>();
    private static HashMap<String, BiFunction<Vector, Double, Vector>> vdvOps = new HashMap<>();
    private static HashMap<String, BiFunction<Vector, Matrix, Vector>> vmvOps = new HashMap<>();
    private static HashMap<String, BiFunction<Point, Matrix, Point>> pmpOps = new HashMap<>();
    private static HashMap<String, BiFunction<Point, Point, Point>> pppOps = new HashMap<>();
    private static HashMap<String, BiFunction<Complex, Complex, Complex>> cccOps = new HashMap<>();
    private static HashMap<String, BiFunction<Complex, Double, Complex>> cdcOps = new HashMap<>();

    private static HashMap<String, Function<Vector, Double>> vdOps = new HashMap<>();

    public TextInputEvent(TextField inputField) {
        this.inputField = inputField;
    }

    public static void fillOpMaps(){
        vvvOps.put("add", Vectors::add);vvvOps.put("subtract", Vectors::subtract);
        vvdOps.put("dot", Vectors::dot); vvdOps.put("angle", Vectors::angle);
        vdvOps.put("scale", Vectors::scale);
        vmvOps.put("transform", Vectors::transform);

        pmpOps.put("transform", Points::transform);
        pppOps.put("add", Points::add);pppOps.put("subtract", Points::subtract);

        cccOps.put("add", ComplexNumbers::add);cccOps.put("multiply", ComplexNumbers::multiply);
        cdcOps.put("pow", ComplexNumbers::pow);

        vdOps.put("abs", Vectors::getMagnitude);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String inp = inputField.getText().replace(" ", "");

        //Check for declaration statement
        if(Pattern.matches(varDec+".*", inp)){

            //VectorConstructor
            if(Pattern.matches(varDec+vecCon, inp)){
                m = Pattern.compile(varDec+vecCon).matcher(inp);
                if(m.find()){
                    DefinedVariables.add(new Vector(Double.parseDouble(m.group(2)), Double.parseDouble(m.group(4))), m.group(1));
                }
            }

            //PointConstructor
            if(Pattern.matches(varDec+poiCon, inp)){
                m = Pattern.compile(varDec+poiCon).matcher(inp);
                if(m.find()){
                    DefinedVariables.add(new Point(Double.parseDouble(m.group(2)), Double.parseDouble(m.group(4))), m.group(1));
                }
            }

            //MatrixConstructor
            if(Pattern.matches(varDec+matCon, inp)){
                System.out.println("found matrix");
                m = Pattern.compile(varDec+matCon).matcher(inp);
                if(m.find()){
                    DefinedVariables.add(new Variable<>(new Matrix(Double.parseDouble(m.group(2)), Double.parseDouble(m.group(4)),Double.parseDouble(m.group(6)),Double.parseDouble(m.group(8))), m.group(1)));
                }
            }

            //LineConstructor
            m = Pattern.compile(varDec+linCon).matcher(inp);
            if(m.find()){
                Variable a = DefinedVariables.get(m.group(2));
                Variable b = DefinedVariables.get(m.group(3));
                /*if(a.getVariable() instanceof Point && b.getVariable() instanceof Point)
                    DefinedVariables.add(new Variable<Line>(new Line((Point)a.getVariable(),(Point)b.getVariable()), m.group(1)));*/
                if(a.getVariable() instanceof Point && b.getVariable() instanceof Vector)
                    DefinedVariables.add(new Variable<Line>(new Line((Point)a.getVariable(),(Vector)b.getVariable()),m.group(1)));
                if(a.getVariable() instanceof Vector && b.getVariable() instanceof Point)
                    DefinedVariables.add(new Variable<Line>(new Line((Point)b.getVariable(),(Vector)a.getVariable()),m.group(1)));
            }

            //ComplexConstructor
            m = Pattern.compile(varDec+comCon).matcher(inp);
            if(m.find()){
                for(int i = 0; i<m.groupCount()+1; i++)
                    System.out.println(m.group(i));
                if(m.group(2)!=null)
                    DefinedVariables.add(new Variable(new Complex(Double.parseDouble(m.group(3)),Double.parseDouble(m.group(5)+m.group(6))), m.group(1)));
                else
                    DefinedVariables.add(new Variable(new Complex(Double.parseDouble(m.group(11)+m.group(12)),Double.parseDouble(m.group(9))), m.group(1)));

            }

            //Vector,Vector to Vector functions
            for (String f : vvvOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Vector && b.getVariable() instanceof Vector){
                        DefinedVariables.add(new Variable<Vector>(vvvOps.get(f).apply((Vector)a.getVariable(), (Vector)b.getVariable()),m.group(1)));
                    }
                }
            }

            //Vector,Vector to Double functions
            for (String f : vvdOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Vector && b.getVariable() instanceof Vector){
                        DefinedVariables.add(new Variable<Double>(vvdOps.get(f).apply((Vector)a.getVariable(), (Vector)b.getVariable()),m.group(1)));
                    }
                }
            }

            //Vector,Double to Vector functions
            for (String f : vvdOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Vector && b.getVariable() instanceof Double){
                        DefinedVariables.add(new Variable<Vector>(vdvOps.get(f).apply((Vector)a.getVariable(), (Double) b.getVariable()),m.group(1)));
                    }
                }
            }

            //Vector,Matrix to Vector functions
            for (String f : vmvOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Vector && b.getVariable() instanceof Matrix){
                        DefinedVariables.add(new Variable<Vector>(vmvOps.get(f).apply((Vector)a.getVariable(), (Matrix) b.getVariable()),m.group(1)));
                    }
                }
            }

            //Point,Matrix to Point functions
            for (String f : pmpOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Point && b.getVariable() instanceof Matrix){
                        DefinedVariables.add(new Variable<Point>(pmpOps.get(f).apply((Point)a.getVariable(), (Matrix) b.getVariable()),m.group(1)));
                    }
                }
            }

            //Point,Point to Point functions
            for (String f : pppOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Point && b.getVariable() instanceof Point){
                        DefinedVariables.add(new Variable<Point>(pppOps.get(f).apply((Point)a.getVariable(), (Point) b.getVariable()),m.group(1)));
                    }
                }
            }

            //Point,Point to Point functions
            for (String f : cccOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Complex && b.getVariable() instanceof Complex){
                        DefinedVariables.add(new Variable<Complex>(cccOps.get(f).apply((Complex) a.getVariable(), (Complex) b.getVariable()),m.group(1)));
                    }
                }
            }

            //Point,Point to Point functions
            for (String f : cdcOps.keySet()) {
                String func = varDec+f+"\\("+varName+","+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    Variable b = DefinedVariables.get(m.group(3));
                    if(a.getVariable() instanceof Complex && b.getVariable() instanceof Double){
                        DefinedVariables.add(new Variable<Complex>(cdcOps.get(f).apply((Complex) a.getVariable(), (Double) b.getVariable()),m.group(1)));
                    }
                }
            }

            //Point,Point to Point functions
            for (String f : vdOps.keySet()) {
                String func = varDec+f+"\\("+varName+"\\)";
                m = Pattern.compile(func).matcher(inp);
                if(m.find()){
                    Variable a = DefinedVariables.get(m.group(2));
                    if(a.getVariable() instanceof Vector){
                        DefinedVariables.add(new Variable<Double>(vdOps.get(f).apply((Vector)a.getVariable()),m.group(1)));
                    }
                }
            }

        }
        else if(Pattern.matches(funDec+".*", inp)){
            try{
                m = Pattern.compile(funDec+"(.*)").matcher(inp);
                if(m.find())
                    DefinedVariables.add(new Variable<Mapping>(new Mapping(m.group(2)), m.group(1)));
            }
            catch (Exception e){
                System.out.println(e);
            };
        }
        inputField.clear();
        System.out.println(DefinedVariables.getVBox().getChildren());
    }
}
