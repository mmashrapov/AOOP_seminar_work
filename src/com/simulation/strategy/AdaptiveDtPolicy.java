package com.simulation.strategy;

import com.simulation.data.Field;

/**
 * Adaptive timestep policy based on max absolute change.
 * If maximum value > threshold, shrinks dt. If values are stable, grows dt.
 */
public class AdaptiveDtPolicy implements ITimeStepPolicy {

    private final double maxGrowthFactor = 1.1;
    private final double shrinkFactor = 0.5;
    private final double maxDt;
    private final double minDt;
    private final double maxAllowedValue = 200.0; // Arbitrary stability limit for this seminar

    public AdaptiveDtPolicy(double minDt, double maxDt) {
        this.minDt = minDt;
        this.maxDt = maxDt;
    }

    @Override
    public double next_dt(Field state, double t, double dt_prev) {
        double maxVal = 0.0;
        int nx = state.getSizeX();
        int ny = state.getSizeY();

        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                double val = Math.abs(state.getValue(i, j));
                if (val > maxVal) {
                    maxVal = val;
                }
            }
        }

        double nextDt = dt_prev;

        // If getting too hot/unstable, shrink timestep
        if (maxVal > maxAllowedValue * 0.8) {
            System.out.println("AdaptiveDtPolicy: Instability detected, shrinking dt.");
            nextDt = dt_prev * shrinkFactor;
        }
        // If perfectly stable, try to grow to finish faster
        else if (maxVal < maxAllowedValue * 0.2) {
            nextDt = dt_prev * maxGrowthFactor;
        }

        // Clamp
        if (nextDt > maxDt)
            nextDt = maxDt;
        if (nextDt < minDt)
            nextDt = minDt;

        return nextDt;
    }
}
