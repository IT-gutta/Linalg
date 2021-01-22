package graphics.toolbar;

import graphics.CanvasRenderer;
import graphics.DefinedVariables;
import javafx.event.ActionEvent;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import math.*;
import org.linalgfx.App;

import java.util.List;

public class NewMenuButton extends MenuButton {
    private Popup popup = new Popup();
    private TextInputDialog dialog = new TextInputDialog("name");
    private Button mongoOppleggButton = new Button();

    public NewMenuButton() {
        super("Add New");
        dialog.initModality(Modality.APPLICATION_MODAL);

        MenuItem vector = new MenuItem("2DVector");
        vector.setOnAction(actionEvent -> {
            var vectorInput = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            vectorInput.getChildren().addAll(new Text("Enter x, y :   "), xInput, yInput);
            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("2DVector");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                DefinedVariables.add(new Vector((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem matrix = new MenuItem("Matrix");
        matrix.setOnAction(actionEvent -> {
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
                DefinedVariables.add(new Matrix((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue(), (double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem line = new MenuItem("Line");
        line.setOnAction(actionEvent -> {

            var matrixInputRows = new VBox();
            var aRow = new HBox();
            var bRow = new HBox();

            TextField aInput = DoubleFormatter.getTextField();
            TextField bInput = DoubleFormatter.getTextField();
            TextField cInput = DoubleFormatter.getTextField();
            TextField dInput = DoubleFormatter.getTextField();

            aRow.getChildren().addAll(aInput, bInput);
            bRow.getChildren().addAll(cInput, dInput);

            matrixInputRows.getChildren().addAll(new Text("Enter point:   "), aRow, new Text("Enter directional vector:   "), bRow);
            dialog.setGraphic(matrixInputRows);
            dialog.setHeaderText("Line");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                DefinedVariables.add(new Line(new Point((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue()), new Vector((double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });



        MenuItem complex = new MenuItem("Complex");
        complex.setOnAction(actionEvent -> {
            var vectorInput = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            vectorInput.getChildren().addAll(new Text("Enter a, b:   "), xInput, yInput);
            dialog.setGraphic(vectorInput);
            dialog.setHeaderText("Complex");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                DefinedVariables.add(new Complex((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });



        getItems().addAll(vector, complex, matrix, line);
    }

}
