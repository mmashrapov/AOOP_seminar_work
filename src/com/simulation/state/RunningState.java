package com.simulation.state;

import com.simulation.core.SimulationController;
import com.simulation.events.EventType;
import com.simulation.events.SimulationEvent;

public class RunningState implements ISimulationState {

    @Override
    public void enter(SimulationController controller) {
        // Log entry to running state implicitly if needed
    }

    @Override
    public void handle(SimulationController controller) {
        try {
            // Main loop logic is now driven step-by-step
            controller.publishEvent(new SimulationEvent(EventType.ON_BEFORE_STEP, controller,
                    controller.getCurrentTime(), controller.getCurrentStep(), ""));

            // Advance physics
            controller.getStrategy().step(controller.getModel(), controller.getField(), controller.getCurrentDt());
            controller.incrementTime();

            controller.publishEvent(new SimulationEvent(EventType.ON_AFTER_STEP, controller,
                    controller.getCurrentTime(), controller.getCurrentStep(), ""));

            if (controller.getCurrentStep() >= controller.getTotalSteps()) {
                controller.changeState(new CompletedState());
            }

        } catch (Exception e) {
            controller.publishEvent(new SimulationEvent(EventType.ON_ERROR, controller, controller.getCurrentTime(),
                    controller.getCurrentStep(), e.getMessage()));
            controller.changeState(new FailedState());
        }
    }

    @Override
    public void exit(SimulationController controller) {
    }
}
