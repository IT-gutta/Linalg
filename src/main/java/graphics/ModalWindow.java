package graphics;

import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.linalgfx.App;

public abstract class ModalWindow {
    public static void newAlert(String message){
        Stage alert = new Stage();
        alert.setHeight(100);
        alert.setWidth(400);
        Text text = new Text(message);
        Text error = new Text("The following error has occurred:");
        VBox root = new VBox(error, text);
        Scene scene = new Scene(root);
        alert.setScene(scene);
        alert.initOwner(App.getStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void alert(String message, AlertType alertType){
        Alert alert = new Alert(alertType, message);
        alert.setHeaderText("An exception was thrown");
        alert.setTitle("Warning");
        alert.showAndWait();
    }
}
