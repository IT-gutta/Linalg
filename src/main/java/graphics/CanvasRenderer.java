package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Line;
import math.Point;
import math.Utils;
import math.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public abstract class CanvasRenderer{
    private static List<Renderable> list = new ArrayList<>();
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;
    private static double offsetX;
    private static double offsetY;
    public static double unitSize;
    private static double baseSpacing;





    public static void start(){
        Line line = new Line(new Point(fromCanvasX(getCanvasWidth()/2) + 2, fromCanvasY(getCanvasHeight()/2)), new Vector(2, 2));
        Vector vector = new Vector(2, 2);

        DefinedVariables.add(vector, "vector");

        DefinedVariables.add(line, "linje");

        accountForChanges();



        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {

                        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        drawLines();
                        list.forEach( r -> r.render(graphicsContext));
                    }
                },
                100,
                30
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

    public static double getUnitSize(){
        return unitSize;
    }

    public static void setUnitSize(double i){
        unitSize = i;
    }

    public static double getCanvasWidth() {
        return canvas.getWidth();
    }

    public static double getCanvasHeight() {
        return canvas.getHeight();
    }

    public static Point toCanvasPoint(Point point) throws IllegalNumberOfDimensionsException {
        return new Point(toCanvasX(point.getElement(0)), toCanvasY(point.getElement(1)));
    }

    //public static Point fromCanvasPoint(Point point) throws IllegalNumberOfDimensionsException{ }

    public static double toCanvasX(double x){
        return getCanvasWidth()/2 + offsetX + x * unitSize;
    }

    public static double toCanvasY(double y){
        return getCanvasHeight() / 2 + offsetY - y * unitSize;
    }

    public static double fromCanvasX(double x){
        return (x - offsetX - getCanvasWidth()/2) / unitSize;
    }

    public static double fromCanvasY(double y){
        return - (y - offsetY - getCanvasHeight()/2) / unitSize;
    }





    public static void accountForChanges(){
        //oppdaterer alle linjer som ikke er i coordinatsystemet
        for(Renderable renderable : list){
            if(renderable instanceof Line)
                ((Line) renderable).updateCanvasPoints();
        }
    }




    public static void changeOffsetX(double x){
        offsetX += x;
    }
    public static void changeOffsetY(double y){
        offsetY += y;
    }
    public static void scaleUnitSize(double s) {
        unitSize *= s;

        if (unitSize == 0)
            unitSize = Double.MIN_VALUE;
    }

    public static double getOffsetX(){
        return offsetX;
    }

    public static double getOffsetY(){
        return offsetY;
    }


    public static void drawLines(){
        baseSpacing = 40;

        double spacing =  baseSpacing * Math.pow(2, Utils.log2(unitSize/baseSpacing) - Math.floor(Utils.log2(unitSize/baseSpacing)));

        long n = -Math.round(Math.floor(Utils.log2(unitSize/baseSpacing)));

        double originX = toCanvasX(0);
        double originY = toCanvasY(0);

        double dX = originX % spacing;
        double dY = originY % spacing;

        graphicsContext.setStroke(Paint.valueOf("black"));
        graphicsContext.setLineWidth(1);

        graphicsContext.strokeLine(originX, 0, originX, getCanvasHeight());
        graphicsContext.strokeLine(0, originY, getCanvasWidth(), originY);

        double x = dX;
        double y = dY;

        graphicsContext.setStroke(Paint.valueOf("grey"));
        graphicsContext.setLineWidth(0.5);
        while(x <= getCanvasWidth()){
            graphicsContext.fillText(stringifyPowerOf2(fromCanvasX(x), n), x, originY-4);
            graphicsContext.strokeLine(x, 0, x, getCanvasHeight());
            x+=spacing;
        }


        while(y <= getCanvasHeight()){
            graphicsContext.fillText(stringifyPowerOf2(fromCanvasY(y), n), originX+6, y);
            graphicsContext.strokeLine(0, y, getCanvasWidth(), y);
            y+=spacing;
        }
    }

    private static DecimalFormat df = new DecimalFormat("0.00");

    private static String stringifyPowerOf2(double d, long n){
        int k = (int) Math.round(d / Math.pow(2, n));

        if(k == 0)
            return "";
        //return k + ", "+n + ", d:" + df.format(d);

        if(n >= 0)
            return "" + (int) (k*Math.pow(2, n));

        int denominator = (int) Math.pow(2, -n);
        int gcd = Utils.gcd(k, denominator);
        denominator = denominator/gcd;
        if(denominator == 1)
            return ""+k/gcd;

        return "" + k/gcd + "/" + (int) Math.pow(2, -n) / gcd;
    }
}
