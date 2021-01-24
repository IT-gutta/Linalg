package org.linalgfx;

import graphics.*;
import graphics.tools.ToolBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        CanvasPane canvasPane = new CanvasPane(1000, 500);
        Canvas canvas = canvasPane.getCanvas();



        GraphicsContext gc = canvas.getGraphicsContext2D();
        CanvasRenderer.setCanvas(canvas);
        CanvasRenderer.setGraphicsContext(gc);
        CanvasRenderer.start();

        DefinedVariables.getScrollPane().getStyleClass().add("variables");

        VBox root = new VBox();


        Label label = new Label("Input");
        TextField textField = new TextField();
        ToolBar toolBar = new ToolBar();

        SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane);
        splitPane.prefHeightProperty().bind(root.heightProperty());

        TextInputEvent.fillOpMaps();

        DefinedVariables.getScrollPane().setMinWidth(150);


        root.getChildren().addAll(toolBar, label, textField, splitPane);
        textField.setOnAction(new TextInputEvent(textField));

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheets/style.css").toExternalForm());
        stage.setScene(scene);

        stage.setMinHeight(500);
        stage.setMinWidth(500);

        stage.show();
    }

    @Override
    public void stop(){
        System.exit(0);
    }


    public static void main(String[] args) {
        launch();
    }

    public static double getWidth(){
        return scene.getWidth();
    }
    public static double getHeight(){
        return scene.getHeight();
    }


    public static void saveToFile(){
        try {
            FileOutputStream fos = new FileOutputStream("/list.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(CanvasRenderer.getList());
            oos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadFromFile(){
        try {
            FileInputStream fos = new FileInputStream("/list.out");
            ObjectInputStream oos = new ObjectInputStream(fos);
            CanvasRenderer.setList((List<Renderable>) oos.readObject());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}