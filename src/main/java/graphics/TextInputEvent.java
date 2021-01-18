package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import math.Matrix;
import math.Vectors;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class TextInputEvent implements EventHandler<ActionEvent> {
    private TextField inputField;

    public TextInputEvent(TextField inputField) {
        this.inputField = inputField;
    }

    public Renderable handleCommand(String command){
        String[] params = command.substring(command.indexOf("(") + 1, command.indexOf(")")).split(",");
        String commandName = command.substring(0, command.indexOf("("));

        if(commandName.toLowerCase().equals("matrix")){
            int matrixWidth = params[0].split("_").length;
            double[][] matrixDoubleArr = new double[params.length][matrixWidth];

            System.out.println(Arrays.toString(params));

            for(int i = 0; i < params.length; i++){
                double[] dArr = new double[matrixWidth];
                String[] row = params[i].split("_");
                for(int j = 0; j < matrixWidth; j++){
                    dArr[j] = Double.parseDouble(row[j]);
                }


                matrixDoubleArr[i] = dArr;
            }

            return new Matrix(matrixDoubleArr);
        }

        if(commandName.toLowerCase().equals("vector")){
            return Vectors.parseVector(params);
        }

        return null;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String inp = inputField.getText().replace(" ", "");

        if(inp.contains("=")){
            //named variable
            String name = inp.split("=")[0].strip();
            String command = inp.split("=")[1].strip();

            Renderable renderable = handleCommand(command);
            if(renderable == null){
                System.out.println("Input not accepted");
                return;
            }
            DefinedVariables.add(renderable, name);
        }

        else{
            //anonymous
            Renderable renderable = handleCommand(inp);
            if(renderable == null){
                System.out.println("Input not accepted");
                return;
            }
            DefinedVariables.addAnonymous(renderable);
        }


        inputField.clear();
    }
}
