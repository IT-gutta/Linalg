package org.graphics.editbuttons;

import org.canvas3d.CanvasRenderer3D;
import javafx.scene.control.CheckBox;
import org.math3d.Vector3;
/**
 * Checkbox for selecting cameramode to view the 3D canvas
 */
public class CameraModeButton extends CheckBox {
    public CameraModeButton(){
        super("FPS camera");

        setOnAction(actionEvent ->{
            if(isSelected())
                CanvasRenderer3D.cameraMode = CanvasRenderer3D.CameraMode.FPS;
            else {
                CanvasRenderer3D.cameraMode = CanvasRenderer3D.CameraMode.STANDARD;
                //when using this mode, cant allow elevation because of a bug with roll
                CanvasRenderer3D.getCamera().getPosition().setY(0);
                CanvasRenderer3D.getCamera().pointAt(Vector3.ZERO());
            }
        });
    }
}
