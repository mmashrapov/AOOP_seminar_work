package com.simulation.state;

import com.simulation.core.SimulationController;

public class FailedState implements ISimulationState {

    @Override
    public void enter(SimulationController controller) {
        // Error already published before entering
    }

    @Override
    public void handle(SimulationController controller) {
        // Terminal state
    }

    @Override
    public void exit(SimulationController controller) {
    }
}
