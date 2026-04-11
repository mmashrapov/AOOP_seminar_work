package com.simulation.data;
public interface ReadOnlyField<T extends Number> {
    T getValue(int x, int y);
    int getSizeX();
    int getSizeY();
}
