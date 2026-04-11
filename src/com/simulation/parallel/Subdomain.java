package com.simulation.parallel;
import com.simulation.data.Field;
public class Subdomain {
    private final int rowStart;
    private final int rowEnd;
    private final Field<Double> globalField;
    private final Double[][] localData;
    private final Double[][] nextLocalData;
    private final int localSizeX;
    private final int localSizeY;
    public Subdomain(int rowStart, int rowEnd, Field<Double> globalField) {
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        this.globalField = globalField;
        this.localSizeX = globalField.getSizeX();
        this.localSizeY = (rowEnd - rowStart) + 2;
        this.localData = new Double[localSizeX][localSizeY];
        this.nextLocalData = new Double[localSizeX][localSizeY];
        loadFromGlobal();
    }
    public void loadFromGlobal() {
        for (int i = 0; i < localSizeX; i++) {
            for (int j = 0; j < localSizeY; j++) {
                int globalY = rowStart - 1 + j;
                if (globalY >= 0 && globalY < globalField.getSizeY()) {
                    localData[i][j] = globalField.getValue(i, globalY);
                } else {
                    localData[i][j] = 0.0;
                }
                nextLocalData[i][j] = localData[i][j]; 
            }
        }
    }
    public void swapBuffers() {
        for (int i = 0; i < localSizeX; i++) {
            for (int j = 0; j < localSizeY; j++) {
                localData[i][j] = nextLocalData[i][j];
            }
        }
    }
    public int getRowStart() {
        return rowStart;
    }
    public int getRowEnd() {
        return rowEnd;
    }
    public Field<Double> getGlobalField() {
        return globalField;
    }
    public Double[][] getLocalData() {
        return localData;
    }
    public Double[][] getNextLocalData() {
        return nextLocalData;
    }
    public void writeBack() {
        for (int i = 0; i < localSizeX; i++) {
            for (int j = rowStart; j < rowEnd; j++) {
                int localY = j - rowStart + 1;
                globalField.setValue(i, j, localData[i][localY]);
            }
        }
    }
}
