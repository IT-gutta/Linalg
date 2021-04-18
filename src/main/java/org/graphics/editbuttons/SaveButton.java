package org.graphics.editbuttons;

import org.linalgfx.DefinedVariables;
import org.graphics.ModalWindow;
import org.graphics.SimpleDialog;
import org.graphics.VariableContainer;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.utils.RegexUtils;
import org.linalgfx.Writable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the button used save the state of the program
 */
public class SaveButton extends Button {
    public SaveButton(){
        super("Save to file");
        setOnAction( a ->{
            SimpleDialog dialog = new SimpleDialog("Are you sure you want to continue?\nOnly 2D canvas variables and mathematical variables will be saved.\nEnter file name below.");
            dialog.showAndWait().ifPresent(result -> {
                if (!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alert("The file name is invalid. Try again", Alert.AlertType.ERROR);
                    return;
                }

                try {
                    File file = new File(System.getProperty("user.home") + "/Applications/Linalg/saves", dialog.getEditor().getText() + ".txt");

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
        });
    }
}
