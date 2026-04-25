package com.simulation.domain;
public class Grid1D implements Grid {
    protected final int sizeX;
    protected final double dx;
    public Grid1D(int sizeX, double dx) {
        if (sizeX <= 0 || dx <= 0) {
            throw new com.simulation.exceptions.ConfigurationException(
                "Grid size and dx must be > 0. Got sizeX=" + sizeX + ", dx=" + dx, 
                "Grid1D", 0, 0.0, sizeX + "x1");
        }
        this.sizeX = sizeX;
        this.dx = dx;
    }
    @Override
    public int getSizeX() {
        return sizeX;
    }
    @Override
    public int getSizeY() {
        return 1; 
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
