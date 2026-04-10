package com.simulation.core;

/**
 * Defines the behavior of a physical model in the simulation.
 * Complies with SOLID:
 * - Single Responsibility: Only computes physics/math (time derivative).
 * - Open/Closed & Liskov Substitution: The solver doesn't need to check types,
 * it just passes spatial derivatives and current state.
 */
public abstract class PhysicalModel extends SimulationComponent {
    /**
     * Computes the time derivative (rate of change) for the current node based on
     * spatial derivatives.
     * CONTRACT:
     * - Invariants: Grid sizes and boundaries are handled by the caller.
     * - Preconditions: derivatives are pre-calculated by the solver.
     *
     * @param currentVal The value of the field at the current node.
     * @param ddx        First derivative with respect to x.
     * @param ddy        First derivative with respect to y.
     * @param d2dx2      Second derivative with respect to x.
     * @param d2dy2      Second derivative with respect to y.
     * @return The computed rate of change (e.g., dT/dt, dC/dt).
     */
    public abstract double computeTimeDerivative(double currentVal, double ddx, double ddy, double d2dx2, double d2dy2);

    /**
     * Gets the name of the physical model.
     * 
     * @return The model name.
     */
    public abstract String getName();
}
