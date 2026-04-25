package com.simulation.exceptions;

public class SimulationException extends RuntimeException {
    private final String modelName;
    private final int step;
    private final double time;
    private final String gridSize;

    public SimulationException(String message, String modelName, int step, double time, String gridSize) {
        super(message);
        this.modelName = modelName;
        this.step = step;
        this.time = time;
        this.gridSize = gridSize;
    }

    public SimulationException(String message, Throwable cause, String modelName, int step, double time, String gridSize) {
        super(message, cause);
        this.modelName = modelName;
        this.step = step;
        this.time = time;
        this.gridSize = gridSize;
    }

    public String getModelName() { return modelName; }
    public int getStep() { return step; }
    public double getTime() { return time; }
    public String getGridSize() { return gridSize; }

    @Override
    public String toString() {
        return String.format("%s: %s [Model: %s | Step: %d | Time: %.4f | Grid: %s]",
                getClass().getSimpleName(), getMessage(), modelName, step, time, gridSize);
    }
}
