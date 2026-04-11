package com.simulation.strategy;
import com.simulation.data.Field;
public interface ITimeStepPolicy {
    double next_dt(Field<Double> state, double t, double dt_prev);
}
