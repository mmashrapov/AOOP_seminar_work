package com.simulation.state;
import com.simulation.core.SimulationController;
import com.simulation.events.EventType;
import com.simulation.events.SimulationEvent;
public class InitializedState implements ISimulationState {
    @Override
    public void enter(SimulationController controller) {
        controller.publishEvent(
                new SimulationEvent(EventType.ON_START, controller, 0, 0, "Simulation Data Input Registered."));
    }
    @Override
    public void handle(SimulationController controller) {
        controller.changeState(new RunningState());
    }
    @Override
    public void exit(SimulationController controller) {
    }
}
