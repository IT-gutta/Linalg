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
import javafx.scene.Parent;
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
//        VBox root = new VBox();
        stage.setMaximized(true);
        Differentiator.fillDerivatives();

        //2d canvas initialization
//        CanvasPane2D canvasPane2D = new CanvasPane2D(16*30, 9*30);
//        Canvas canvas2D = canvasPane2D.getCanvas();
//        GraphicsContext graphicsContext2D = canvas2D.getGraphicsContext2D();
//        CanvasRenderer2D.setCanvas(canvas2D);
//        CanvasRenderer2D.setGraphicsContext(graphicsContext2D);
//        CanvasRenderer2D.setUnitSize(40);
//        CanvasRenderer2D.start();

        //3d canvas initialization
//        CanvasPane3D canvasPane3D = new CanvasPane3D(16*30, 9*30);
//        Canvas canvas3D = canvasPane3D.getCanvas();
//        GraphicsContext graphicsContext3D = canvas3D.getGraphicsContext2D();
//        CanvasRenderer3D.setCanvas(canvas3D);
//        CanvasRenderer3D.setGraphicsContext(graphicsContext3D);
//        CanvasRenderer3D.start();

//        DefinedVariables.getScrollPane().getStyleClass().add("variables");

//        Label label = new Label("Input: ");
//        TextField textField = new TextField();
//        ToolBar toolBar = new ToolBar();
//        Label error = new Label("");
//        error.getStyleClass().add("error");
//        textField.setOnAction(new TextInputEvent(textField));

//        SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane2D, canvasPane3D);
//        splitPane.prefHeightProperty().bind(root.heightProperty());
//        splitPane.setDividerPositions(0.3,0.9);
        //SplitPane splitPane = new SplitPane(DefinedVariables.getScrollPane(), canvasPane2D);



        OperatorMaps.fillOpMaps();

        DefinedVariables.getScrollPane().setMinWidth(150);


//        root.getChildren().addAll(toolBar, error, textField, splitPane);


//        scene = new Scene(root);
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("program.fxml")));
        scene = new Scene(root2);
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
        SimpleDialog dialog = new SimpleDialog("Are you sure you want to continue?\nOnly 2D canvas variables and mathematical variables will be saved.\nEnter file name below.");
        dialog.showAndWait().ifPresent(result ->{
            if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                ModalWindow.alert("The file name is invalid. Try again", Alert.AlertType.ERROR);
                saveToFile();
                return;
            }

            try {
                File file = new File(System.getProperty("user.home")+"/Applications/Linalg/saves", dialog.getEditor().getText()+".txt");

                //if file exists, excecute script inside if statement, else it creates a new file, and skips if statement
                if(!file.createNewFile()) {
                    Optional<ButtonType> alertResult = ModalWindow.alert("The file already exists, do you want to override?", Alert.AlertType.WARNING);
                    if(!alertResult.isPresent() || alertResult.get() == ButtonType.CANCEL) //alert has been canceled
                        return;
                }


                BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));

                for(VariableContainer variableContainer : DefinedVariables.getVBox().
                        getChildren().stream().
                        map(node -> (VariableContainer) node).
                        filter(var -> var.getVariable() instanceof Writable).
                        collect(Collectors.toList())){
                    bw.write(variableContainer.toFile()+"\n");
                }
                bw.flush();
                bw.close();

            } catch (Exception e){
                ModalWindow.alert("An error occured while trying to save file. Make sure to have the folder Applications/Linalg/saves created under your home directory", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        });
    }

    /**
     * Loads the application state to a file
     */
    public static void loadFromFile(){
        File dir = new File(System.getProperty("user.home")+"/Applications/Linalg/saves");
        File[] saves = dir.listFiles();
        if(saves == null) {
            ModalWindow.alert("An error occured while trying to fetch saved files. Make sure to have the folder Applications/Linalg/saves created under your home directory", Alert.AlertType.ERROR);
            throw new IllegalStateException("Files er null");
        }

        Map<String, File> savesMap = new HashMap<>();
        ComboBox<String> comboBox = new ComboBox<>();
        for(int i = 0; i < saves.length; i++){
            if(saves[i].isFile()) {
                savesMap.put(saves[i].getName(), saves[i]);
                comboBox.getItems().add(saves[i].getName());
            }
        }
        if(comboBox.getItems().size() == 0){
            ModalWindow.alert("There are no saves for you to load.\nPlease create one before loading from file.", Alert.AlertType.INFORMATION);
            return;
        }

        comboBox.getSelectionModel().selectFirst();

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to continue?\nOnly the variables from the file will be loaded, all other variables will be removed!\nSelect file to load from: ");
        alert.getDialogPane().setGraphic(comboBox);

        Optional<ButtonType> alertResult = alert.showAndWait();
        if(!alertResult.isPresent()) //alert has been canceled
            return;


        try(Scanner sc = new Scanner(savesMap.get(comboBox.getValue()))){
            DefinedVariables.clear();
            while(sc.hasNextLine()){
                String[] info = sc.nextLine().split("---");
                DefinedVariables.addFromFile(info[0], info[1], info[2]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}