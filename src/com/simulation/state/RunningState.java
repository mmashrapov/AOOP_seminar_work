package com.simulation.state;
import com.simulation.core.SimulationController;
import com.simulation.events.EventType;
import com.simulation.events.SimulationEvent;
public class RunningState implements ISimulationState {
    @Override
    public void enter(SimulationController controller) {
    }
    @Override
    public void handle(SimulationController controller) {
        try {
            controller.publishEvent(new SimulationEvent(EventType.ON_BEFORE_STEP, controller,
                    controller.getCurrentTime(), controller.getCurrentStep(), ""));
            controller.getStrategy().step(controller.getModel(), controller.getField(), controller.getCurrentDt());
            controller.incrementTime();
            controller.publishEvent(new SimulationEvent(EventType.ON_AFTER_STEP, controller,
                    controller.getCurrentTime(), controller.getCurrentStep(), ""));
            if (controller.getCurrentStep() >= controller.getTotalSteps()) {
                controller.changeState(new CompletedState());
            }
        } catch (com.simulation.exceptions.StabilityException e) {
            com.simulation.logging.SimulationLogger.warn("StabilityException caught, reducing dt by half: " + e.getMessage());
            controller.setDt(controller.getCurrentDt() * 0.5);
            // Do not fail, wait for the next iteration to organically retry
        } catch (com.simulation.exceptions.ConvergenceException e) {
            if (controller.getStrategy() instanceof com.simulation.strategy.ImplicitIterativeStepper) {
                com.simulation.logging.SimulationLogger.error("Implicit solver failed to converge.", e);
                controller.publishEvent(new SimulationEvent(EventType.ON_ERROR, controller, controller.getCurrentTime(),
                        controller.getCurrentStep(), e.getMessage()));
                controller.changeState(new FailedState());
            } else {
                com.simulation.logging.SimulationLogger.warn("ConvergenceException caught. Switching to Implicit Stepper: " + e.getMessage());
                controller.setStrategy(new com.simulation.strategy.ImplicitIterativeStepper(controller.getDomain()));
            }
        } catch (com.simulation.exceptions.IOFailureException e) {
            com.simulation.logging.SimulationLogger.warn("Output failed, continuing simulation: " + e.getMessage());
        } catch (com.simulation.exceptions.SimulationException e) {
            com.simulation.logging.SimulationLogger.error(e);
            controller.publishEvent(new SimulationEvent(EventType.ON_ERROR, controller, controller.getCurrentTime(),
                    controller.getCurrentStep(), e.getMessage()));
            controller.changeState(new FailedState());
        } catch (Exception e) {
            com.simulation.logging.SimulationLogger.error("Unknown exception", e);
            controller.publishEvent(new SimulationEvent(EventType.ON_ERROR, controller, controller.getCurrentTime(),
                    controller.getCurrentStep(), e.getMessage()));
            controller.changeState(new FailedState());
        }
    }
    @Override
    public void exit(SimulationController controller) {
    }
}
