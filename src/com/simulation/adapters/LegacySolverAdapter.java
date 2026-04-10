package com.simulation.adapters;

import com.simulation.core.Solver;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
import com.simulation.legacy.LegacyHeatModule;

/**
 * Adapter that makes the legacy module usable through the framework interfaces.
 * Complies with the Adapter Structural Pattern.
 */
public class LegacySolverAdapter extends Solver {

    private final SimulationDomain domain;
    private final double alpha; // We know the legacy model requires this specifically

    public LegacySolverAdapter(SimulationDomain domain, double alpha) {
        this.domain = domain;
        this.alpha = alpha;
    }

    @Override
    public void step(Field field, double dt, PhysicalModel model) {
        int nx = field.getSizeX();
        int ny = field.getSizeY();
        double dx = domain.getDx();
        double dy = domain.getDy();

        // 1. Translate State/Field to raw legacy data types
        double[][] rawArray = new double[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                rawArray[i][j] = field.getValue(i, j);
            }
        }

        // 2. Call the legacy C-like function
        LegacyHeatModule.legacy_heat_step(rawArray, nx, ny, dx, dy, dt, alpha);

        // 3. Translate raw arrays back to State/Field
        field.swapData(rawArray);
    }
}
