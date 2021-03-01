package graphics.editbuttons;

import canvas3d.CanvasRenderer3D;
import javafx.scene.control.CheckBox;
import math3d.Vector3;

public class CameraModeButton extends CheckBox {
    public CameraModeButton(){
        super("FPS camera");

        setOnAction(actionEvent ->{
            if(isSelected())
                CanvasRenderer3D.cameraMode = CanvasRenderer3D.CameraMode.FPS;
            else {
                CanvasRenderer3D.cameraMode = CanvasRenderer3D.CameraMode.STANDARD;
                CanvasRenderer3D.getCamera().pointAt(Vector3.ZERO());
            }
        });
    }
}
