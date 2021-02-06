package math;

import graphics.CanvasRenderer;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Point implements Renderable {
    private double[] point;
    private boolean isHidden = false;
    public Point(double... args){
        point = args;
    }
    public Point(String name, double... args){
        point = args;
        this.name = name;
    }
    public double[] getPoint(){
        return point;
    }
    public double getElement(int i){
        return point[i];
    }
    public void setElement(int i, double d){
        point[i] = d;
    }
    public String name;

    public Point transform(Matrix matrix){
        return matrix.transform(toVector()).toPoint();
    }

    public int getDimensions(){
        return point.length;
    }

    @Override
    public String toString(){
        String s = "";
        for(double element:point){
            s+=Double.toString(element)+", ";
        }
        return "("+s.substring(0,s.length()-2)+")";
    }

    public Vector toVector(){
        return Vectors.fromPoint(this);
    }

    @Override
    public void render(GraphicsContext gc){
        if(isHidden())
            return;
        Point p = CanvasRenderer.toCanvasPoint(new Point(point));
        gc.fillOval(p.getElement(0) - 5, p.getElement(1)- 5, 10,10);
        if(name!=null){
            gc.setFill(Paint.valueOf("purple"));
            Vector d = Vectors.scale(Vectors.fromPoint(this), 1/toVector().getMagnitude()/3);
            gc.fillText(name, CanvasRenderer.toCanvasX(getElement(0)+d.getElement(0)), CanvasRenderer.toCanvasY(getElement(1)+d.getElement(1)));
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
