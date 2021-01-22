package org.linalgfx;

import graphics.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import math.Matrix;
import math.Vector;
import math.Vectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(1000,600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        CanvasRenderer.setCanvas(canvas);
        CanvasRenderer.setGraphicsContext(gc);
        CanvasRenderer.start();

        VBox root = new VBox();
        Label label = new Label("Input");
        TextField textField = new TextField();

        TextInputEvent.fillOpMaps();

        root.getChildren().addAll(label, textField, DefinedVariables.getVBox(), canvas);
        textField.setOnAction(new TextInputEvent(textField));

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheets/style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void stop(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}