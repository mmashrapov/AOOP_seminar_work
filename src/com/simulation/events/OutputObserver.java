package com.simulation.events;

import com.simulation.core.OutputHandler;

public class OutputObserver implements ISimulationObserver {

    private final OutputHandler outputHandler;
    private final int outputInterval;

    public OutputObserver(OutputHandler outputHandler, int outputInterval) {
        this.outputHandler = outputHandler;
        this.outputInterval = outputInterval;
    }

    @Override
    public void onEvent(SimulationEvent event) {
        if (event.getType() == EventType.ON_START) {
            outputHandler.writeOutput(event.getSource().getField(), 0);
        } else if (event.getType() == EventType.ON_AFTER_STEP) {
            if (event.getStep() % outputInterval == 0) {
                outputHandler.writeOutput(event.getSource().getField(), event.getStep());
            }
        } else if (event.getType() == EventType.ON_COMPLETED) {
            outputHandler.writeOutput(event.getSource().getField(), event.getStep());
        }
    }
}
