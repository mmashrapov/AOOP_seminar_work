package com.simulation.exceptions;

public class NumericalException extends SimulationException {
    public NumericalException(String message, String modelName, int step, double time, String gridSize) {
        super(message, modelName, step, time, gridSize);
    }
}
