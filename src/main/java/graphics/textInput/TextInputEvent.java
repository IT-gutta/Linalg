package graphics.textInput;

import canvas2d.Point2D;
import canvas2d.Vector2D;
import canvas3d.Point3D;
import canvas3d.Vector3D;
import graphics.DefinedVariables;
import graphics.ModalWindow;
import graphics.VariableContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import math.*;
import canvas2d.Mapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles textual user input
 */
public class TextInputEvent implements EventHandler<ActionEvent>{

    private final TextField inputField;
    private final Label errorField;
    private Matcher m;


    public TextInputEvent(TextField inputField, Label errorField) {
        this.inputField = inputField;
        this.errorField = errorField;
    }

    /**
     * Parses the input by comparing it to regexes and functions from the maps and stores the result as a
     * defined variable if the input produces a valid variable
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        boolean legal = false;
        boolean message = false;
        String inp = inputField.getText().replace(" ", "");
        //Check for var declaration statement
        if(Pattern.matches(Regexes.varDec+".+", inp)){
            //Vector n
            if(Pattern.matches(Regexes.varDec+Regexes.vectorN(), inp)){
                m = Pattern.compile(Regexes.varDec+Regexes.vectorN()).matcher(inp);
                if(m.find()){
                    Vector v = new Vector(InputParser.vectorN(m.group(2)));
                    if(v.getDimensions()==2)
                        DefinedVariables.add(new VariableContainer<>(new Vector2D(v.toVector2()), m.group(1)));
                    else if(v.getDimensions()==3)
                        DefinedVariables.add(new VariableContainer<>(new Vector3D(v.toVector3()), m.group(1)));
                    else
                        DefinedVariables.add(new VariableContainer<>(v, m.group(1)));
                    legal = true;
                }
            }
            //Point n
            if(Pattern.matches(Regexes.varDec+Regexes.pointN(), inp)){
                m = Pattern.compile(Regexes.varDec+Regexes.pointN()).matcher(inp);
                if(m.find()){
                    Point p = new Point(InputParser.vectorN(m.group(2)));
                    if(p.getDimensions()==2)
                        DefinedVariables.add(new VariableContainer<>(new Point2D(p.toPoint2()), m.group(1)));
                    else if(p.getDimensions()==3)
                        DefinedVariables.add(new VariableContainer<>(new Point3D(p.toPoint3()), m.group(1)));
                    else
                        DefinedVariables.add(new VariableContainer<>(p, m.group(1)));
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
            //Double from function
            if(Pattern.matches(Regexes.varDec+Regexes.varName+"\\("+Regexes.varName+"\\)", inp)) {
                m = Pattern.compile(Regexes.varDec+Regexes.varName+"\\("+Regexes.varName+"\\)").matcher(inp);
                if (m.find()) {
                    System.out.println(1);
                    Expression f = (Expression) DefinedVariables.get(m.group(2)).getMath();
                    Double x = (Double)DefinedVariables.get(m.group(3)).getMath();
                    DefinedVariables.add(new VariableContainer<Double>(f.evaluate(x), m.group(1)));
                    legal = true;
                }
            }
            if(!legal){
                    m = Pattern.compile(Regexes.varDec+"(.*)").matcher(inp);
                    if(m.find() && !m.group(2).contains("x")){
                        try{
                            Expression e = new Expression(m.group(2));
                            DefinedVariables.add(new VariableContainer<Double>(e.evaluate(0), m.group(1)));
                            legal = true;
                        }
                        catch (Exception e){
//                            errorField.setText(e.getMessage());
                        }
                    }
            }
            //check for triFunction input
            for(InputMapTriFunc map: OperatorMaps.triFuncMaps){
                for(Object o:map.getMap().keySet()){
                    String f = (String)o;
                    String func = Regexes.varDec+f+"\\("+Regexes.varName+","+Regexes.varName+","+Regexes.varName+"\\)";
                    m = Pattern.compile(func).matcher(inp);
                    if(m.find()){
                        VariableContainer a = DefinedVariables.get(m.group(2));
                        VariableContainer b = DefinedVariables.get(m.group(3));
                        VariableContainer c = DefinedVariables.get(m.group(4));
                        if(a.getMath().getClass().equals(map.getInput1().getClass()) && b.getMath().getClass().equals(map.getInput2().getClass()) && c.getMath().getClass().equals(map.getInput3().getClass())){
                            try{
                                DefinedVariables.add(new VariableContainer<>(map.apply(f, map.getInput1().getClass().cast(a.getMath()), map.getInput2().getClass().cast(b.getMath()), map.getInput3().getClass().cast(c.getMath())), m.group(1)));
                                legal = true;
                            }
                            catch (Exception e){
//                                errorField.setText(e.getMessage());
                            }
                        }
                    }
                }
            }
            //check for biFunction input
            for(InputMapBiFunc map: OperatorMaps.biFuncMaps){
                for(Object o:map.getMap().keySet()){
                    String f = (String)o;
                    String func = Regexes.varDec+f+"\\("+Regexes.varName+","+Regexes.varName+"\\)";
                    m = Pattern.compile(func).matcher(inp);
                    if(m.find()){
                        VariableContainer a = DefinedVariables.get(m.group(2));
                        VariableContainer b = DefinedVariables.get(m.group(3));
                        if(a.getMath().getClass().equals(map.getInput1().getClass()) && b.getMath().getClass().equals(map.getInput2().getClass())){
                            try{
                                DefinedVariables.add(new VariableContainer<>(map.apply(f, map.getInput1().getClass().cast(a.getMath()), map.getInput2().getClass().cast(b.getMath())), m.group(1)));
                                legal = true;
                            }
                            catch (Exception e){
//                                errorField.setText(e.getMessage());
                            }
                        }
                    }
                }
            }
            //check for function input
            for(InputMapFunc map: OperatorMaps.funcMaps){
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
//                                errorField.setText(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        //function declaration
        else if(Pattern.matches(Regexes.funDec+".*", inp)){
            m = Pattern.compile(Regexes.funDec+"derivative\\("+Regexes.varName+"\\)").matcher(inp);
            try {
                if(m.find()){
                    System.out.println(m.group(2));
                    Expression e = Differentiator.derivative((Expression)DefinedVariables.get(m.group(2)).getMath());
                    System.out.println(e);
                    DefinedVariables.add(new VariableContainer(new Mapping(e), m.group(1)));
                    legal = true;
                }
            }
            catch (Exception e){
                ModalWindow.alert("Differentiation unsuccessful", AlertType.ERROR);
                message = true;
            }
            try{
                m = Pattern.compile(Regexes.funDec+"(.*)").matcher(inp);
                if(m.find()){
                    DefinedVariables.add(new VariableContainer<Mapping>(new Mapping(m.group(2)), m.group(1)));
                    legal = true;
                }
            }
            catch (Exception e){
                if(!legal && !message)
                    ModalWindow.alert("The function you entered is invalid", AlertType.ERROR);
                message = true;
            }
        }
        else{
            ModalWindow.alert("The declaration you entered is invalid", AlertType.ERROR);
            message = true;
        }
        if(legal)
            inputField.clear();
        else if(!message)
            ModalWindow.alert("The value provided for the declaration is invalid", AlertType.ERROR);

    }
}

//TODO Add more functions
//TODO Add derivatives
