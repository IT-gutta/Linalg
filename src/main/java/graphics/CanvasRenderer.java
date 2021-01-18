package graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import math.Matrix;
import math.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class CanvasRenderer {
    private static List<Renderable> list = new ArrayList<>();
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;


    public static void start(){
        Vector vector = new Vector(2, 3);
        double[][] dArr = {
                {0, -1},
                {1, 0}
        };
        Matrix matrix = new Matrix(dArr);

        DefinedVariables.add(matrix, "Rotasjonsmatrix");
        //DefinedVariables.add(new Variable(vector, "roterendeVektor"));
        DefinedVariables.addAnonymous(new CoordinateSystem(canvas.getWidth(), canvas.getHeight()));

        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        vector.applyTransformation(matrix);
                        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        list.forEach( r -> r.render(graphicsContext));
                    }
                },
                0,
                1000
        );

    }

    public static void add(Renderable r){
        list.add(r);
    }

    //public static void addAll(Renderable... rs){
    //    list.addAll(List.of(rs));
    //}

    public static boolean contains(Renderable r){
        return list.contains(r);
    }

    public static void removeAll(List<Renderable> renderables){
        list.removeAll(renderables);
    }


    public static void setGraphicsContext(GraphicsContext graphicsContext) {
        CanvasRenderer.graphicsContext = graphicsContext;
    }

    public static void setCanvas(Canvas canvas) {
        CanvasRenderer.canvas = canvas;
    }


    public static List<Renderable> getList(){
        return list;
    }

}
