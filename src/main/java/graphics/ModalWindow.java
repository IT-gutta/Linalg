package graphics;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;


public abstract class ModalWindow {
    public static Optional<ButtonType> alert(String message, AlertType alertType){
        Alert alert = new Alert(alertType, message);
        alert.setHeaderText("An exception was thrown");
        alert.setTitle("Warning");
        return alert.showAndWait();
    }
}
