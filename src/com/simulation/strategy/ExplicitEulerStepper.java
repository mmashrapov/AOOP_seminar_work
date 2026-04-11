package com.simulation.strategy;

import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;

/**
 * Concrete solver strategy: Explicit Euler Method.
 * Ported from older structural solver logic.
 * Single Responsibility: Only advances time via an Explicit Euler timestep.
 */
public class ExplicitEulerStepper implements IStepperStrategy {

    private final SimulationDomain domain;

    public ExplicitEulerStepper(SimulationDomain domain) {
        this.domain = domain;
    }

    @Override
    public void step(PhysicalModel<Double> model, Field<Double> state, double dt) {
        int nx = state.getSizeX();
        int ny = state.getSizeY();
        double dx = domain.getDx();
        double dy = domain.getDy();

        double[][] nextData = new double[nx][ny];

        // Central difference stencils
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i == 0 || i == nx - 1 || j == 0 || j == ny - 1) {
                    // Dirichlet boundaries
                    nextData[i][j] = state.getValue(i, j);
                } else {
                    double currentVal = state.getValue(i, j);
                    double left = state.getValue(i - 1, j);
                    double right = state.getValue(i + 1, j);
                    double up = state.getValue(i, j + 1);
                    double down = state.getValue(i, j - 1);

                    // First derivatives (central difference)
                    double ddx = (right - left) / (2 * dx);
                    double ddy = (up - down) / (2 * dy);

                    // Second derivatives
                    double d2dx2 = (right - 2 * currentVal + left) / (dx * dx);
                    double d2dy2 = (up - 2 * currentVal + down) / (dy * dy);

                    // Call the physics model
                    double timeDerivative = model.computeTimeDerivative(currentVal, ddx, ddy, d2dx2, d2dy2);

                    // Explicit Euler integration
                    nextData[i][j] = currentVal + timeDerivative * dt;
                }
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
