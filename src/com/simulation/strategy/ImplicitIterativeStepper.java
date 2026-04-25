package com.simulation.strategy;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
public class ImplicitIterativeStepper implements IStepperStrategy {
    private final SimulationDomain domain;
    private final int maxIterations = 5;
    public ImplicitIterativeStepper(SimulationDomain domain) {
        this.domain = domain;
    }
    @Override
    public void step(PhysicalModel<Double> model, Field<Double> state, double dt) {
        int nx = state.getSizeX();
        int ny = state.getSizeY();
        double dx = domain.getDx();
        double dy = domain.getDy();
        double[][] nextData = new double[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                nextData[i][j] = state.getValue(i, j);
            }
        }
        double threshold = 1e-5;
        double maxDelta = 0.0;
        for (int iter = 0; iter < maxIterations; iter++) {
            maxDelta = 0.0;
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
                    double newVal = state.getValue(i, j) + timeDerivative * dt;
                    maxDelta = Math.max(maxDelta, Math.abs(newVal - nextData[i][j]));
                    nextData[i][j] = newVal;
                }
            }
            if (maxDelta < threshold) {
                break;
            }
        }
        if (maxDelta >= threshold || Double.isNaN(maxDelta)) {
             throw new com.simulation.exceptions.ConvergenceException(
                 "Implicit solver failed to converge after " + maxIterations + " iterations. Max delta=" + maxDelta,
                 model.getName(), 0, 0.0, nx + "x" + ny);
        }
        for (int i = 0; i < nx; i++) {
            nextData[i][0] = state.getValue(i, 0);
            nextData[i][ny - 1] = state.getValue(i, ny - 1);
        }
        for (int j = 0; j < ny; j++) {
            nextData[0][j] = state.getValue(0, j);
            nextData[nx - 1][j] = state.getValue(nx - 1, j);
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
