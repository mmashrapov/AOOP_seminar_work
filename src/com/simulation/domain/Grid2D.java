package com.simulation.domain;
public class Grid2D extends Grid1D {
    private final int sizeY;
    private final double dy;
    public Grid2D(int sizeX, int sizeY, double dx, double dy) {
        super(sizeX, dx);
        if (sizeY <= 0 || dy <= 0) {
             throw new com.simulation.exceptions.ConfigurationException(
                 "Grid sizeY and dy must be > 0. Got sizeY=" + sizeY + ", dy=" + dy, 
                 "Grid2D", 0, 0.0, sizeX + "x" + sizeY);
        }
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
