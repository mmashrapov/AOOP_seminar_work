package com.simulation.state;
import com.simulation.core.SimulationController;
import com.simulation.events.EventType;
import com.simulation.events.SimulationEvent;
public class CompletedState implements ISimulationState {
    @Override
    public void enter(SimulationController controller) {
        controller.publishEvent(new SimulationEvent(EventType.ON_COMPLETED, controller, controller.getCurrentTime(),
                controller.getCurrentStep(), "Simulation reached target steps."));
    }
    @Override
    public void handle(SimulationController controller) {
    }
    @Override
    public void exit(SimulationController controller) {
    }
}
