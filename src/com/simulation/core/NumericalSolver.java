package com.simulation.core;
import com.simulation.data.Field;
public interface NumericalSolver {
    void step(Field<Double> field, double dt, PhysicalModel<Double> model);
}
