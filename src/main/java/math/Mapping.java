package math;

import graphics.CanvasRenderer;
import graphics.CoordinateSystem;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.function.Function;

public class Mapping implements Renderable {
    private final Function<Double,Double> mapping;
    private final double start;
    private final double end;
    private final double step;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Point> canvasPoints;
    private String name;
    private boolean isHidden = false;
    public Mapping(Function<Double, Double> fun, double start, double end, double step, String name){
        mapping = fun;
        this.start = start;
        this.end = end;
        this.step = step;
        this.name = name;
        initPoints();
    }

    public Mapping(Function<Double, Double> fun, String name){
        mapping = fun;
        start = -10;
        end = 10;
        step = Math.pow(10, -3);
        this.name = name;
        initPoints();
    }

    public void initPoints(){
        double d = start;
        while(d<=end){
            points.add(new Point(d, evaluate(d)));
            d+=step;
        }
        initCanvasPoints();
    }

    public void initCanvasPoints(){
        canvasPoints = new ArrayList<>();
        for(Point p:points){
            canvasPoints.add(CanvasRenderer.toCanvasPoint(p));
        }
    }

    public void transform(Matrix m){
        ArrayList<Point> a = new ArrayList<>();
        for(Point p:points){
            a.add(p.transform(m));
        }
        points = a;
        initCanvasPoints();
    }

    public double evaluate(double x){
        return mapping.apply(x);
    }

    public void render(GraphicsContext gc){
        if(isHidden())
            return;
        for(Point p:canvasPoints){
            gc.fillOval(p.getElement(0),p.getElement(1),2,2);
        }
    }

    public static void main(String[] args) {
        Mapping m = new Mapping(Math::cos, "cos(x)");
        System.out.println(m.evaluate(0));
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public void show() {
        isHidden = false;
    }

    @Override
    public void hide() {
        isHidden = true;
    }

}
