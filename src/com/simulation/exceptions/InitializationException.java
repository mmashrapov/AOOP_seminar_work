package com.simulation.exceptions;

public class InitializationException extends SimulationException {
    public InitializationException(String message, String modelName, int step, double time, String gridSize) {
        super(message, modelName, step, time, gridSize);
    }
}
