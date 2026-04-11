package com.simulation.models;
import com.simulation.core.PhysicalModel;
import com.simulation.meta.NumericOps;
import com.simulation.meta.NumericTraits;
public class GenericHeatTransferModel<T extends Number> extends PhysicalModel<T> {
    private final T alpha;
    private final NumericOps<T> ops;
    public GenericHeatTransferModel(T alpha, Class<T> typeClass) {
        this.alpha = alpha;
        this.ops = NumericTraits.get(typeClass);
    }
    @Override
    public T computeTimeDerivative(T currentVal, T ddx, T ddy, T d2dx2, T d2dy2) {
        T laplacian = ops.add(d2dx2, d2dy2);
        return ops.mul(laplacian, alpha);
    }
    @Override
    public String getName() {
        return "GenericHeatTransfer";
    }
}
