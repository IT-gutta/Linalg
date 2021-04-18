package org.graphics.editbuttons;

import org.graphics.DefinedVariables;
import org.graphics.ModalWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Represents the button used to load a file into the program
 */
public class LoadButton extends Button {
    public LoadButton(){
        super("Load from file");
        setOnAction(a ->{
            File dir = new File(System.getProperty("user.home") + "/Applications/Linalg/saves");
            File[] saves = dir.listFiles();
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
        });
    }
}
