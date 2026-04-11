package com.simulation.core;
import com.simulation.data.Field;
public abstract class BoundaryCondition extends SimulationComponent {
    public abstract void applyBoundaryCondition(Field<Double> field, double[][] nextData);
}
