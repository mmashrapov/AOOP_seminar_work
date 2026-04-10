package com.simulation.domain;

import com.simulation.core.SimulationDomain;

/**
 * A simple 2D cartesian grid domain.
 */
public class GridDomain implements SimulationDomain {
    private final int sizeX;
    private final int sizeY;
    private final double dx;
    private final double dy;

    public GridDomain(int sizeX, int sizeY, double dx, double dy) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }
}
