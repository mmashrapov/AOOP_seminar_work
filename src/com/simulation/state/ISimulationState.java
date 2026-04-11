package com.simulation.state;
import com.simulation.core.SimulationController;
public interface ISimulationState {
    void handle(SimulationController controller);
    void enter(SimulationController controller);
    void exit(SimulationController controller);
}
