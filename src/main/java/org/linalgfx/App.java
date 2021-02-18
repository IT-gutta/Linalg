package org.linalgfx;

import canvas2d.CanvasPane2D;
import canvas2d.CanvasRenderer2D;
import canvas3d.CanvasPane3D;
import canvas3d.CanvasRenderer3D;
import graphics.DefinedVariables;
import graphics.ToolBar;
import graphics.textInput.TextInputEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    //TODO add the ability to select objects in the graphics window
    //TODO add save functionality (save all variables in a file)
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        //2d canvas initialization
        CanvasPane2D canvasPane2D = new CanvasPane2D(16*30, 9*30);
        Canvas canvas2D = canvasPane2D.getCanvas();
        GraphicsContext graphicsContext2D = canvas2D.getGraphicsContext2D();
        CanvasRenderer2D.setCanvas(canvas2D);
        CanvasRenderer2D.setGraphicsContext(graphicsContext2D);
        CanvasRenderer2D.setUnitSize(40);
        CanvasRenderer2D.start();

        //3d canvas initialization
        CanvasPane3D canvasPane3D = new CanvasPane3D(16*30, 9*30);
        Canvas canvas3D = canvasPane3D.getCanvas();
        GraphicsContext graphicsContext3D = canvas3D.getGraphicsContext2D();
        CanvasRenderer3D.setCanvas(canvas3D);
        CanvasRenderer3D.setGraphicsContext(graphicsContext3D);
        CanvasRenderer3D.start();


        DefinedVariables.getScrollPane().getStyleClass().add("variables");

        VBox root = new VBox();


        //Label label = new Label("Input");
        TextField textField = new TextField();
        ToolBar toolBar = new ToolBar();

        Label error = new Label("");
        error.getStyleClass().add("error");

        SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane2D, canvasPane3D);
        //SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane2D);
        splitPane.prefHeightProperty().bind(root.heightProperty());

        TextInputEvent.fillOpMaps();

        DefinedVariables.getScrollPane().setMinWidth(150);


        root.getChildren().addAll(toolBar, error, textField, splitPane);
        textField.setOnAction(new TextInputEvent(textField, error));

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