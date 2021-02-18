package math2d;

import canvas2d.CanvasRenderer2D;
import canvas2d.Render2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.Expression;

public class Mapping extends Render2D<Expression> {
    //TODO fix problems related to rendering
    private final double start;
    private final double end;
    private final double step;
    private String name;


    public Mapping(String expression){
        super(new Expression(expression));

        start = -10;
        end = 10;
        step = 1;
    }

    public Mapping(Expression expression){
        super(expression);

        start = -10;
        end = 10;
        step = 1;
    }


    public double evaluate(double x){
        return math.evaluate(x);
    }


    @Override
    public String toString(){
        return math.getExpression();
    }

    @Override
    public void render(GraphicsContext gc, String name, Paint paint) {
        gc.setLineWidth(1.5);
        gc.setStroke(paint);
        //non-transformable
        double prev = start;
        for(int x = 0; x < CanvasRenderer2D.getCanvasWidth(); x+= step){

            gc.strokeLine(prev, CanvasRenderer2D.toCanvasY(evaluate(CanvasRenderer2D.fromCanvasX(prev))), x, CanvasRenderer2D.toCanvasY(evaluate(CanvasRenderer2D.fromCanvasX(x))));

            prev = x;
        }
    }

}
