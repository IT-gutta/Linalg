package org.linalgfx;

import graphics.Renderable;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.skin.ContextMenuSkin;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import math.Complex;
import math.Matrix;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private Canvas canvas;

    @Override
    public void start(Stage stage) throws IOException {
        canvas = new Canvas(1000,600);

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