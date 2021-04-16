package graphics;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.linalgfx.App;

public abstract class AlertWindow {
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
}
