package com.simulation.models;

import com.simulation.core.PhysicalModel;

/**
 * Simulates wave propagation: d2u/dt2 = c^2 * (d2u/dx2 + d2u/dy2)
 * For standard Euler integration without state separation, we treat it as
 * a basic form or proxy derivation for the assignment.
 */
public class WavePropagationModel extends PhysicalModel<Double> {
    private final double waveSpeed;

    public WavePropagationModel(double waveSpeed) {
        this.waveSpeed = waveSpeed;
    }

    @Override
    public void initialize() {
        System.out.println("Initializing Wave Propagation Model with c=" + waveSpeed);
    }

    @Override
    public Double computeTimeDerivative(Double currentVal, Double ddx, Double ddy, Double d2dx2, Double d2dy2) {
        // Evaluate: c^2 * laplacian
        return (waveSpeed * waveSpeed) * (d2dx2 + d2dy2);
    }

    @Override
    public String getName() {
        return "Wave Propagation Model";
    }
}
