package graphics.toolbar;

import graphics.CanvasRenderer;
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
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import org.linalgfx.App;

import java.util.List;

public class NewMenuButton extends MenuButton {
    private Popup popup = new Popup();

    public NewMenuButton() {
        super("Add New");

        MenuItem vector = new MenuItem("2DVector");
        vector.setOnAction(actionEvent -> {
            System.out.println("Du trykket på vector");

            var vectorInput = new HBox();
            vectorInput.getStyleClass().add("popupWindow");
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            vectorInput.getChildren().addAll(new Text("Skriv inn x- og y-koordinater:   "), xInput, yInput);
            popup.getContent().add(vectorInput);
            popup.show(this, App.getWidth()/2, App.getWidth()/4);
        });

        MenuItem matrix = new MenuItem("Matrix");
        matrix.setOnAction(actionEvent -> {
            System.out.println("Du trykket på matrix");
        });

        MenuItem line = new MenuItem("Line");
        line.setOnAction(actionEvent -> {
            System.out.println("Du trykket på line");
        });


        getItems().addAll(vector, matrix, line);
    }

}
