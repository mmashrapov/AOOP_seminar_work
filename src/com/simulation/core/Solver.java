package com.simulation.core;

import com.simulation.data.Field;

/**
 * Base abstract class for numerical solvers.
 */
public abstract class Solver extends SimulationComponent {

    /**
     * Advances the field by computing the next step.
     */
    public abstract void step(Field field, double dt, PhysicalModel model);
}
