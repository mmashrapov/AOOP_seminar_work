package com.simulation;

import com.simulation.data.Field;
import com.simulation.domain.Grid2D;
import com.simulation.domain.Grid1D;
import com.simulation.models.GenericHeatTransferModel;
import com.simulation.solvers.GenericExplicitEulerSolver;

public class GenericMain {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("SEMINAR 8: GENERICS & METAPROGRAMMING DEMONSTRATION");
        System.out.println("==================================================");

        // 1. Grid mapped compile-time types (Polymorphism using generics constraints)
        Grid2D domain2D = new Grid2D(5, 5, 0.1, 0.1);
        // Grid1D domain1D = new Grid1D(5, 0.1); // Cannot be passed to Grid2D bound
        // GenericExplicitEulerSolver!

        // 2. Type safe float physics state initialization
        Field<Float> stateFloat = new Field<>(5, 5, 0.0f);
        stateFloat.setValue(2, 2, 100.0f);

        // 3. Compile-time bounded dependencies
        GenericHeatTransferModel<Float> physicsFloat = new GenericHeatTransferModel<>(0.1f, Float.class);

        // Grid2D constraints prevent passing a 1D grid dynamically here
        GenericExplicitEulerSolver<Float, Grid2D> solverFloat = new GenericExplicitEulerSolver<>(domain2D, Float.class);

        System.out.println("\n--- Step 1: Solving via Pure Metaprogramming with Float Type ---");
        System.out.println("Initial Float center (2,2) value: " + stateFloat.getValue(2, 2));

        solverFloat.step(stateFloat, 0.01f, physicsFloat);

        System.out.println("Float center (2,2) value after 1 step: " + stateFloat.getValue(2, 2));

        // --- Demonstration with Double type using same component hierarchy --- //
        Grid2D domainDouble = new Grid2D(5, 5, 0.1, 0.1);
        Field<Double> stateDouble = new Field<>(5, 5, 0.0);
        stateDouble.setValue(2, 2, 100.0);

        GenericHeatTransferModel<Double> physicsDouble = new GenericHeatTransferModel<>(0.1, Double.class);
        GenericExplicitEulerSolver<Double, Grid2D> solverDouble = new GenericExplicitEulerSolver<>(domainDouble,
                Double.class);

        System.out.println("\n--- Step 2: Identical Algorithm using Double Numeric Traits ---");
        System.out.println("Initial Double center (2,2) value: " + stateDouble.getValue(2, 2));

        solverDouble.step(stateDouble, 0.01, physicsDouble);

        System.out.println("Double center (2,2) value after 1 step: " + stateDouble.getValue(2, 2));
    }
}
