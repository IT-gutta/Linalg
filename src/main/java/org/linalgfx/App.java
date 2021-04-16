package org.linalgfx;

import canvas2d.CanvasPane2D;
import canvas2d.CanvasRenderer2D;
import canvas3d.CanvasPane3D;
import canvas3d.CanvasRenderer3D;
import graphics.AlertWindow;
import graphics.DefinedVariables;
import graphics.ToolBar;
import graphics.VariableContainer;
import graphics.textInput.OperatorMaps;
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
import math.Differentiator;
import write.Writable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * JavaFX Application which handles the layout of the GUI
 */
public class App extends Application {
    //TODO add the ability to select objects in the graphics window
    //TODO add save functionality (save all variables in a file)
    private static Scene scene;
    private static Stage stage;

    public static Stage getStage(){
        return stage;
    }

    /**
     * The start method instantiates the canvaspanes, and starts the canvas animation loops, as well as
     * define the other visible elemements like the toolBar and the inputField
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        VBox root = new VBox();
        stage.setMaximized(true);
        Differentiator.fillDerivatives();

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

        Label label = new Label("Input: ");
        TextField textField = new TextField();
        ToolBar toolBar = new ToolBar();
        Label error = new Label("");
        error.getStyleClass().add("error");
        textField.setOnAction(new TextInputEvent(textField, error));

        SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane2D, canvasPane3D);
        splitPane.prefHeightProperty().bind(root.heightProperty());
        splitPane.setDividerPositions(0.3,0.9);
        //SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane2D);



        OperatorMaps.fillOpMaps();

        DefinedVariables.getScrollPane().setMinWidth(150);


        root.getChildren().addAll(toolBar, error, textField, splitPane);


        scene = new Scene(root);
        scene.getStylesheets().add(resourceURL("stylesheets/style.css"));
        stage.setScene(scene);

        stage.setMinHeight(500);
        stage.setMinWidth(500);

        stage.show();
//        AlertWindow.newAlert("Invalid input");
    }
    /**
     * Stops the javafx application and terminates gui
     */
    @Override
    public void stop(){
        System.exit(0);
    }

    /**
     * Returns the full external path of a resource relative to the resource folder for this class (App)
     */
    public static String resourceURL(String path){
        return App.class.getResource(path).toExternalForm();
    }

    /**
     * Launches the program
     */
    public static void main(String[] args) {
        launch();
    }
    /**
     * Returns width of the entire GUI
     */
    public static double getWidth(){
        return scene.getWidth();
    }
    /**
     * Returns height of the enire gui
     */
    public static double getHeight(){
        return scene.getHeight();
    }

    /**
     * Saves the application state to a file
     */
    public static void saveToFile(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/"+App.class.getResource("current_save.txt").toExternalForm().replace("file:/", ""), false));

            for(VariableContainer variableContainer : DefinedVariables.getVBox().
                    getChildren().stream().
                    map(node -> (VariableContainer) node).
                    filter(var -> var.getVariable() instanceof Writable).
                    collect(Collectors.toList())){
                bw.write(variableContainer.toFile()+"\n");
            }
            bw.flush();
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Loads the application state to a file
     */
    public static void loadFromFile(){
        try(Scanner sc = new Scanner(new File("/"+App.class.getResource("current_save.txt").toExternalForm().replace("file:/", "")))){
            DefinedVariables.getVBox().getChildren().clear();
            while(sc.hasNextLine()){
                String[] info = sc.nextLine().split("---");
                System.out.println(Arrays.toString(info));
                DefinedVariables.addFromFile(info[0], info[1], info[2]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}