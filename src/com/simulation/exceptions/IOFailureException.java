package com.simulation.exceptions;

public class IOFailureException extends SimulationException {
    public IOFailureException(String message, Throwable cause, String modelName, int step, double time, String gridSize) {
        super(message, cause, modelName, step, time, gridSize);
    }
}
