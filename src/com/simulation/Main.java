package com.simulation;

import com.simulation.core.OutputHandler;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationController;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
import com.simulation.domain.GridDomain;

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

/**
 * Main execution class demonstrating Behavioral Patterns (Strategy, Observer,
 * State).
 */
public class Main {
        public static void main(String[] args) {
                int sizeX = 10;
                int sizeY = 10;
                SimulationDomain domain = new GridDomain(sizeX, sizeY, 0.1, 0.1);

                String outDir = "simulation_results";
                new File(outDir).mkdirs();
                OutputHandler csvOutput = new CsvOutputHandler(outDir);

                Field initialField = new Field(sizeX, sizeY, 0.0);
                initialField.setValue(sizeX / 2, sizeY / 2, 100.0); // High source

                int steps = 20;

                // --- SEMINAR 6 EXPERIMENTS --- //
                System.out.println("==================================================");
                System.out.println("SEMINAR 6: BEHAVIORAL PATTERNS");
                System.out.println("==================================================");

                // 1. Setup Strategy Policies
                IStepperStrategy explicitStrategy = new ExplicitEulerStepper(domain);
                ITimeStepPolicy adaptiveDtPolicy = new AdaptiveDtPolicy(0.01, 1.0);

                // 2. Setup the Controller
                SimulationController controller = new SimulationController(
                                new HeatTransferModel(0.01),
                                explicitStrategy,
                                adaptiveDtPolicy,
                                domain,
                                steps); // 3. Setup and Attach Observers
                controller.addObserver(new ConsoleLoggerObserver());
                controller.addObserver(new OutputObserver(csvOutput, 10));
                controller.addObserver(new CheckpointObserver(5, csvOutput));
                controller.addObserver(new ConvergenceObserver(0.001, 0.5)); // tolerance, slowConvThreshold

                // 4. Run State Machine Execution
                // Controller transitions Configured -> Initialized -> Running -> ...
                System.out.println("\n--- Scenario 1: Adaptive Heat with State & Observer ---");
                controller.run(new Field(initialField));

                // --- Interaction 3: OperatorSplittingStepper MultiPhysics --- //
                System.out.println("\n--- Scenario 2: Operator Splitting Multi-Physics ---");

                IStepperStrategy operatorSplitStrategy = new OperatorSplittingStepper(
                                new ExplicitEulerStepper(domain),
                                Arrays.<PhysicalModel>asList(new HeatTransferModel(0.01),
                                                new SinglePhaseFluidFlowModel(0.02)));
                ITimeStepPolicy fixedDtPolicy = new FixedDtPolicy(0.1);

                SimulationController splitController = new SimulationController(
                                null, // model is overridden by OperatorSplittingStepper
                                operatorSplitStrategy,
                                fixedDtPolicy,
                                domain,
                                steps);
                splitController.addObserver(new ConsoleLoggerObserver());

                splitController.run(new Field(initialField));
        }
}
