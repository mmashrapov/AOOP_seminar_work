package com.simulation.data;

/**
 * A read-only interface to Field data preventing accidental mutation
 * without requiring deep array copying.
 * 
 * Supports ownership boundary passing.
 */
public interface ReadOnlyField<T extends Number> {
    T getValue(int x, int y);

    int getSizeX();

    int getSizeY();
}
