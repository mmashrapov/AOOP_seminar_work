package com.simulation.core;

/**
 * The absolute base class for all simulation entities.
 * Defines the common initialization behavior for overriding.
 */
public abstract class SimulationComponent {

    /**
     * Initializes the component. Derived classes must override this method
     * to provide specific initialization behavior.
     */
    public void initialize() {
    }
}
