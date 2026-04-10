package com.simulation.strategy;

import com.simulation.core.PhysicalModel;
import com.simulation.data.Field;

/**
 * Strategy pattern interface for stepping the simulation logic.
 * Encapsulates the algorithm to advance the simulation state by one timestep.
 */
public interface IStepperStrategy {
    /**
     * Advances the simulation state.
     * 
     * @param model Given physical model (or composite) deciding the underlying
     *              equation.
     * @param state The field state to mutate.
     * @param dt    The explicit time step to advance by.
     */
    void step(PhysicalModel model, Field state, double dt);
}
