package com.simulation.state;

import com.simulation.core.SimulationController;
import com.simulation.events.EventType;
import com.simulation.events.SimulationEvent;

public class ConfiguredState implements ISimulationState {

    @Override
    public void enter(SimulationController controller) {
        controller.publishEvent(
                new SimulationEvent(EventType.ON_CONFIGURED, controller, 0, 0, "Simulation completely constructed."));
    }

    @Override
    public void handle(SimulationController controller) {
        // Transition to Initialized when asked to run
        controller.changeState(new InitializedState());
    }

    @Override
    public void exit(SimulationController controller) {
    }
}
