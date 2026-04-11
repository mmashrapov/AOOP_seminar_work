package com.simulation.models;

import com.simulation.core.PhysicalModel;

/**
 * Concrete physical model: Single-Phase Fluid Flow.
 * Simulates a simple diffusion equation (e.g., pressure diffusion).
 * dP/dt = c * (d2P/dx2 + d2P/dy2) where c is diffusivity.
 */
public class SinglePhaseFluidFlowModel extends PhysicalModel<Double> {
    private final double fluidDiffusivity;

    public SinglePhaseFluidFlowModel(double fluidDiffusivity) {
        this.fluidDiffusivity = fluidDiffusivity;
    }

    public double getFluidDiffusivity() {
        return fluidDiffusivity;
    }

    @Override
    public Double computeTimeDerivative(Double currentVal, Double ddx, Double ddy, Double d2dx2, Double d2dy2) {
        return fluidDiffusivity * (d2dx2 + d2dy2);
    }

    @Override
    public String getName() {
        return "Single-Phase Fluid Flow Model";
    }
}
