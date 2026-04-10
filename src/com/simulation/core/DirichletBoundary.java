package com.simulation.core;

import com.simulation.data.Field;

/**
 * Dirichlet Boundary Condition sets a constant specific value at boundary
 * nodes.
 */
public class DirichletBoundary extends BoundaryCondition {

    private final double value;

    public DirichletBoundary(double value) {
        this.value = value;
    }

    @Override
    public void applyBoundaryCondition(Field field, double[][] nextData) {
        int nx = field.getSizeX();
        int ny = field.getSizeY();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i == 0 || i == nx - 1 || j == 0 || j == ny - 1) {
                    nextData[i][j] = value;
                }
            }
        }
    }
}
