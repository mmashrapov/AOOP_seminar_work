package com.simulation.strategy;
import com.simulation.data.Field;
public class FixedDtPolicy implements ITimeStepPolicy {
    private final double fixedDt;
    public FixedDtPolicy(double fixedDt) {
        this.fixedDt = fixedDt;
    }
    @Override
    public double next_dt(Field<Double> state, double t, double dt_prev) {
        return fixedDt;
    }
}
