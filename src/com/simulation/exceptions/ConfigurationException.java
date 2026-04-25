package com.simulation.exceptions;

public class ConfigurationException extends SimulationException {
    public ConfigurationException(String message, String modelName, int step, double time, String gridSize) {
        super(message, modelName, step, time, gridSize);
    }
}
