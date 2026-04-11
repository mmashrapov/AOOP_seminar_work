package com.simulation.tests;

import com.simulation.adapters.LegacySolverAdapter;
import com.simulation.core.Solver;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
import com.simulation.domain.Grid2D;
import com.simulation.models.CompositePhysicalModel;
import com.simulation.models.HeatTransferModel;
import com.simulation.models.SinglePhaseFluidFlowModel;
import com.simulation.solvers.ExplicitEulerSolver;

public class FrameworkTests {

    public static void main(String[] args) {
        System.out.println("Starting Framework Tests...");
        try {
            testAdapterTranslationCorrectness();
            testCompositeExecution();
            System.out.println("ALL TESTS PASSED SUCCESSFULLY!");
        } catch (AssertionError e) {
            System.err.println("TEST FAILED: " + e.getMessage());
        }
    }

    private static void assertEquals(double expected, double actual, double epsilon, String message) {
        if (Math.abs(expected - actual) > epsilon) {
            throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void testAdapterTranslationCorrectness() {
        System.out.println("Running testAdapterTranslationCorrectness...");

        // Setup shared environment
        int size = 5;
        SimulationDomain domain = new Grid2D(size, size, 1.0, 1.0);
        double dt = 0.5;
        double alpha = 0.1;

        // 1. Native Approach
        Field<Double> nativeField = new Field<Double>(size, size, 0.0);
        nativeField.setValue(2, 2, 100.0); // Hotspot in center

        Solver<Double> nativeSolver = new ExplicitEulerSolver(domain);
        PhysicalModel<Double> nativeModel = new HeatTransferModel(alpha);

        nativeSolver.step(nativeField, dt, nativeModel);

        // 2. Adapter (Legacy) Approach
        Field<Double> legacyField = new Field<Double>(size, size, 0.0);
        legacyField.setValue(2, 2, 100.0);

        Solver<Double> adapterSolver = new LegacySolverAdapter(domain, alpha);
        // Note: the Legacy adapter acts as a solver but internally invokes
        // LegacyHeatModule.
        // It bypasses the PhysicalModel logic, so we pass null (or a dummy)
        adapterSolver.step(legacyField, dt, null);

        // Compare nodes to ensure mappings mirror each other
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertEquals(nativeField.getValue(i, j).doubleValue(), legacyField.getValue(i, j).doubleValue(), 1e-6,
                        String.format("Mismatch at node (%d, %d)", i, j));
            }
        }
        System.out.println(" -> testAdapterTranslationCorrectness PASSED");
    }

    private static void testCompositeExecution() {
        System.out.println("Running testCompositeExecution...");

        PhysicalModel<Double> heat = new HeatTransferModel(1.0);
        PhysicalModel<Double> flow = new SinglePhaseFluidFlowModel(2.0);

        CompositePhysicalModel composite = new CompositePhysicalModel();
        composite.addModel(heat);
        composite.addModel(flow);

        // The expected total time derivative is the sum of both derivatives
        // Let's pass dummy spatial values: current=10, ddx=0, ddy=0, d2dx2=2, d2dy2=2
        // (so laplacian = 4)
        double current = 10;
        double ddx = 0, ddy = 0;
        double d2dx2 = 2.0, d2dy2 = 2.0;

        double expectedHeatDeriv = heat.computeTimeDerivative(current, ddx, ddy, d2dx2, d2dy2);
        double expectedFlowDeriv = flow.computeTimeDerivative(current, ddx, ddy, d2dx2, d2dy2);

        double compositeDeriv = composite.computeTimeDerivative(current, ddx, ddy, d2dx2, d2dy2);

        assertEquals(expectedHeatDeriv + expectedFlowDeriv, compositeDeriv, 1e-6,
                "Composite derivative did not sum its children correctly.");

        System.out.println(" -> testCompositeExecution PASSED");
    }
}
