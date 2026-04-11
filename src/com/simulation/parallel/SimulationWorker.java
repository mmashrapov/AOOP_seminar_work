package com.simulation.parallel;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import java.util.concurrent.Callable;
public class SimulationWorker implements Callable<Double> {
    private final Subdomain subdomain;
    private final PhysicalModel<Double> model;
    private final SimulationDomain domain;
    private final double dt;
    public SimulationWorker(Subdomain subdomain, PhysicalModel<Double> model, SimulationDomain domain, double dt) {
        this.subdomain = subdomain;
        this.model = model;
        this.domain = domain;
        this.dt = dt;
    }
    @Override
    public Double call() throws Exception {
        Double[][] current = subdomain.getLocalData();
        Double[][] next = subdomain.getNextLocalData();
        int nx = subdomain.getGlobalField().getSizeX();
        int localSizeY = subdomain.getRowEnd() - subdomain.getRowStart() + 2;
        int globalSizeY = subdomain.getGlobalField().getSizeY();
        double dx = domain.getDx();
        double dy = domain.getDy();
        double maxDelta = 0.0;
        for (int i = 0; i < nx; i++) {
            for (int y = 1; y < localSizeY - 1; y++) {
                int globalY = subdomain.getRowStart() + y - 1;
                if (i == 0 || i == nx - 1 || globalY == 0 || globalY == globalSizeY - 1) {
                    next[i][y] = current[i][y];
                } else {
                    double currentVal = current[i][y];
                    double left = current[i - 1][y];
                    double right = current[i + 1][y];
                    double up = current[i][y + 1];
                    double down = current[i][y - 1];
                    double ddx = (right - left) / (2 * dx);
                    double ddy = (up - down) / (2 * dy);
                    double d2dx2 = (right - 2 * currentVal + left) / (dx * dx);
                    double d2dy2 = (up - 2 * currentVal + down) / (dy * dy);
                    double timeDerivative = model.computeTimeDerivative(currentVal, ddx, ddy, d2dx2, d2dy2);
                    double nextVal = currentVal + timeDerivative * dt;
                    next[i][y] = nextVal;
                    double delta = Math.abs(nextVal - currentVal);
                    if (delta > maxDelta) {
                        maxDelta = delta;
                    }
                }
            }
        }
        return maxDelta;
    }
}
