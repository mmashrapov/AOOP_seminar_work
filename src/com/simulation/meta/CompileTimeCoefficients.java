package com.simulation.meta;
public final class CompileTimeCoefficients {
    public static final double DEFAULT_CONVERGENCE_TOLERANCE = 1e-6;
    public static final double DEFAULT_RELAXATION_FACTOR = 0.8;
    private CompileTimeCoefficients() {
    }
    public static <T extends Number> T getCourantNumber(Class<T> type) {
        if (type == Double.class) {
            return type.cast(0.5); 
        } else if (type == Float.class) {
            return type.cast(0.25f); 
        } else {
            throw new UnsupportedOperationException("Unknown numerical type constraint");
        }
    }
}
