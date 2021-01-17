package org.linalgfx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import math.Complex;
import math.Matrix;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        final Canvas canvas = new Canvas(500,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Button btn = new Button();
        btn.setOnAction(ev -> {
            gc.strokeLine(0, 0, 100, 100);
        });
        VBox root = new VBox();
        root.getChildren().addAll(btn, canvas);
        btn.onActionProperty();

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheets/style.css").toExternalForm());
        stage.setScene(scene);

        System.out.println(new Complex(0, 0));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}