package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import math.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public abstract class CanvasRenderer{
    private static List<Renderable> list = new ArrayList<>();
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;
    private static CoordinateSystem cs;
    static int unitSize;



    public static void start(){
        //viktig å kjøre først
        cs = new CoordinateSystem();
        DefinedVariables.add(cs, "Coordinate System");



        Mapping m = new Mapping(Math::cos, "cos(x)");


        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        list.forEach( r -> r.render(graphicsContext));
                    }
                },
                100,
                100
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

    public static boolean remove(Renderable renderable){
        return list.remove(renderable);
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

    public static void setList(List<Renderable> list){
        CanvasRenderer.list = list;
    }

    public static int getUnitSize(){
        return unitSize;
    }

    public static void setUnitSize(int i){
        unitSize = i;
    }

    public static double getCanvasWidth() {
        return canvas.getWidth();
    }

    public static double getCanvasHeight() {
        return canvas.getHeight();
    }

    public static Point toCanvasPoint(Point point) throws IllegalNumberOfDimensionsException {
        if(point.getPoint().length != 2)
            throw new IllegalNumberOfDimensionsException("Point has to be 2D");
        return new Point(point.getElement(0)*getUnitSize() + getCanvasWidth() / 2, -point.getElement(1)*getUnitSize() + getCanvasHeight() / 2);
    }

    public static Point fromCanvasPoint(Point point) throws IllegalNumberOfDimensionsException{
        if(point.getPoint().length != 2)
            throw new IllegalNumberOfDimensionsException("Point has to be 2D");
        return new Point((point.getElement(0) - getCanvasWidth() / 2) / getUnitSize(), (point.getElement(0) - getCanvasWidth() / 2) / -getUnitSize());
    }


    public static void updateCoordinateSystem(){
        if(cs == null)
            return;
        cs.updateLines();
    }

}
