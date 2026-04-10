package com.simulation.models;

import com.simulation.core.PhysicalModel;

/**
 * Concrete physical model: Reactive Transport Model.
 * Simulates advection-dispersion with some simple reaction.
 * dC/dt = D * (d2C/dx2 + d2C/dy2) - k * C
 */
public class ReactiveTransportModel extends PhysicalModel {
    private final double diffusionCoefficient; // D
    private final double reactionRate; // k

    public ReactiveTransportModel(double diffusionCoefficient, double reactionRate) {
        this.diffusionCoefficient = diffusionCoefficient;
        this.reactionRate = reactionRate;
    }

    public double getDiffusionCoefficient() {
        return diffusionCoefficient;
    }

    public double getReactionRate() {
        return reactionRate;
    }

    @Override
    public double computeTimeDerivative(double currentVal, double ddx, double ddy, double d2dx2, double d2dy2) {
        // D * Laplacian - k * C
        return diffusionCoefficient * (d2dx2 + d2dy2) - reactionRate * currentVal;
    }

    @Override
    public String getName() {
        return "Reactive Transport Model";
    }
}
