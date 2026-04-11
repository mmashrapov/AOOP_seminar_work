package com.simulation.models;

import com.simulation.core.PhysicalModel;

/**
 * Concrete physical model: Advection-Diffusion Model.
 * Simulates advection and diffusion: dC/dt = D*(d2C/dx2 + d2C/dy2) -
 * v_x*(dC/dx) - v_y*(dC/dy)
 */
public class AdvectionDiffusionModel extends PhysicalModel<Double> {
    private final double diffusionCoefficient; // D
    private final double velocityX; // v_x
    private final double velocityY; // v_y

    public AdvectionDiffusionModel(double diffusionCoefficient, double velocityX, double velocityY) {
        this.diffusionCoefficient = diffusionCoefficient;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    @Override
    public Double computeTimeDerivative(Double currentVal, Double ddx, Double ddy, Double d2dx2, Double d2dy2) {
        // D * Laplacian(C) - (v_x * dC/dx + v_y * dC/dy)
        double diffusionTerm = diffusionCoefficient * (d2dx2 + d2dy2);
        double advectionTerm = (velocityX * ddx) + (velocityY * ddy);

        return diffusionTerm - advectionTerm;
    }

    @Override
    public String getName() {
        return "Advection-Diffusion Model";
    }
}
