package graphics.editbuttons;

import graphics.DefinedVariables;
import graphics.DoubleFormatter;
import graphics.Icons;
import graphics.VariableContainer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import math.Complex;
import math.Matrix;
import canvas2d.Line2D;
import canvas2d.Point2D;
import canvas2d.Vector2D;
import regex.RegexUtils;

public class AddButton2D extends MenuButton {
    //TODO Add support for more variables (grid, linesegment and more) in the new menu window
    private TextInputDialog dialog;

    public AddButton2D() {
        super("Add 2D Shape");
        setGraphic(Icons.of("addnew.png", 30));
        getStyleClass().add("transparent-button");
        getStyleClass().add("new-menu-button");
        clearDialog();


        MenuItem vector = new MenuItem("Vector 2D", Icons.of("vector.png", 20));
        vector.getStyleClass().add("new-menu-item");
        vector.setOnAction(actionEvent -> {
            clearDialog();
            var vectorInput = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            vectorInput.getChildren().addAll(new Text("Enter x, y :   "), xInput, yInput);
            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("Vector 2D");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{

                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Vector2D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem matrix = new MenuItem("Matrix 2x2", Icons.of("matrix2d.png", 20));
        matrix.getStyleClass().add("new-menu-item");
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
            dialog.setHeaderText("Matrix 2x2");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new VariableContainer<Matrix>(new Matrix((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue(), (double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue()), dialog.getEditor().getText()));
            });
        });

        MenuItem line = new MenuItem("Line", Icons.of("line.png", 20));
        line.getStyleClass().add("new-menu-item");
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

                DefinedVariables.add(new Line2D(new Point2D((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue()), new Vector2D((double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });



        MenuItem complex = new MenuItem("Complex", Icons.of("i.png", 20));
        complex.getStyleClass().add("new-menu-item");
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

                DefinedVariables.add(new VariableContainer<Complex>(new Complex((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText()));
            });
        });


        MenuItem point = new MenuItem("Point", Icons.of("point.png", 20));
        point.getStyleClass().add("new-menu-item");
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

                DefinedVariables.add(new Point2D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });



        MenuItem scalar = new MenuItem("Scalar", Icons.of("number.png", 20));
        scalar.getStyleClass().add("new-menu-item");
        scalar.setOnAction(actionEvent -> {
            clearDialog();
            var numberInputBox = new HBox();
            TextField input = DoubleFormatter.getTextField();

            numberInputBox.getChildren().addAll(new Text("Enter the scalar:   "), input);
            dialog.setGraphic(numberInputBox);
            dialog.setHeaderText("Point");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new VariableContainer<>((Double) input.getTextFormatter().getValue(), dialog.getEditor().getText()));
            });
        });




        getItems().addAll(vector, complex, matrix, line, point, scalar);
    }


    private void clearDialog(){
        dialog = new TextInputDialog("");
        dialog.initModality(Modality.APPLICATION_MODAL);
    }
}