package org.linalgfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(300,300);
        Button btn = new Button();
        VBox root = new VBox();
        root.getChildren().addAll(btn, canvas);
        btn.onActionProperty();

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheets/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}