package com.simulation.models;
import com.simulation.core.PhysicalModel;
public class AdvectionDiffusionModel extends PhysicalModel<Double> {
    private final double diffusionCoefficient; 
    private final double velocityX; 
    private final double velocityY; 
    public AdvectionDiffusionModel(double diffusionCoefficient, double velocityX, double velocityY) {
        this.diffusionCoefficient = diffusionCoefficient;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    @Override
    public Double computeTimeDerivative(Double currentVal, Double ddx, Double ddy, Double d2dx2, Double d2dy2) {
        double diffusionTerm = diffusionCoefficient * (d2dx2 + d2dy2);
        double advectionTerm = (velocityX * ddx) + (velocityY * ddy);
        return diffusionTerm - advectionTerm;
    }
    @Override
    public String getName() {
        return "Advection-Diffusion Model";
    }
}
