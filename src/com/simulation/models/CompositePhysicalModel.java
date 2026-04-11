package com.simulation.models;
import com.simulation.core.PhysicalModel;
import java.util.ArrayList;
import java.util.List;
public class CompositePhysicalModel extends PhysicalModel<Double> {
    private final List<PhysicalModel<Double>> models;
    public CompositePhysicalModel() {
        this.models = new ArrayList<>();
    }
    public void addModel(PhysicalModel<Double> model) {
        models.add(model);
    }
    public void removeModel(PhysicalModel<Double> model) {
        models.remove(model);
    }
    @Override
    public Double computeTimeDerivative(Double currentVal, Double ddx, Double ddy, Double d2dx2, Double d2dy2) {
        double totalTimeDerivative = 0.0;
        for (PhysicalModel<Double> model : models) {
            totalTimeDerivative += model.computeTimeDerivative(currentVal, ddx, ddy, d2dx2, d2dy2);
        }
        return totalTimeDerivative;
    }
    @Override
    public String getName() {
        if (models.isEmpty())
            return "Empty Composite Model";
        StringBuilder b = new StringBuilder("Composite(");
        for (int i = 0; i < models.size(); i++) {
            b.append(models.get(i).getName());
            if (i < models.size() - 1) {
                b.append(" + ");
            }
        }
        b.append(")");
        return b.toString();
    }
}
