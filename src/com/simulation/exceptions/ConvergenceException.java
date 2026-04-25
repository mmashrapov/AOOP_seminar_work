package com.simulation.exceptions;

public class ConvergenceException extends NumericalException {
    public ConvergenceException(String message, String modelName, int step, double time, String gridSize) {
        super(message, modelName, step, time, gridSize);
    }
}
