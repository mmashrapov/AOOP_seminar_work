package com.simulation.strategy;
import com.simulation.data.Field;
public class AdaptiveDtPolicy implements ITimeStepPolicy {
    private final double maxGrowthFactor = 1.1;
    private final double shrinkFactor = 0.5;
    private final double maxDt;
    private final double minDt;
    private final double maxAllowedValue = 200.0; 
    public AdaptiveDtPolicy(double minDt, double maxDt) {
        this.minDt = minDt;
        this.maxDt = maxDt;
    }
    @Override
    public double next_dt(Field<Double> state, double t, double dt_prev) {
        double maxVal = 0.0;
        int nx = state.getSizeX();
        int ny = state.getSizeY();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                double val = Math.abs(state.getValue(i, j).doubleValue());
                if (val > maxVal) {
                    maxVal = val;
                }
            }
        }
        double nextDt = dt_prev;
        if (maxVal > maxAllowedValue * 0.8) {
            System.out.println("AdaptiveDtPolicy: Instability detected, shrinking dt.");
            nextDt = dt_prev * shrinkFactor;
        }
        else if (maxVal < maxAllowedValue * 0.2) {
            nextDt = dt_prev * maxGrowthFactor;
        }
        if (nextDt > maxDt)
            nextDt = maxDt;
        if (nextDt < minDt)
            nextDt = minDt;
        return nextDt;
    }
}
