package com.simulation.models;
import com.simulation.core.PhysicalModel;
public class ReactiveTransportModel extends PhysicalModel<Double> {
    private final double diffusionCoefficient; 
    private final double reactionRate; 
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
    public Double computeTimeDerivative(Double currentVal, Double ddx, Double ddy, Double d2dx2, Double d2dy2) {
        return diffusionCoefficient * (d2dx2 + d2dy2) - reactionRate * currentVal;
    }
    @Override
    public String getName() {
        return "Reactive Transport Model";
    }
}
