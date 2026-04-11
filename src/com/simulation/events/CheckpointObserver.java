package com.simulation.events;
import com.simulation.core.OutputHandler;
import com.simulation.core.SimulationController;
public class CheckpointObserver implements ISimulationObserver {
    private final int checkpointInterval;
    private final OutputHandler outputHandler;
    public CheckpointObserver(int checkpointInterval, OutputHandler outputHandler) {
        this.checkpointInterval = checkpointInterval;
        this.outputHandler = outputHandler;
    }
    @Override
    public void onEvent(SimulationEvent event) {
        if (event.getType() == EventType.ON_CHECKPOINT ||
                (event.getType() == EventType.ON_AFTER_STEP && event.getStep() > 0
                        && event.getStep() % checkpointInterval == 0)) {
            SimulationController controller = event.getSource();
            outputHandler.writeOutput(controller.getField(), event.getStep() * 1000);
            System.out.println("[CHECKPOINT] Data saved at step " + event.getStep());
        }
    }
}
