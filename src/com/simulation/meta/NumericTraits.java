package com.simulation.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * Type traits registry for computational types.
 * Allows generic solvers to dynamically fetch arithmetic operations for generic
 * parameters.
 */
public class NumericTraits {
    private static final Map<Class<?>, NumericOps<?>> traits = new HashMap<>();

    static {
        traits.put(Double.class, new NumericOps<Double>() {
            @Override
            public Double add(Double a, Double b) {
                return a + b;
            }

            @Override
            public Double sub(Double a, Double b) {
                return a - b;
            }

            @Override
            public Double mul(Double a, Double b) {
                return a * b;
            }

            @Override
            public Double div(Double a, Double b) {
                return a / b;
            }

            @Override
            public Double zero() {
                return 0.0;
            }

            @Override
            public Double fromDouble(double v) {
                return v;
            }
        });

        traits.put(Float.class, new NumericOps<Float>() {
            @Override
            public Float add(Float a, Float b) {
                return a + b;
            }

            @Override
            public Float sub(Float a, Float b) {
                return a - b;
            }

            @Override
            public Float mul(Float a, Float b) {
                return a * b;
            }

            @Override
            public Float div(Float a, Float b) {
                return a / b;
            }

            @Override
            public Float zero() {
                return 0.0f;
            }

            @Override
            public Float fromDouble(double v) {
                return (float) v;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> NumericOps<T> get(Class<T> type) {
        NumericOps<?> ops = traits.get(type);
        if (ops == null) {
            throw new IllegalArgumentException("No numeric traits registered for type: " + type.getName());
        }
        return (NumericOps<T>) ops;
    }
}
