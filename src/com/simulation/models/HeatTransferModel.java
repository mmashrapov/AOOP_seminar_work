package com.simulation.models;
import com.simulation.core.PhysicalModel;
public class HeatTransferModel extends PhysicalModel<Double> {
    private final double thermalDiffusivity; 
    public HeatTransferModel(double thermalDiffusivity) {
        this.thermalDiffusivity = thermalDiffusivity;
    }
    @Override
    public void initialize() {
        System.out.println("Initializing Heat Transfer Model. Alpha = " + thermalDiffusivity);
    }
    public double getThermalDiffusivity() {
        return thermalDiffusivity;
    }
    @Override
    public Double computeTimeDerivative(Double currentVal, Double ddx, Double ddy, Double d2dx2, Double d2dy2) {
        return thermalDiffusivity * (d2dx2 + d2dy2);
    }
    @Override
    public String getName() {
        return "Heat Transfer Model";
    }
}
