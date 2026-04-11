package com.simulation.legacy;
public class LegacyHeatModule {
    public static void legacy_heat_step(double[][] T, int nx, int ny, double dx, double dy, double dt, double alpha) {
        double[][] nextT = new double[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i == 0 || i == nx - 1 || j == 0 || j == ny - 1) {
                    nextT[i][j] = T[i][j];
                } else {
                    double currentVal = T[i][j];
                    double left = T[i - 1][j];
                    double right = T[i + 1][j];
                    double up = T[i][j + 1];
                    double down = T[i][j - 1];
                    double d2dx2 = (right - 2 * currentVal + left) / (dx * dx);
                    double d2dy2 = (up - 2 * currentVal + down) / (dy * dy);
                    nextT[i][j] = currentVal + dt * alpha * (d2dx2 + d2dy2);
                }
            }
        }
        for (int i = 0; i < nx; i++) {
            System.arraycopy(nextT[i], 0, T[i], 0, ny);
        }
    }
}
