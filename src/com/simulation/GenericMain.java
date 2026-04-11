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
        Grid2D domain2D = new Grid2D(5, 5, 0.1, 0.1);
        Field<Float> stateFloat = new Field<>(5, 5, 0.0f);
        stateFloat.setValue(2, 2, 100.0f);
        GenericHeatTransferModel<Float> physicsFloat = new GenericHeatTransferModel<>(0.1f, Float.class);
        GenericExplicitEulerSolver<Float, Grid2D> solverFloat = new GenericExplicitEulerSolver<>(domain2D, Float.class);
        System.out.println("\n--- Step 1: Solving via Pure Metaprogramming with Float Type ---");
        System.out.println("Initial Float center (2,2) value: " + stateFloat.getValue(2, 2));
        solverFloat.step(stateFloat, 0.01f, physicsFloat);
        System.out.println("Float center (2,2) value after 1 step: " + stateFloat.getValue(2, 2));
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
