package graphics;

import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import math.Point;
import math.Vector;

public class Camera3D{
    private Vector position;
    private Vector direction;
    public Camera3D(){
        this.position = new Vector(0, 0, 0);
        this.direction = new Vector(0, 0, 1);
    }
}
