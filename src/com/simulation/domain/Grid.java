package com.simulation.domain;

import com.simulation.core.SimulationDomain;

/**
 * Interface representing a spatial grid structure for compile-time dimension
 * handling.
 */
public interface Grid extends SimulationDomain {
    /**
     * @return The dimensionality of the grid (e.g. 1, 2, 3).
     */
    int getDimension();
}
