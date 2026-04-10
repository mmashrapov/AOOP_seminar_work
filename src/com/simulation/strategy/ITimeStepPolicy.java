package com.simulation.strategy;

import com.simulation.data.Field;

/**
 * Strategy pattern interface for determining the next simulation timestep
 * dynamically.
 */
public interface ITimeStepPolicy {
    /**
     * Calculates the next dt.
     * 
     * @param state   The current physical state field.
     * @param t       The current absolute simulation time.
     * @param dt_prev The previously used timestep.
     * @return The timestep to use for the next step.
     */
    double next_dt(Field state, double t, double dt_prev);
}
/** ocp */