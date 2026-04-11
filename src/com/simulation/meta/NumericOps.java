package com.simulation.meta;
public interface NumericOps<T extends Number> {
    T add(T a, T b);
    T sub(T a, T b);
    T mul(T a, T b);
    T div(T a, T b);
    T zero();
    T fromDouble(double v);
}
