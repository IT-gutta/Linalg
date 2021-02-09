package canvas3d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class GraphicsContext3D {
    private GraphicsContext graphicsContext2D;

    public GraphicsContext3D(GraphicsContext graphicsContext2D){
        this.graphicsContext2D = graphicsContext2D;
    }

    public void strokeLine(double x1, double y1, double z1, double x2, double y2, double z2){

    }



    public void setFill(Paint paint){
        graphicsContext2D.setFill(paint);
    }
    public void setFill(String color){
        graphicsContext2D.setFill(Paint.valueOf(color));
    }
    public void setStroke(Paint paint){
        graphicsContext2D.setStroke(paint);
    }
    public void setStroke(String color){
        graphicsContext2D.setStroke(Paint.valueOf(color));
    }
}
