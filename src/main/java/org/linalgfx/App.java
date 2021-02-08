package org.linalgfx;

import graphics.*;
import graphics.tools.ToolBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

/**
 * JavaFX App
 */
public class App extends Application {
    //TODO add 3d-graphics
    //TODO add the ability to select objects in the graphics window
    //TODO add save functionality (save all variables in a file)
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        CanvasPane2D canvasPane2D = new CanvasPane2D(1000, 500);
        Canvas canvas = canvasPane2D.getCanvas();


        GraphicsContext gc = canvas.getGraphicsContext2D();
        CanvasRenderer2D.setCanvas(canvas);
        CanvasRenderer2D.setGraphicsContext(gc);
        CanvasRenderer2D.setUnitSize(40);
        CanvasRenderer2D.start();

        DefinedVariables.getScrollPane().getStyleClass().add("variables");

        VBox root = new VBox();


        Label label = new Label("Input");
        TextField textField = new TextField();
        ToolBar toolBar = new ToolBar();

        SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane2D);
        splitPane.prefHeightProperty().bind(root.heightProperty());

        TextInputEvent.fillOpMaps();

        DefinedVariables.getScrollPane().setMinWidth(150);


        root.getChildren().addAll(toolBar, label, textField, splitPane);
        textField.setOnAction(new TextInputEvent(textField));

        scene = new Scene(root);
        scene.getStylesheets().add(resourceURL("stylesheets/style.css"));
        stage.setScene(scene);

        stage.setMinHeight(500);
        stage.setMinWidth(500);

        stage.show();
    }

    @Override
    public void stop(){
        System.exit(0);
    }

    public static String resourceURL(String path){
        return App.class.getResource(path).toExternalForm();
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


//    public static void saveToFile(){
//        try {
//            FileOutputStream fos = new FileOutputStream("/list.out");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            //oos.writeObject(DefinedVariables.getVBox().getChildren());
//            oos.flush();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void loadFromFile(){
//        try {
//            FileInputStream fos = new FileInputStream("/list.out");
//            ObjectInputStream oos = new ObjectInputStream(fos);
//            //Def.setList((List<Renderable>) oos.readObject());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}