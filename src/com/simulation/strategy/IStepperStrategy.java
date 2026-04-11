package com.simulation.strategy;
import com.simulation.core.PhysicalModel;
import com.simulation.data.Field;
public interface IStepperStrategy {
    void step(PhysicalModel<Double> model, Field<Double> state, double dt);
}
