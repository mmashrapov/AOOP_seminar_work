package com.simulation;
import com.simulation.core.SimulationController;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
import com.simulation.domain.Grid2D;
import com.simulation.models.HeatTransferModel;
import com.simulation.parallel.ParallelSimulationController;
import com.simulation.strategy.FixedDtPolicy;
import com.simulation.strategy.ExplicitEulerStepper;
public class ParallelMain {
    public static void main(String[] args) {
        int sizeX = 100;
        int sizeY = 100;
        SimulationDomain domain = new Grid2D(sizeX, sizeY, 0.1, 0.1);
        Field<Double> initialField = new Field<Double>(sizeX, sizeY, 0.0);
        initialField.setValue(sizeX / 2, sizeY / 2, 100.0); 
        int steps = 100;
        int numWorkers = 4;
        System.out.println("==================================================");
        System.out.println("SEMINAR 10: CONCURRENT SIMULATION");
        System.out.println("==================================================");
        System.out.println("\n--- 1. Sequential Execution ---");
        long seqStart = System.currentTimeMillis();
        SimulationController seqController = new SimulationController(
                new HeatTransferModel(0.01),
                new ExplicitEulerStepper(domain),
                new FixedDtPolicy(0.1),
                domain,
                steps);
        seqController.run(new Field<Double>(initialField));
        long seqEnd = System.currentTimeMillis();
        long seqTime = seqEnd - seqStart;
        System.out.println("Sequential Time: " + seqTime + " ms");
        System.out.println("\n--- 2. Parallel Execution (Workers: " + numWorkers + ") ---");
        long parStart = System.currentTimeMillis();
        ParallelSimulationController parController = new ParallelSimulationController(
                new HeatTransferModel(0.01),
                new FixedDtPolicy(0.1),
                domain,
                steps,
                numWorkers);
        parController.run(new Field<Double>(initialField));
        long parEnd = System.currentTimeMillis();
        long parTime = parEnd - parStart;
        System.out.println("Parallel Time: " + parTime + " ms");
        System.out.println("\n--- Results Summary ---");
        System.out.println("Grid Size: " + sizeX + " x " + sizeY);
        System.out.println("Sequential: " + seqTime + " ms");
        System.out.println("Parallel:   " + parTime + " ms");
        double ratio = (double) seqTime / Math.max(1, parTime);
        System.out.printf("Speedup:    %.2fx\n", ratio);
        System.out.println(
                "\nNote: For small grids, synchronization overhead may cause parallel execution to be slower.");
    }
}
