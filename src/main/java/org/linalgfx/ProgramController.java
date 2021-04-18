package org.linalgfx;

import org.canvas2d.CanvasPane2D;
import org.canvas2d.CanvasRenderer2D;
import org.canvas3d.CanvasPane3D;
import org.canvas3d.CanvasRenderer3D;
import org.graphics.*;
import org.utils.textInput.OperatorMaps;
import org.utils.textInput.TextInputEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.math.Differentiator;
import org.math3d.Vector3;
import org.utils.RegexUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProgramController {
    @FXML
    private VBox root;
    @FXML
    private TextField inputField;
    @FXML
    private SplitPane splitPane;

    @FXML
    private CheckBox cameraMode;

    private File savesDirectory;

    public void initialize() {
        CanvasPane2D canvasPane2D = new CanvasPane2D(16 * 30, 9 * 30);
        Canvas canvas2D = canvasPane2D.getCanvas();
        GraphicsContext graphicsContext2D = canvas2D.getGraphicsContext2D();
        CanvasRenderer2D.setCanvas(canvas2D);
        CanvasRenderer2D.setGraphicsContext(graphicsContext2D);
        CanvasRenderer2D.setUnitSize(40);
        CanvasRenderer2D.start();

        CanvasPane3D canvasPane3D = new CanvasPane3D(16 * 30, 9 * 30);
        Canvas canvas3D = canvasPane3D.getCanvas();
        GraphicsContext graphicsContext3D = canvas3D.getGraphicsContext2D();
        CanvasRenderer3D.setCanvas(canvas3D);
        CanvasRenderer3D.setGraphicsContext(graphicsContext3D);
        CanvasRenderer3D.start();


        DefinedVariables.getScrollPane().getStyleClass().add("variables");
        DefinedVariables.getScrollPane().setMinWidth(150);

        inputField = new TextField();
        inputField.setOnAction(new TextInputEvent(inputField));

        splitPane.getItems().addAll(DefinedVariables.getScrollPane(), canvasPane2D, canvasPane3D);
        splitPane.prefHeightProperty().bind(root.heightProperty());
        splitPane.setDividerPositions(0.3, 0.9);

        OperatorMaps.fillOpMaps();
        Differentiator.fillDerivatives();

        DefinedVariables.getScrollPane().setMinWidth(150);

        initializeFileSystem();
    }


    private void initializeFileSystem(){
        savesDirectory = new File(System.getProperty("user.home") + "/Applications/Linalg/saves");
        if(!savesDirectory.exists())
            savesDirectory.mkdirs();

        System.out.println(savesDirectory.exists());
    }

    @FXML
    private void saveToFile(ActionEvent event){
        SimpleDialog dialog = new SimpleDialog("Are you sure you want to continue?\nOnly 2D canvas variables and mathematical variables will be saved.\nEnter file name below.");
        dialog.showAndWait().ifPresent(result -> {
            if (!RegexUtils.isValidName(dialog.getEditor().getText())) {
                ModalWindow.alert("The file name is invalid. Try again", Alert.AlertType.ERROR);
                return;
            }

            try {
                File file = new File(savesDirectory.getAbsolutePath(), dialog.getEditor().getText() + ".txt");

                //if file exists, excecute script inside if statement, else it creates a new file, and skips if statement
                if (!file.createNewFile()) {
                    Optional<ButtonType> alertResult = ModalWindow.alert("The file already exists, do you want to override?", Alert.AlertType.WARNING);
                    if (!alertResult.isPresent() || alertResult.get() == ButtonType.CANCEL) //alert has been canceled
                        return;
                }


                BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));

                for (VariableContainer variableContainer : DefinedVariables.getVBox().
                        getChildren().stream().
                        map(node -> (VariableContainer) node).
                        filter(var -> var.getVariable() instanceof Writable).
                        collect(Collectors.toList())) {
                    bw.write(variableContainer.toFile() + "\n");
                }
                bw.flush();
                bw.close();

            } catch (Exception e) {
                ModalWindow.alert("An error occured while trying to save file. Make sure to have the folder Applic ations/Linalg/saves created under your home directory", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void loadFromFile(ActionEvent event){
        File[] saves = savesDirectory.listFiles();
        if (saves == null) {
            ModalWindow.alert("An error occured while trying to fetch saved files. Make sure to have the folder Applications/Linalg/saves created under your home directory", Alert.AlertType.ERROR);
            throw new IllegalStateException("Files er null");
        }

        Map<String, File> savesMap = new HashMap<>();
        ComboBox<String> comboBox = new ComboBox<>();
        for (int i = 0; i < saves.length; i++) {
            if (saves[i].isFile()) {
                savesMap.put(saves[i].getName(), saves[i]);
                comboBox.getItems().add(saves[i].getName());
            }
        }
        if (comboBox.getItems().size() == 0) {
            ModalWindow.alert("There are no saves for you to load.\nPlease create one before loading from file.", Alert.AlertType.INFORMATION);
            return;
        }

        comboBox.getSelectionModel().selectFirst();

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to continue?\nOnly the variables from the file will be loaded, all other variables will be removed!\nSelect file to load from: ");
        alert.getDialogPane().setGraphic(comboBox);

        Optional<ButtonType> alertResult = alert.showAndWait();
        if (!alertResult.isPresent()) //alert has been canceled
            return;


        try (Scanner sc = new Scanner(savesMap.get(comboBox.getValue()))) {
            DefinedVariables.clear();
            while (sc.hasNextLine()) {
                String[] info = sc.nextLine().split("---");
                DefinedVariables.addFromFile(info[0], info[1], info[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeCameraMode(ActionEvent event){
        if(cameraMode.isSelected())
            CanvasRenderer3D.cameraMode = CanvasRenderer3D.CameraMode.FPS;
        else {
            CanvasRenderer3D.cameraMode = CanvasRenderer3D.CameraMode.STANDARD;
            //when using this mode, cant allow elevation because of a bug with roll
            CanvasRenderer3D.getCamera().getPosition().setY(0);
            CanvasRenderer3D.getCamera().pointAt(Vector3.ZERO());
        }
    }

}