package com.simulation.exceptions;

public class StabilityException extends NumericalException {
    public StabilityException(String message, String modelName, int step, double time, String gridSize) {
        super(message, modelName, step, time, gridSize);
    }
}
