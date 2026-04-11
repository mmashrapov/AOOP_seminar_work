package com.simulation.core;

import com.simulation.data.Field;

/**
 * Defines the behavior of a numerical solver.
 */
public interface NumericalSolver {
    /**
     * Applies the numerical scheme to the field for one time step.
     * 
     * @param field The field to update.
     * @param dt    Time step size.
     * @param model Specific physical model guiding the simulation.
     */
    void step(Field<Double> field, double dt, PhysicalModel<Double> model);
}
