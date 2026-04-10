package com.simulation.events;

public enum EventType {
    ON_CONFIGURED,
    ON_START, // Emitted when simulation officially begins
    ON_BEFORE_STEP,
    ON_AFTER_STEP,
    ON_CONVERGED, // Emitted when delta < tolerance
    ON_PAUSED, // Emitted upon manual pause state
    ON_CHECKPOINT,
    ON_ERROR,
    ON_STOP,
    ON_COMPLETED
}
