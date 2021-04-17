package org.linalgfx;

import canvas2d.CanvasPane2D;
import canvas2d.CanvasRenderer2D;
import canvas3d.CanvasPane3D;
import canvas3d.CanvasRenderer3D;
import graphics.*;
import graphics.ToolBar;
import graphics.textInput.OperatorMaps;
import graphics.textInput.TextInputEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import math.Differentiator;
import regex.RegexUtils;
import write.Writable;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JavaFX Application which handles the layout of the GUI
 */
public class App extends Application {
    //TODO add the ability to select objects in the graphics window
    //TODO add save functionality (save all variables in a file)


    /**
     * The start method instantiates the canvaspanes, and starts the canvas animation loops, as well as
     * define the other visible elemements like the toolBar and the inputField
     */
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("program.fxml"))));
        scene.getStylesheets().add(resourceURL("stylesheets/style.css"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
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
}