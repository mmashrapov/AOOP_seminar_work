package com.simulation.events;

import com.simulation.data.Field;
import com.simulation.core.SimulationController;

public class ConvergenceObserver implements ISimulationObserver {

    private final double convergenceTolerance;
    private final double slowConvergenceThreshold;
    private Field<Double> lastFieldState = null;

    public ConvergenceObserver(double convergenceTolerance, double slowConvergenceThreshold) {
        this.convergenceTolerance = convergenceTolerance;
        this.slowConvergenceThreshold = slowConvergenceThreshold;
    }

    @Override
    public void onEvent(SimulationEvent event) {
        if (event.getType() == EventType.ON_AFTER_STEP) {
            SimulationController controller = event.getSource();
            Field<Double> currentField = controller.getField();

            if (lastFieldState == null) {
                lastFieldState = new Field<Double>(currentField);
                return;
            }

            double maxDelta = calculateMaxDelta(lastFieldState, currentField);

            // 1. Check strict convergence
            if (maxDelta < convergenceTolerance) {
                controller.publishEvent(new SimulationEvent(EventType.ON_CONVERGED, controller, event.getTime(),
                        event.getStep(), "Delta: " + maxDelta));
            }
            // 2. Check for slow convergence (Interaction #1: Observer emits
            // SolverSwitchRecommended)
            else if (maxDelta > convergenceTolerance && maxDelta < slowConvergenceThreshold) {
                // We emit a metadata flag to suggest a switch
                controller.publishEvent(new SimulationEvent(EventType.ON_BEFORE_STEP, controller, event.getTime(),
                        event.getStep(), "SolverSwitchRecommended"));
            }

            // Update state
            lastFieldState = new Field<Double>(currentField);
        }
    }

    private double calculateMaxDelta(Field<Double> oldField, Field<Double> newField) {
        double maxDelta = 0.0;
        int nx = oldField.getSizeX();
        int ny = oldField.getSizeY();

        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                double delta = Math.abs(oldField.getValue(i, j).doubleValue() - newField.getValue(i, j).doubleValue());
                if (delta > maxDelta) {
                    maxDelta = delta;
                }
            }
        }
        return maxDelta;
    }
}
