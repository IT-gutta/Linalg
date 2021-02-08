package graphics;

import math.Matrix;

public interface Interpolatable {
    void startInterpolation(Matrix m, int millis);
    void handleInterpolation();
}
