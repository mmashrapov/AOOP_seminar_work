package com.simulation.strategy;

import com.simulation.core.PhysicalModel;
import com.simulation.data.Field;

import java.util.List;

/**
 * Concrete solver strategy: Operator Splitting.
 * Designed to sequentially step multiple models one after the other.
 */
public class OperatorSplittingStepper implements IStepperStrategy {

    private final IStepperStrategy baseStepper;
    private final List<PhysicalModel<Double>> sequentialModels;

    /**
     * @param baseStepper      The fundamental stepping strategy to apply (e.g.,
     *                         ExplicitEulerStepper)
     * @param sequentialModels An ordered list of physics modules to run iteratively
     */
    public OperatorSplittingStepper(IStepperStrategy baseStepper, List<PhysicalModel<Double>> sequentialModels) {
        this.baseStepper = baseStepper;
        this.sequentialModels = sequentialModels;
    }

    @Override
    public void step(PhysicalModel<Double> model, Field<Double> state, double dt) {
        // Here we ignore the outer 'model' parameter because this strategy overrides it
        // with its internal sequential model list.
        for (PhysicalModel<Double> partModel : sequentialModels) {
            // Apply the physics operator independently to the mutating state
            baseStepper.step(partModel, state, dt);
        }
    }
}
