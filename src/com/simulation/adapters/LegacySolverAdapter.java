package com.simulation.adapters;
import com.simulation.core.Solver;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
import com.simulation.legacy.LegacyHeatModule;
public class LegacySolverAdapter extends Solver<Double> {
    private final SimulationDomain domain;
    private final double alpha; 
    public LegacySolverAdapter(SimulationDomain domain, double alpha) {
        this.domain = domain;
        this.alpha = alpha;
    }
    @Override
    public void step(Field<Double> field, double dt, PhysicalModel<Double> model) {
        int nx = field.getSizeX();
        int ny = field.getSizeY();
        double dx = domain.getDx();
        double dy = domain.getDy();
        double[][] rawArray = new double[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                rawArray[i][j] = field.getValue(i, j).doubleValue();
            }
        }
        LegacyHeatModule.legacy_heat_step(rawArray, nx, ny, dx, dy, dt, alpha);
        Number[][] newDataObj = new Number[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                newDataObj[i][j] = rawArray[i][j];
            }
        }
        field.swapData(newDataObj);
    }
}
