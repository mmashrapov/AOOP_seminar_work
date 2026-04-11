package com.simulation.core;
import com.simulation.data.Field;
public abstract class Solver<T extends Number> extends SimulationComponent {
    public abstract void step(Field<T> state, double dt, PhysicalModel<T> model);
}
