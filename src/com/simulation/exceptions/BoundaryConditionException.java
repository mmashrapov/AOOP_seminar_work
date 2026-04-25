package com.simulation.exceptions;

public class BoundaryConditionException extends SimulationException {
    public BoundaryConditionException(String message, String modelName, int step, double time, String gridSize) {
        super(message, modelName, step, time, gridSize);
    }
}
