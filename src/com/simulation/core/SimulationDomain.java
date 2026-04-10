package com.simulation.core;

/**
 * Defines the behavior of the simulation domain (e.g., a grid or mesh).
 */
public interface SimulationDomain {
    /**
     * Gets the number of nodes/cells in the X direction.
     * @return Number of nodes in X.
     */
    int getSizeX();

    /**
     * Gets the number of nodes/cells in the Y direction.
     * @return Number of nodes in Y.
     */
    int getSizeY();
    
    /**
     * Gets the physical size of a single cell in the X direction.
     * @return Grid spacing dx.
     */
    double getDx();
    
    /**
     * Gets the physical size of a single cell in the Y direction.
     * @return Grid spacing dy.
     */
    double getDy();
}
