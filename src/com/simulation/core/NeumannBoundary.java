package com.simulation.core;

import com.simulation.data.Field;

/**
 * Neumann Boundary Condition forces a specific derivative (flux) at boundary
 * nodes.
 */
public class NeumannBoundary extends BoundaryCondition {

    private final double flux;

    public NeumannBoundary(double flux) {
        this.flux = flux;
    }

    @Override
    public void applyBoundaryCondition(Field<Double> field, double[][] nextData) {
        int nx = field.getSizeX();
        int ny = field.getSizeY();
        // Simplified Neumann implementation (copy inner node plus flux)
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i == 0) {
                    nextData[i][j] = nextData[1][j] - flux;
                } else if (i == nx - 1) {
                    nextData[i][j] = nextData[nx - 2][j] + flux;
                } else if (j == 0) {
                    nextData[i][j] = nextData[i][1] - flux;
                } else if (j == ny - 1) {
                    nextData[i][j] = nextData[i][ny - 2] + flux;
                }
            }
        }
    }
}
