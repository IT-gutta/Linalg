package graphics.textInput;

import graphics.DefinedVariables;
import graphics.VariableContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import math.*;
import math2d.Mapping;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInputEvent implements EventHandler<ActionEvent>{
    private static final ArrayList<InputMapBiFunc> biFuncMaps = new ArrayList<>();
    private static final ArrayList<InputMapFunc> funcMaps = new ArrayList<>();

    private final TextField inputField;
    private final Label errorField;
    private Matcher m;

    private static final InputMapBiFunc<Vector, Vector, Vector> vvvOps = new InputMapBiFunc<>(new Vector(),new Vector(),new Vector());
    private static final InputMapBiFunc<Vector, Vector, Double> vvdOps = new InputMapBiFunc<>(new Vector(),new Vector(),0d);
    private static final InputMapBiFunc<Vector, Double, Vector> vdvOps = new InputMapBiFunc<>(new Vector(),0d,new Vector());
    private static final InputMapBiFunc<Vector, Matrix, Vector> vmvOps = new InputMapBiFunc<>(new Vector(),new Matrix(),new Vector());
    private static final InputMapBiFunc<Matrix, Matrix, Matrix> mmmOps = new InputMapBiFunc<>(new Matrix(),new Matrix(),new Matrix());
    private static final InputMapBiFunc<Point, Matrix, Point> pmpOps = new InputMapBiFunc<>(new Point(),new Matrix(),new Point());
    private static final InputMapBiFunc<Point, Point, Point> pppOps = new InputMapBiFunc<>(new Point(),new Point(),new Point());
    private static final InputMapBiFunc<Complex, Complex, Complex> cccOps = new InputMapBiFunc<>(new Complex(),new Complex(), new Complex());
    private static final InputMapBiFunc<Complex, Double, Complex> cdcOps = new InputMapBiFunc<>(new Complex(),0d, new Complex());
    private static final InputMapBiFunc<Matrix, Vector, Vector> mvvOps = new InputMapBiFunc<>(new Matrix(), new Vector(), new Vector());


    private static final InputMapFunc<Vector, Double> vdOps = new InputMapFunc<>(new Vector(), 0d);
    private static final InputMapFunc<Matrix, Matrix> mmOps = new InputMapFunc<>(new Matrix(), new Matrix());

    public TextInputEvent(TextField inputField, Label errorField) {
        this.inputField = inputField;
        this.errorField = errorField;
    }

    public static void fillOpMaps(){
        vvvOps.put("add", Vectors::add);vvvOps.put("subtract", Vectors::subtract);
        biFuncMaps.add(vvvOps);
        vvdOps.put("dot", Vectors::dot); vvdOps.put("angle", Vectors::angle);
        biFuncMaps.add(vvdOps);
        vdvOps.put("scale", Vectors::scale);
        biFuncMaps.add(vdvOps);
        vmvOps.put("transform", Vectors::transform);
        biFuncMaps.add(vmvOps);
        mmmOps.put("product", Matrices::product);
        biFuncMaps.add(mmmOps);
        mvvOps.put("solve", Solver::solveLinSys);
        biFuncMaps.add(mvvOps);
        pmpOps.put("transform", Points::transform);
        biFuncMaps.add(pmpOps);
        pppOps.put("add", Points::add);pppOps.put("subtract", Points::subtract);
        biFuncMaps.add(pppOps);
        cccOps.put("add", ComplexNumbers::add);cccOps.put("multiply", ComplexNumbers::multiply);
        biFuncMaps.add(cccOps);
        cdcOps.put("pow", ComplexNumbers::pow);
        biFuncMaps.add(cdcOps);
        vdOps.put("abs", Vectors::getMagnitude);
        funcMaps.add(vdOps);
        mmOps.put("inverse", Solver::invertedMatrix);
        funcMaps.add(mmOps);
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        boolean legal = false;
        String inp = inputField.getText().replace(" ", "");
        //Check for var declaration statement
        if(Pattern.matches(Regexes.varDec+".*", inp)){
            //Vector n
            if(Pattern.matches(Regexes.varDec+Regexes.vectorN(), inp)){
                m = Pattern.compile(Regexes.varDec+Regexes.vectorN()).matcher(inp);
                if(m.find()){
                    DefinedVariables.add(new VariableContainer<>(new Vector(InputParser.vectorN(m.group(2))), m.group(1)));
                    legal = true;
                }
            }
            //Point n
            if(Pattern.matches(Regexes.varDec+Regexes.pointN(), inp)){
                m = Pattern.compile(Regexes.varDec+Regexes.pointN()).matcher(inp);
                if(m.find()){
                    DefinedVariables.add(new VariableContainer<>(new Point(InputParser.vectorN(m.group(2))), m.group(1)));
                    legal = true;
                }
            }
            //Matrix n*m
            if(Pattern.matches(Regexes.varDec+Regexes.matrix(), inp)){
                m = Pattern.compile(Regexes.varDec+Regexes.matrix()).matcher(inp);
                if(m.find()){
                    DefinedVariables.add(new VariableContainer<>(new Matrix(InputParser.matrixMN(m.group(2))), m.group(1)));
                    legal = true;
                }
            }
            //Complex
            if(Pattern.matches(Regexes.varDec+Regexes.complex(), inp)){
                m = Pattern.compile(Regexes.varDec+Regexes.complex()).matcher(inp);
                if(m.find()){
                    for(int i = 0; i<m.groupCount(); i++){
                        System.out.println(i+": "+m.group(i));
                    }
                    double re = 1;
                    double im = 0;
                    if(m.group(3)!=null){
                        re = Double.parseDouble(m.group(4));
                        if(m.group(6) == null || m.group(6).equals("") || m.group(6).equals("+"))
                            im = 1;
                        else if(m.group(6).equals("-"))
                            im = -1;
                        else
                            im = Double.parseDouble(m.group(6));
                    }
                    else if(m.group(9)!=null){
                        re = Double.parseDouble(m.group(13));
                        if(m.group(10) == null || m.group(10).equals("") || m.group(10).equals("+"))
                            im = 1;
                        else if(m.group(10).equals("-"))
                            im = -1;
                        else
                            im = Double.parseDouble(m.group(10));
                    }
                    else if(m.group(16)!=null){
                        re = 0;
                        if(m.group(17) == null || m.group(17).equals("") || m.group(17).equals("+"))
                            im = 1;
                        else if(m.group(17).equals("-"))
                            im = -1;
                        else
                            im = Double.parseDouble(m.group(17));
                    }
                    DefinedVariables.add(new VariableContainer<>(new Complex(re, im),m.group(1)));
                    legal = true;
                }
            }
            //Double
            if(Pattern.matches(Regexes.varDec+Regexes.flNum, inp)){
                m = Pattern.compile(Regexes.varDec+Regexes.flNum).matcher(inp);
                if(m.find())
                    DefinedVariables.add(new VariableContainer<Double>(Double.parseDouble(m.group(2)), m.group(1)));
            }
            //check for biFunction input
            for(InputMapBiFunc map:biFuncMaps){
                for(Object o:map.getMap().keySet()){
                    String f = (String)o;
                    String func = Regexes.varDec+f+"\\("+Regexes.varName+","+Regexes.varName+"\\)";
                    m = Pattern.compile(func).matcher(inp);
                    if(m.find()){
                        VariableContainer a = DefinedVariables.get(m.group(2));
                        VariableContainer b = DefinedVariables.get(m.group(3));
                        System.out.println(a);
                        System.out.println(b);
                        System.out.println(a.getMath().getClass());
                        System.out.println(b.getMath().getClass());
                        if(a.getMath().getClass().equals(map.getInput1().getClass()) && b.getMath().getClass().equals(map.getInput2().getClass())){
                            try{
                                System.out.println(a);
                                System.out.println(b);
                                DefinedVariables.add(new VariableContainer<>(map.apply(f, map.getInput1().getClass().cast(a.getMath()), map.getInput2().getClass().cast(b.getMath())), m.group(1)));
                                legal = true;
                            }
                            catch (Exception e){
                                errorField.setText(e.getMessage());
                            }
                        }
                    }
                }
            }
            //check for function input
            for(InputMapFunc map:funcMaps){
                for(Object o:map.getMap().keySet()){
                    String f = (String)o;
                    String func = Regexes.varDec+f+"\\("+Regexes.varName+"\\)";
                    m = Pattern.compile(func).matcher(inp);
                    if(m.find()){
                        VariableContainer a = DefinedVariables.get(m.group(2));
                        if(a.getMath().getClass().equals(map.getInput().getClass())){
                            try{
                                DefinedVariables.add(new VariableContainer<>(map.apply(f, map.getInput().getClass().cast(a.getMath())), m.group(1)));
                            }
                            catch (Exception e){
                                errorField.setText(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        //function declaration
        else if(Pattern.matches(Regexes.funDec+".*", inp)){
            try{
                m = Pattern.compile(Regexes.funDec+"(.*)").matcher(inp);
                if(m.find())
                    DefinedVariables.add(new VariableContainer<Mapping>(new Mapping(m.group(2)), m.group(1)));
            }
            catch (Exception e){
                errorField.setText(e.getMessage());
            }
        }
        if(legal)
            inputField.clear();
    }
}

//TODO Add more functions
//TODO Add derivatives
