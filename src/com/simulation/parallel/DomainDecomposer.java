package com.simulation.parallel;
import com.simulation.data.Field;
import java.util.ArrayList;
import java.util.List;
public class DomainDecomposer {
    public static List<Subdomain> split(Field<Double> field, int numWorkers) {
        List<Subdomain> subdomains = new ArrayList<>();
        int ny = field.getSizeY();
        int rowsPerWorker = ny / numWorkers;
        int remainder = ny % numWorkers;
        int currentStart = 0;
        for (int i = 0; i < numWorkers; i++) {
            int rows = rowsPerWorker + (i < remainder ? 1 : 0);
            if (rows == 0)
                break;
            subdomains.add(new Subdomain(currentStart, currentStart + rows, field));
            currentStart += rows;
        }
        return subdomains;
    }
}
