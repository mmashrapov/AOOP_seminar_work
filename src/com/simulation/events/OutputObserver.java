package com.simulation.events;
import com.simulation.core.OutputHandler;
public class OutputObserver implements ISimulationObserver {
    private final OutputHandler outputHandler;
    private final int frequency;
    public OutputObserver(OutputHandler outputHandler, int frequency) {
        this.outputHandler = outputHandler;
        this.frequency = frequency;
    }
    @Override
    public void onEvent(SimulationEvent event) {
        if (event.getType() == EventType.ON_AFTER_STEP || event.getType() == EventType.ON_START) {
            int step = event.getStep();
            if (step % frequency == 0) {
                outputHandler.writeOutput(event.getSource().getField(), step);
            }
        }
    }
}
