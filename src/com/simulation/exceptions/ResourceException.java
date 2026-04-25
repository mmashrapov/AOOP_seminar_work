package com.simulation.exceptions;

public class ResourceException extends SimulationException {
    public ResourceException(String message, String modelName, int step, double time, String gridSize) {
        super(message, modelName, step, time, gridSize);
    }
}
