package com.simulation.solvers;
import com.simulation.core.PhysicalModel;
import com.simulation.data.Field;
import com.simulation.domain.Grid2D;
import com.simulation.meta.CompileTimeCoefficients;
import com.simulation.meta.NumericOps;
import com.simulation.meta.NumericTraits;
public class GenericExplicitEulerSolver<T extends Number, G extends Grid2D> {
    private final G domain;
    private final Class<T> typeClass;
    private final NumericOps<T> ops;
    public GenericExplicitEulerSolver(G domain, Class<T> typeClass) {
        this.domain = domain;
        this.typeClass = typeClass;
        this.ops = NumericTraits.get(typeClass);
    }
    public void step(Field<T> state, T dt, PhysicalModel<T> model) {
        int nx = state.getSizeX();
        int ny = state.getSizeY();
        T dx = ops.fromDouble(domain.getDx());
        T dy = ops.fromDouble(domain.getDy());
        T staticCourantCondition = CompileTimeCoefficients.getCourantNumber(typeClass);
        Number[][] nextData = new Number[nx][ny];
        for (int i = 1; i < nx - 1; i++) {
            for (int j = 1; j < ny - 1; j++) {
                T currentVal = state.getValue(i, j);
                T left = state.getValue(i - 1, j);
                T right = state.getValue(i + 1, j);
                T up = state.getValue(i, j + 1);
                T down = state.getValue(i, j - 1);
                T diffX = ops.sub(right, left);
                T twoDx = ops.mul(ops.fromDouble(2.0), dx);
                T ddx = ops.div(diffX, twoDx);
                T diffY = ops.sub(up, down);
                T twoDy = ops.mul(ops.fromDouble(2.0), dy);
                T ddy = ops.div(diffY, twoDy);
                T doubleCurrent = ops.mul(ops.fromDouble(2.0), currentVal);
                T numX = ops.add(ops.sub(right, doubleCurrent), left);
                T d2dx2 = ops.div(numX, ops.mul(dx, dx));
                T numY = ops.add(ops.sub(up, doubleCurrent), down);
                T d2dy2 = ops.div(numY, ops.mul(dy, dy));
                T timeDeriv = model.computeTimeDerivative(currentVal, ddx, ddy, d2dx2, d2dy2);
                T delta = ops.mul(timeDeriv, dt);
                nextData[i][j] = ops.add(currentVal, delta);
            }
        }
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i == 0 || i == nx - 1 || j == 0 || j == ny - 1) {
                    nextData[i][j] = state.getValue(i, j);
                }
            }
        }
        state.swapData(nextData);
    }
}
