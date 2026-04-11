package com.simulation.output;
import com.simulation.core.OutputHandler;
import com.simulation.data.ReadOnlyField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class CsvOutputHandler extends OutputHandler {
    private final String outDir;
    public CsvOutputHandler(String outDir) {
        this.outDir = outDir;
        new File(this.outDir).mkdirs();
    }
    @Override
    public void writeOutput(ReadOnlyField<Double> field, int step) {
        String fileName = outDir + File.separator + "field_step_" + step + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int y = 0; y < field.getSizeY(); y++) {
                for (int x = 0; x < field.getSizeX(); x++) {
                    writer.print(field.getValue(x, y));
                    if (x < field.getSizeX() - 1) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
