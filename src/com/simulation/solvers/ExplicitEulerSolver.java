package com.simulation.solvers;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
public class ExplicitEulerSolver extends com.simulation.core.Solver<Double> {
    private final SimulationDomain domain;
    private final java.util.List<com.simulation.core.BoundaryCondition> boundaryConditions;
    public ExplicitEulerSolver(SimulationDomain domain) {
        this.domain = domain;
        this.boundaryConditions = new java.util.ArrayList<>();
    }
    public ExplicitEulerSolver(SimulationDomain domain,
            java.util.List<com.simulation.core.BoundaryCondition> boundaryConditions) {
        this.domain = domain;
        this.boundaryConditions = boundaryConditions;
    }
    @Override
    public void step(Field<Double> state, double dt, PhysicalModel<Double> model) {
        int nx = state.getSizeX();
        int ny = state.getSizeY();
        double dx = domain.getDx();
        double dy = domain.getDy();
        double[][] nextData = new double[nx][ny];
        for (int i = 1; i < nx - 1; i++) {
            for (int j = 1; j < ny - 1; j++) {
                double currentVal = state.getValue(i, j).doubleValue();
                double left = state.getValue(i - 1, j).doubleValue();
                double right = state.getValue(i + 1, j).doubleValue();
                double up = state.getValue(i, j + 1).doubleValue();
                double down = state.getValue(i, j - 1).doubleValue();
                double ddx = (right - left) / (2 * dx);
                double ddy = (up - down) / (2 * dy);
                double d2dx2 = (right - 2 * currentVal + left) / (dx * dx);
                double d2dy2 = (up - 2 * currentVal + down) / (dy * dy);
                double timeDerivative = model.computeTimeDerivative(currentVal, ddx, ddy, d2dx2, d2dy2);
                nextData[i][j] = currentVal + timeDerivative * dt;
            }
        }
        if (boundaryConditions.isEmpty()) {
            for (int i = 0; i < nx; i++) {
                for (int j = 0; j < ny; j++) {
                    if (i == 0 || i == nx - 1 || j == 0 || j == ny - 1) {
                        nextData[i][j] = state.getValue(i, j).doubleValue();
                    }
                }
            }
        } else {
            for (com.simulation.core.BoundaryCondition bc : boundaryConditions) {
                bc.applyBoundaryCondition(state, nextData);
            }
        }
        Number[][] newDataObj = new Number[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                newDataObj[i][j] = nextData[i][j];
            }
        }
        state.swapData(newDataObj);
    }
}
