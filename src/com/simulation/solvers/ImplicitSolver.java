package com.simulation.solvers;

import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;

/**
 * Concrete solver: Implicit Method stub.
 * This class exists to fulfill the advanced inheritance hierarchy requirement
 * exactly as prescribed (Solver -> ExplicitSolver, ImplicitSolver).
 */
public class ImplicitSolver extends com.simulation.core.Solver {

    private final SimulationDomain domain;

    public ImplicitSolver(SimulationDomain domain) {
        this.domain = domain;
    }

    @Override
    public void step(Field field, double dt, PhysicalModel model) {
        // In a real scenario, this would use a matrix solver or iterative method,
        // like the logic in ImplicitIterativeStepper.
        System.out.println("ImplicitSolver step called. (Stub for Method Overriding Demo)");
    }
}
