package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import graphics.Variable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Mapping implements Renderable{
    //TODO fix problems related to rendering
    private Variable<Mapping> wrapper;
    private final Expression mapping;
    private final double start;
    private final double end;
    private final double step;
    private String name;
    private boolean isHidden = false;

    /*
    public Mapping(String expression, double start, double end, double step){
        mapping = new Expression(expression);
        this.start = start;
        this.end = end;
        this.step = step;
        initPoints();
    }*/

    public Mapping(String expression){
        try{
            mapping = new Expression(expression);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        start = -10;
        end = 10;
        step = 1;
    }


    public double evaluate(double x){
        return mapping.evaluate(x);
    }


    public static void main(String[] args) {
    }

    @Override
    public String toString(){
        return mapping.getExpression();
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint) {
        gc.setLineWidth(1);
        gc.setStroke(paint);
        //non-transformable
        double prev = start;
        for(int x = 0; x < CanvasRenderer.getCanvasWidth(); x+= step){

            gc.strokeLine(prev, CanvasRenderer.toCanvasY(evaluate(CanvasRenderer.fromCanvasX(prev))), x, CanvasRenderer.toCanvasY(evaluate(CanvasRenderer.fromCanvasX(x))));

            prev = x;
        }
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
