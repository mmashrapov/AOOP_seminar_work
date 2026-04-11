package com.simulation.solvers;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
public class ImplicitSolver extends com.simulation.core.Solver<Double> {
    private final SimulationDomain domain;
    public ImplicitSolver(SimulationDomain domain) {
        this.domain = domain;
    }
    @Override
    public void step(Field<Double> field, double dt, PhysicalModel<Double> model) {
        System.out.println("ImplicitSolver step called. (Stub for Method Overriding Demo)");
    }
}
