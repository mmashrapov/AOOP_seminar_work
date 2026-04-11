package com.simulation.parallel;
import com.simulation.core.PhysicalModel;
import com.simulation.core.SimulationController;
import com.simulation.core.SimulationDomain;
import com.simulation.data.Field;
import com.simulation.strategy.ITimeStepPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class ParallelSimulationController extends SimulationController {
    private final int numWorkers;
    private final SimulationDomain domain;
    private ExecutorService executor;
    public ParallelSimulationController(PhysicalModel<Double> initialModel, ITimeStepPolicy dtPolicy,
            SimulationDomain domain, int totalSteps, int numWorkers) {
        super(initialModel, null, dtPolicy, domain, totalSteps);
        this.numWorkers = numWorkers;
        this.domain = domain;
    }
    @Override
    public void run(Field<Double> initialField) {
        this.executor = Executors.newFixedThreadPool(numWorkers);
        List<Subdomain> subdomains = DomainDecomposer.split(initialField, numWorkers);
        this.setStrategy((model, state, dt) -> {
            List<SimulationWorker> workers = new ArrayList<>(subdomains.size());
            for (Subdomain sd : subdomains) {
                workers.add(new SimulationWorker(sd, model, domain, dt));
            }
            try {
                List<Future<Double>> futures = executor.invokeAll(workers);
                BoundaryExchangeManager.exchange(subdomains);
                for (Subdomain sd : subdomains) {
                    sd.writeBack();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Parallel execution interrupted.");
            }
        });
        super.run(initialField);
        executor.shutdown();
    }
}
