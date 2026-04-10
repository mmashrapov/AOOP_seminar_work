package com.simulation.events;

import com.simulation.core.SimulationController;

public class SimulationEvent {

    private final EventType type;
    private final SimulationController source;
    private final double time;
    private final int step;
    private final String metadata;

    public SimulationEvent(EventType type, SimulationController source, double time, int step, String metadata) {
        this.type = type;
        this.source = source;
        this.time = time;
        this.step = step;
        this.metadata = metadata;
    }

    public EventType getType() {
        return type;
    }

    public SimulationController getSource() {
        return source;
    }

    public double getTime() {
        return time;
    }

    public int getStep() {
        return step;
    }

    public String getMetadata() {
        return metadata;
    }
}
