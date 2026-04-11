package com.simulation.core;
import com.simulation.data.ReadOnlyField;
public abstract class OutputHandler extends SimulationComponent {
    public abstract void writeOutput(ReadOnlyField<Double> field, int step);
}
