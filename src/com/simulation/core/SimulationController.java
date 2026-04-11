package com.simulation.core;

import com.simulation.data.Field;
import com.simulation.events.ISimulationObserver;
import com.simulation.events.SimulationEvent;
import com.simulation.events.EventType;
import com.simulation.state.ISimulationState;
import com.simulation.state.ConfiguredState;
import com.simulation.strategy.IStepperStrategy;
import com.simulation.strategy.ITimeStepPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates the model, strategy (solver), dt policy, and observers.
 * Driven entirely by a State Machine loop.
 * 
 * Memory Ownership (Seminar 9):
 * - Controller OWNS: strategy, dtPolicy, domain, observer collections.
 * - Controller SHARES: model (can be injected into multiple controllers).
 * - Controller TRANSIENT: field (injected during run(), lifecycle managed
 * externally).
 */
public class SimulationController {

    private PhysicalModel<Double> model;
    private IStepperStrategy strategy;
    private ITimeStepPolicy dtPolicy;
    private SimulationDomain domain;

    private ISimulationState currentState;
    private Field<Double> field;

    private final int totalSteps;
    private int currentStep;
    private double currentTime;
    private double currentDt;

    private final List<ISimulationObserver> observers = new ArrayList<>();

    public SimulationController(PhysicalModel<Double> initialModel, IStepperStrategy strategy, ITimeStepPolicy dtPolicy,
            SimulationDomain domain, int totalSteps) {
        this.model = initialModel;
        this.strategy = strategy;
        this.dtPolicy = dtPolicy;
        this.domain = domain;
        this.totalSteps = totalSteps;
        this.currentStep = 0;
        this.currentTime = 0.0;

        // Assume starting initial dt based on policy with 0.1 dummy prev
        this.currentDt = 0.1;

        // Enter initial state
        changeState(new ConfiguredState());
    }

    public void addObserver(ISimulationObserver observer) {
        observers.add(observer);
    }

    /** Memory Management: Allows freeing strong references to avoid leaks. */
    public void removeObserver(ISimulationObserver observer) {
        observers.remove(observer);
    }

    /** Free all observer references from heap collection. */
    public void clearObservers() {
        observers.clear();
    }

    public void publishEvent(SimulationEvent event) {
        // If the event specifically tells us to swap solver, do it directly
        // (Interaction 1)
        if (event.getType() == EventType.ON_BEFORE_STEP && "SolverSwitchRecommended".equals(event.getMetadata())) {
            System.out
                    .println("[SIMULATION] Catching SolverSwitchRecommended -> Swapping to Implicit. (Interaction #1)");
            // We swap the strategy dynamically
            // (Assuming we have access to domain. For isolation, we use a basic instance
            // but ideally we pass domain or cast existing strategy)
            if (this.strategy instanceof com.simulation.strategy.ExplicitEulerStepper) {
                // We use the available domain to instantiate the Implicit stepper properly
                this.strategy = new com.simulation.strategy.ImplicitIterativeStepper(this.domain);
            }
        }

        for (ISimulationObserver ob : observers) {
            ob.onEvent(event);
        }
    }

    public void changeState(ISimulationState newState) {
        if (this.currentState != null) {
            this.currentState.exit(this);
        }
        this.currentState = newState;
        this.currentState.enter(this);
    }

    /**
     * Starts the main lifecycle.
     */
    public void run(Field<Double> initialField) {
        this.field = initialField;

        // Trigger configured to transition into Initialized
        currentState.handle(this);

        // Trigger Initialized into Running
        currentState.handle(this);

        // Keep pumping the state machine until Completed, Failed, or Paused
        while (currentState.getClass().getSimpleName().equals("RunningState")) {
            // Calculate DT right before the step
            currentDt = dtPolicy.next_dt(field, currentTime, currentDt);
            currentState.handle(this);
        }
    }

    // --- Accessors/Mutators --- //
    public void setModel(PhysicalModel<Double> newModel) {
        this.model = newModel;
    }

    public PhysicalModel<Double> getModel() {
        return model;
    }

    public void setStrategy(IStepperStrategy newStrategy) {
        this.strategy = newStrategy;
    }

    public IStepperStrategy getStrategy() {
        return strategy;
    }

    public void setDtPolicy(ITimeStepPolicy dtPolicy) {
        this.dtPolicy = dtPolicy;
    }

    public ITimeStepPolicy getDtPolicy() {
        return dtPolicy;
    }

    public Field<Double> getField() {
        return field;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getCurrentDt() {
        return currentDt;
    }

    public ISimulationState getCurrentState() {
        return currentState;
    }

    public void incrementTime() {
        this.currentStep++;
        this.currentTime += currentDt;
    }
}
