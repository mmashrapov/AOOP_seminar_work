package com.simulation.data;

/**
 * Encapsulates internal state variables such as temperature, pressure, or
 * concentration fields.
 * No public data members. All access goes through the provided getters/setters.
 */
public class Field<T extends Number> implements ReadOnlyField<T> {
    private Number[][] data;
    private final int sizeX;
    private final int sizeY;

    public Field(int sizeX, int sizeY, T initialValue) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.data = new Number[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                data[i][j] = initialValue;
            }
        }
    }

    /**
     * Copy constructor.
     */
    public Field(Field<T> other) {
        this.sizeX = other.getSizeX();
        this.sizeY = other.getSizeY();
        this.data = new Number[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.data[i][j] = other.getValue(i, j);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T getValue(int i, int j) {
        return (T) data[i][j];
    }

    public void setValue(int i, int j, T value) {
        data[i][j] = value;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    /**
     * Replace the internal data array entirely
     * 
     * @param newData the new data grid.
     */
    public void swapData(Number[][] newData) {
        this.data = newData;
    }

    /**
     * Provides a read-only proxy view of this Field, ensuring the receiver
     * cannot cast it back to Field to mutate or extract the primitive arrays.
     */
    public ReadOnlyField<T> readOnlyView() {
        return new ReadOnlyField<T>() {
            @Override
            public T getValue(int i, int j) {
                return Field.this.getValue(i, j);
            }

            @Override
            public int getSizeX() {
                return Field.this.getSizeX();
            }

            @Override
            public int getSizeY() {
                return Field.this.getSizeY();
            }
        };
    }
}
