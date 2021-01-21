package math;

import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.function.Function;

public class Mapping implements Renderable {
    private final Function<Double,Double> mapping;
    private double start;
    private double end;
    private double step;
    private ArrayList<Point> points;
    public Mapping(Function<Double, Double> fun, double start, double end, double step){
        mapping = fun;
        this.start = start;
        this.end = end;
        this.step = step;
        initPoints();
    }
    public Mapping(Function<Double, Double> fun){
        mapping = fun;
        start = -10;
        end = 10;
        step = Math.pow(10, -3);
        initPoints();
    }
    public void initPoints(){
        double d = start;
        while(d<=end){
            points.add(new Point(d, evaluate(d)));
        }
    }
    public double evaluate(double x){
        return mapping.apply(x);
    }

    public void render(GraphicsContext gc){
        for(Point p:points){
            Point q = p;
        }
    }



}
