package com.simulation;
import com.simulation.core.OutputHandler;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationController;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
import com.simulation.domain.Grid2D;
import com.simulation.models.HeatTransferModel;
import com.simulation.models.SinglePhaseFluidFlowModel;
import com.simulation.output.CsvOutputHandler;
import com.simulation.strategy.AdaptiveDtPolicy;
import com.simulation.strategy.ExplicitEulerStepper;
import com.simulation.strategy.FixedDtPolicy;
import com.simulation.strategy.IStepperStrategy;
import com.simulation.strategy.ITimeStepPolicy;
import com.simulation.strategy.OperatorSplittingStepper;
import com.simulation.events.ConsoleLoggerObserver;
import com.simulation.events.ConvergenceObserver;
import com.simulation.events.CheckpointObserver;
import com.simulation.events.OutputObserver;
import java.io.File;
import java.util.Arrays;
public class Main {
        public static void main(String[] args) {
                int sizeX = 10;
                int sizeY = 10;
                SimulationDomain domain = new Grid2D(sizeX, sizeY, 0.1, 0.1);
                String outDir = "simulation_results";
                new File(outDir).mkdirs();
                OutputHandler csvOutput = new CsvOutputHandler(outDir);
                Field<Double> initialField = new Field<Double>(sizeX, sizeY, 0.0);
                initialField.setValue(sizeX / 2, sizeY / 2, 100.0); 
                int steps = 20;
                System.out.println("==================================================");
                System.out.println("SEMINAR 6: BEHAVIORAL PATTERNS");
                System.out.println("==================================================");
                IStepperStrategy explicitStrategy = new ExplicitEulerStepper(domain);
                ITimeStepPolicy adaptiveDtPolicy = new AdaptiveDtPolicy(0.01, 1.0);
                SimulationController controller = new SimulationController(
                                new HeatTransferModel(0.01),
                                explicitStrategy,
                                adaptiveDtPolicy,
                                domain,
                                steps); 
                controller.addObserver(new ConsoleLoggerObserver());
                controller.addObserver(new OutputObserver(csvOutput, 10));
                controller.addObserver(new CheckpointObserver(5, csvOutput));
                controller.addObserver(new ConvergenceObserver(0.001, 0.5)); 
                System.out.println("\n--- Scenario 1: Adaptive Heat with State & Observer ---");
                controller.run(new Field<Double>(initialField));
                System.out.println("\n--- Scenario 2: Operator Splitting Multi-Physics ---");
                IStepperStrategy operatorSplitStrategy = new OperatorSplittingStepper(
                                new ExplicitEulerStepper(domain),
                                Arrays.<PhysicalModel<Double>>asList(new HeatTransferModel(0.01),
                                                new SinglePhaseFluidFlowModel(0.02)));
                ITimeStepPolicy fixedDtPolicy = new FixedDtPolicy(0.1);
                SimulationController splitController = new SimulationController(
                                null, 
                                operatorSplitStrategy,
                                fixedDtPolicy,
                                domain,
                                steps);
                splitController.addObserver(new ConsoleLoggerObserver());
                splitController.run(new Field<Double>(initialField));
        }
}
