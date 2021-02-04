package graphics.tools;

import graphics.DefinedVariables;
import graphics.Variable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import math.*;
import regex.RegexUtils;

import java.util.regex.Pattern;

public class NewMenuButton extends MenuButton {
    //TODO Add support for more variables (grid, linesegment and more) in the new menu window
    private TextInputDialog dialog;

    public NewMenuButton() {
        super("Add New");
        clearDialog();

        MenuItem vector = new MenuItem("2DVector");
        vector.setOnAction(actionEvent -> {
            clearDialog();
            var vectorInput = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            vectorInput.getChildren().addAll(new Text("Enter x, y :   "), xInput, yInput);
            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("2DVector");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{

                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Vector(dialog.getEditor().getText(), (double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem matrix = new MenuItem("Matrix");
        matrix.setOnAction(actionEvent -> {
            clearDialog();
            var matrixInputRows = new VBox();
            var aRow = new HBox();
            var bRow = new HBox();

            TextField aInput = DoubleFormatter.getTextField();
            TextField bInput = DoubleFormatter.getTextField();
            TextField cInput = DoubleFormatter.getTextField();
            TextField dInput = DoubleFormatter.getTextField();

            aRow.getChildren().addAll(aInput, bInput);
            bRow.getChildren().addAll(cInput, dInput);

            matrixInputRows.getChildren().addAll(new Text("Enter values:   "), aRow, bRow);
            dialog.setGraphic(matrixInputRows);
            dialog.setHeaderText("2x2Matrix");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Variable<Matrix>(new Matrix((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue(), (double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue()), dialog.getEditor().getText()));
            });
        });

        MenuItem line = new MenuItem("Line");
        line.setOnAction(actionEvent -> {
            clearDialog();
            var lineInputRows = new VBox();
            var aRow = new HBox();
            var bRow = new HBox();

            TextField aInput = DoubleFormatter.getTextField();
            TextField bInput = DoubleFormatter.getTextField();
            TextField cInput = DoubleFormatter.getTextField();
            TextField dInput = DoubleFormatter.getTextField();

            aRow.getChildren().addAll(aInput, bInput);
            bRow.getChildren().addAll(cInput, dInput);

            lineInputRows.getChildren().addAll(new Text("Enter point:   "), aRow, new Text("Enter directional vector:   "), bRow);
            dialog.setGraphic(lineInputRows);
            dialog.setHeaderText("Line");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Line(new Point((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue()), new Vector((double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });



        MenuItem complex = new MenuItem("Complex");
        complex.setOnAction(actionEvent -> {
            clearDialog();
            var complexInputBox = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            complexInputBox.getChildren().addAll(new Text("Enter a, b:   "), xInput, yInput);
            dialog.setGraphic(complexInputBox);
            dialog.setHeaderText("Complex");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Complex((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });


        MenuItem point = new MenuItem("Point");
        point.setOnAction(actionEvent -> {
            clearDialog();
            var pointInputBox = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            pointInputBox.getChildren().addAll(new Text("Enter x, y:   "), xInput, yInput);
            dialog.setGraphic(pointInputBox);
            dialog.setHeaderText("Point");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Point((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });



        MenuItem number = new MenuItem("Number");
        number.setOnAction(actionEvent -> {
            clearDialog();
            var numberInputBox = new HBox();
            TextField input = DoubleFormatter.getTextField();

            numberInputBox.getChildren().addAll(new Text("Enter the number:   "), input);
            dialog.setGraphic(numberInputBox);
            dialog.setHeaderText("Point");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Variable<>((Double) input.getTextFormatter().getValue(), dialog.getEditor().getText()));
            });
        });



        getItems().addAll(vector, complex, matrix, line, point);
    }


    private void clearDialog(){
        dialog = new TextInputDialog("");
        dialog.initModality(Modality.APPLICATION_MODAL);
    }
}
