package com.simulation.legacy;

/**
 * Acts as a 3rd party static library simulating a C-like API.
 * Works strictly with raw primitive arrays and basic scalar values.
 */
public class LegacyHeatModule {

    /**
     * Legacy time step method. Modifies the T array in-place.
     * 
     * @param T     The raw 2D temperature array.
     * @param nx    The size in the x direction.
     * @param ny    The size in the y direction.
     * @param dx    The grid spacing in x.
     * @param dy    The grid spacing in y.
     * @param dt    The time step.
     * @param alpha The thermal diffusivity.
     */
    public static void legacy_heat_step(double[][] T, int nx, int ny, double dx, double dy, double dt, double alpha) {
        double[][] nextT = new double[nx][ny];

        // Central difference explicit Euler, written in a generic legacy C-style manner
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i == 0 || i == nx - 1 || j == 0 || j == ny - 1) {
                    // Dirichlet boundaries
                    nextT[i][j] = T[i][j];
                } else {
                    double currentVal = T[i][j];
                    double left = T[i - 1][j];
                    double right = T[i + 1][j];
                    double up = T[i][j + 1];
                    double down = T[i][j - 1];

                    double d2dx2 = (right - 2 * currentVal + left) / (dx * dx);
                    double d2dy2 = (up - 2 * currentVal + down) / (dy * dy);

                    // Compute next temperature
                    nextT[i][j] = currentVal + dt * alpha * (d2dx2 + d2dy2);
                }
            }
        }

        // Copy back to original array (simulating in-place C pointer mutation)
        for (int i = 0; i < nx; i++) {
            System.arraycopy(nextT[i], 0, T[i], 0, ny);
        }
    }
}
