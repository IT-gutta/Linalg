package terraingeneration;

import math2d.Vector2;

public class PerlinNoiseMap {
    public double get(double y, double x){
        return perlin(x+10000, y+10000);
    }

    private double interpolate(double a0, double a1, double w){
        /* // You may want clamping by inserting:
         * if (0.0 > w) return a0;
         * if (1.0 < w) return a1;
         */

        return (a1 - a0) * ((w * (w * 6.0 - 15.0) + 10.0) * w * w * w) + a0;

        /* // Use this cubic interpolation [[Smoothstep]] instead, for a smooth appearance:
         * return (a1 - a0) * (3.0 - w * 2.0) * w * w + a0;
         *
         * // Use [[Smootherstep]] for an even smoother result with a second derivative equal to zero on boundaries:
         * return (a1 - a0) * ((w * (w * 6.0 - 15.0) + 10.0) * w * w * w) + a0;
         */
    }

    private Vector2 randomGradient(int ix, int iy) {
        // Random float. No precomputed gradients mean this works for any number of grid coordinates
        double random = 2920 * Math.sin(ix * 21942 + iy * 171324 + 8912) * Math.cos(ix * 23157 * iy * 217832 + 9758);
        return new Vector2(Math.cos(random), Math.sin(random));
    }

    private double dotGridGradient(int ix, int iy, double x, double y) {
        // Get gradient from integer coordinates
        Vector2 gradient = randomGradient(ix, iy);

        // Compute the distance vector
        double dx = x - (double)ix;
        double dy = y - (double)iy;

        // Compute the dot-product
        return (dx*gradient.getX() + dy*gradient.getY());
    }

    // Compute Perlin noise at coordinates x, y
    private double perlin(double x, double y) {
        // Determine grid cell coordinates
        int x0 = (int)x;
        int x1 = x0 + 1;
        int y0 = (int)y;
        int y1 = y0 + 1;

        // Determine interpolation weights
        // Could also use higher order polynomial/s-curve here
        double sx = x - (double) x0;
        double sy = y - (double) y0;

        // Interpolate between grid point gradients
        double n0, n1, ix0, ix1, value;

        n0 = dotGridGradient(x0, y0, x, y);
        n1 = dotGridGradient(x1, y0, x, y);
        ix0 = interpolate(n0, n1, sx);

        n0 = dotGridGradient(x0, y1, x, y);
        n1 = dotGridGradient(x1, y1, x, y);
        ix1 = interpolate(n0, n1, sx);

        value = interpolate(ix0, ix1, sy);
        return value;
    }
}
