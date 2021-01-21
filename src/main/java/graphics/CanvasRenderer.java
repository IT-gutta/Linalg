package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public abstract class CanvasRenderer {
    private static List<Renderable> list = new ArrayList<>();
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;
    static int unitSize;
    static double canvasWidth;
    static double canvasHeight;


    public static void start(){
        //viktig å kjøre først
        CoordinateSystem cs = new CoordinateSystem(canvas.getWidth(), canvas.getHeight());


        Vector vector = new Vector(2, 3);
        double[][] dArr = {
                {1, -1},
                {1, 0}
        };
        Matrix matrix = new Matrix(dArr);
        Mapping m = new Mapping(Math::cos, "cos(x)");
        m.transform(matrix);
        Vector v = new Vector(1,2);

        DefinedVariables.add(matrix, "RM");
        DefinedVariables.add(m, "a");
        DefinedVariables.add(v, "v");
//      DefinedVariables.add(new Variable(vector, "roterendeVektor"));

        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        list.forEach( r -> r.render(graphicsContext));
                        System.out.println(list);
                    }
                },
                100,
                1000
        );
    }

    public static void add(Renderable r){
        list.add(r);
    }

    public static void addAll(Renderable... rs){
        list.addAll(List.of(rs));
    }

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

    public static int getUnitSize(){
        return unitSize;
    }

    public static void setUnitSize(int i){
        unitSize = i;
    }

    public static double getCanvasWidth() {
        return canvasWidth;
    }

    public static double getCanvasHeight() {
        return canvasHeight;
    }

    public static Point toCanvasPoint(Point point) throws IllegalNumberOfDimensionsException {
        if(point.getPoint().length != 2)
            throw new IllegalNumberOfDimensionsException("Point has to be 2D");
        return new Point(point.getElement(0)*getUnitSize() + canvasWidth / 2, -point.getElement(1)*getUnitSize() + canvasHeight / 2);
    }

    public static Point fromCanvasPoint(Point point) throws IllegalNumberOfDimensionsException{
        if(point.getPoint().length != 2)
            throw new IllegalNumberOfDimensionsException("Point has to be 2D");
        return new Point((point.getElement(0) - canvasWidth / 2) / getUnitSize(), (point.getElement(0) - canvasWidth / 2) / -getUnitSize());
    }
}
