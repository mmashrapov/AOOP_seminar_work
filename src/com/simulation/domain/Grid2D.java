package com.simulation.domain;

/**
 * 2D spatial grid representation for static dimension typing.
 * Replaces the previous dynamic GridDomain.
 */
public class Grid2D extends Grid1D {
    private final int sizeY;
    private final double dy;

    public Grid2D(int sizeX, int sizeY, double dx, double dy) {
        super(sizeX, dx);
        this.sizeY = sizeY;
        this.dy = dy;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public int getDimension() {
        return 2;
    }
}
