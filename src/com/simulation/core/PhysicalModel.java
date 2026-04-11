package com.simulation.core;
public abstract class PhysicalModel<T extends Number> extends SimulationComponent {
    public abstract T computeTimeDerivative(T currentVal, T ddx, T ddy, T d2dx2, T d2dy2);
    public abstract String getName();
}
