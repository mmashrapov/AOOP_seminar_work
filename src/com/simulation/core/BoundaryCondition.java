package com.simulation.core;

import com.simulation.data.Field;

/**
 * Base abstract class for boundary conditions.
 */
public abstract class BoundaryCondition extends SimulationComponent {

    /**
     * Applies the boundary condition to the given data array.
     */
    public abstract void applyBoundaryCondition(Field field, double[][] nextData);
}
