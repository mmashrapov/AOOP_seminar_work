package com.simulation.strategy;
import com.simulation.core.PhysicalModel;
import com.simulation.data.Field;
import java.util.List;
public class OperatorSplittingStepper implements IStepperStrategy {
    private final IStepperStrategy baseStepper;
    private final List<PhysicalModel<Double>> sequentialModels;
    public OperatorSplittingStepper(IStepperStrategy baseStepper, List<PhysicalModel<Double>> sequentialModels) {
        this.baseStepper = baseStepper;
        this.sequentialModels = sequentialModels;
    }
    @Override
    public void step(PhysicalModel<Double> model, Field<Double> state, double dt) {
        for (PhysicalModel<Double> partModel : sequentialModels) {
            baseStepper.step(partModel, state, dt);
        }
    }
}
