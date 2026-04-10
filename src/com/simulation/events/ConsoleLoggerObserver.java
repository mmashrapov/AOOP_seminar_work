package com.simulation.events;

public class ConsoleLoggerObserver implements ISimulationObserver {
    @Override
    public void onEvent(SimulationEvent event) {
        switch (event.getType()) {
            case ON_START:
                System.out.println("[LOGGER] Simulation Started.");
                break;
            case ON_AFTER_STEP:
                System.out.println("[LOGGER] Completed Step: " + event.getStep() + " at Time: " + event.getTime());
                break;
            case ON_CONVERGED:
                System.out.println("[LOGGER] Simulation Converged!");
                break;
            case ON_ERROR:
                System.err.println("[LOGGER] ERROR: " + event.getMetadata());
                break;
            case ON_PAUSED:
                System.out.println("[LOGGER] Simulation Paused.");
                break;
            case ON_COMPLETED:
                System.out.println("[LOGGER] Simulation Finished Successfully.");
                break;
            default:
                break;
        }
    }
}
