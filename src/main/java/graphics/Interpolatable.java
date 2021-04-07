package graphics;

import math.Matrix;

/**
 * Implemented by classes that are have interpolation animations
 */
public interface Interpolatable {
    void startInterpolation(Matrix m, int millis);
    void handleInterpolation();
}
