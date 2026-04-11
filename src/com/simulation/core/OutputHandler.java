package com.simulation.core;

import com.simulation.data.ReadOnlyField;

/**
 * Defines the behavior for handling simulation output.
 * Abstract class for output handlers.
 */
public abstract class OutputHandler extends SimulationComponent {
    /**
     * Writes the given field data to an output source (e.g., file).
     * 
     * @param field The field to write (provided safely as ReadOnlyField).
     * @param step  The current simulation step (useful for naming/tagging).
     */
    public abstract void writeOutput(ReadOnlyField<Double> field, int step);
}
