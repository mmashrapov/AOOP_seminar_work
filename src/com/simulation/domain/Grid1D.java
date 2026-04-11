package com.simulation.domain;

/**
 * 1D spatial grid representation for static dimension typing.
 */
public class Grid1D implements Grid {
    protected final int sizeX;
    protected final double dx;

    public Grid1D(int sizeX, double dx) {
        this.sizeX = sizeX;
        this.dx = dx;
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return 1; // Conceptually 1D has 1 'row'
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return 1.0;
    }

    @Override
    public int getDimension() {
        return 1;
    }
}
