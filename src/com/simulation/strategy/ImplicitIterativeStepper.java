package com.simulation.strategy;

import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;

/**
 * Concrete solver strategy: Implicit Iterative Method.
 * Uses a pseudo-Gauss-Seidel iterative approach to approximate an implicit step
 * constraint.
 * Designed to be swapped at runtime when explicit instability is detected.
 */
public class ImplicitIterativeStepper implements IStepperStrategy {

    private final SimulationDomain domain;
    private final int maxIterations = 5;

    public ImplicitIterativeStepper(SimulationDomain domain) {
        this.domain = domain;
    }

    @Override
    public void step(PhysicalModel model, Field state, double dt) {
        int nx = state.getSizeX();
        int ny = state.getSizeY();
        double dx = domain.getDx();
        double dy = domain.getDy();

        // Start with a guess using the old state
        double[][] nextData = new double[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                nextData[i][j] = state.getValue(i, j);
            }
        }

        // Iteratively refine the new state (simplified nonlinear implicit approach)
        for (int iter = 0; iter < maxIterations; iter++) {
            for (int i = 1; i < nx - 1; i++) {
                for (int j = 1; j < ny - 1; j++) {
                    double currentVal = nextData[i][j];
                    double left = nextData[i - 1][j];
                    double right = nextData[i + 1][j];
                    double up = nextData[i][j + 1];
                    double down = nextData[i][j - 1];

                    double ddx = (right - left) / (2 * dx);
                    double ddy = (up - down) / (2 * dy);
                    double d2dx2 = (right - 2 * currentVal + left) / (dx * dx);
                    double d2dy2 = (up - 2 * currentVal + down) / (dy * dy);

                    double timeDerivative = model.computeTimeDerivative(currentVal, ddx, ddy, d2dx2, d2dy2);

                    // Update using the backward Euler formulation estimate
                    // T_new = T_old + dt * f(T_new)
                    // To stabilize iteration, relax the update slightly
                    nextData[i][j] = state.getValue(i, j) + timeDerivative * dt;
                }
            }
        }

        // Fix boundaries
        for (int i = 0; i < nx; i++) {
            nextData[i][0] = state.getValue(i, 0);
            nextData[i][ny - 1] = state.getValue(i, ny - 1);
        }
        for (int j = 0; j < ny; j++) {
            nextData[0][j] = state.getValue(0, j);
            nextData[nx - 1][j] = state.getValue(nx - 1, j);
        }

        state.swapData(nextData);
    }
}
