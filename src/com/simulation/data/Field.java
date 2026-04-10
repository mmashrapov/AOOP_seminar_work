package com.simulation.data;

/**
 * Encapsulates internal state variables such as temperature, pressure, or
 * concentration fields.
 * No public data members. All access goes through the provided getters/setters.
 */
public class Field {
    private double[][] data;
    private final int sizeX;
    private final int sizeY;

    public Field(int sizeX, int sizeY, double initialValue) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.data = new double[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                data[i][j] = initialValue;
            }
        }
    }

    /**
     * Copy constructor.
     */
    public Field(Field other) {
        this.sizeX = other.getSizeX();
        this.sizeY = other.getSizeY();
        this.data = new double[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.data[i][j] = other.getValue(i, j);
            }
        }
    }

    public double getValue(int i, int j) {
        return data[i][j];
    }

    public void setValue(int i, int j, double value) {
        data[i][j] = value;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    /**
     * Replace the internal data array entirely (useful for swapping buffers in
     * solvers).
     * 
     * @param newData the new data grid.
     */
    public void swapData(double[][] newData) {
        this.data = newData;
    }
}
