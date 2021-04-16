package canvas2d;

import graphics.Interpolatable;
import javafx.scene.canvas.GraphicsContext;
import math.Complex;
import math.Matrix;
import write.Writable;

public class Complex2D extends Render2D implements Interpolatable, Writable {
    Complex complex;

    public Complex2D(Complex complex){
        this.complex = complex;
    }
    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public Object getMath() {
        return complex;
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {

    }

    @Override
    public void handleInterpolation() {

    }

    @Override
    public String writeString() {
        return "Complex2D---"+complex.getRe()+" "+complex.getIm();
    }
}
