package com.simulation.output;

import com.simulation.core.OutputHandler;
import com.simulation.data.Field;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Concrete output handler: writes field data to CSV file.
 */
public class CsvOutputHandler extends OutputHandler {
    private final String outputDirectory;

    public CsvOutputHandler(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void writeOutput(Field field, int step) {
        String filename = outputDirectory + "/output_step_" + step + ".csv";
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < field.getSizeX(); i++) {
                for (int j = 0; j < field.getSizeY(); j++) {
                    writer.append(String.valueOf(field.getValue(i, j)));
                    if (j < field.getSizeY() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            // System.out.println("Saved step " + step + " to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}
