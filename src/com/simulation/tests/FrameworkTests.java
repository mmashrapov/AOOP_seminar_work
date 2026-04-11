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
        int size = 5;
        SimulationDomain domain = new Grid2D(size, size, 1.0, 1.0);
        double dt = 0.5;
        double alpha = 0.1;
        Field<Double> nativeField = new Field<Double>(size, size, 0.0);
        nativeField.setValue(2, 2, 100.0); 
        Solver<Double> nativeSolver = new ExplicitEulerSolver(domain);
        PhysicalModel<Double> nativeModel = new HeatTransferModel(alpha);
        nativeSolver.step(nativeField, dt, nativeModel);
        Field<Double> legacyField = new Field<Double>(size, size, 0.0);
        legacyField.setValue(2, 2, 100.0);
        Solver<Double> adapterSolver = new LegacySolverAdapter(domain, alpha);
        adapterSolver.step(legacyField, dt, null);
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
