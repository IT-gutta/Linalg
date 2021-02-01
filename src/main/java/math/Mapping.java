package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Mapping implements Renderable {
    //TODO fix problems related to rendering
    private final Expression mapping;
    private final double start;
    private final double end;
    private final double step;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Point> canvasPoints;
    private String name;
    private boolean isHidden = false;
    public Mapping(String expression, double start, double end, double step){
        mapping = new Expression(expression);
        this.start = start;
        this.end = end;
        this.step = step;
        initPoints();
    }

    public Mapping(String expression){
        mapping = new Expression(expression);
        start = -10;
        end = 10;
        step = Math.pow(10, -1);
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
        return mapping.evaluate(x);
    }

    public void render(GraphicsContext gc){
        if(isHidden())
            return;
        for(Point p:canvasPoints){
            gc.fillOval(p.getElement(0),p.getElement(1),2,2);
        }
    }

    public static void main(String[] args) {
    }

    @Override
    public String toString(){
        return mapping.getExpression();
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
