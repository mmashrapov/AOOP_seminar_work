package com.simulation.logging;

import com.simulation.exceptions.SimulationException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulationLogger {
    private static final String LOG_FILE = "simulation.log";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static void logToFile(String level, String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = dateFormat.format(new Date());
            writer.printf("[%s] %s: %s%n", timestamp, level, message);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    public static void info(String message) {
        System.out.println("[INFO] " + message);
        logToFile("INFO", message);
    }

    public static void warn(String message) {
        System.out.println("[WARN] " + message);
        logToFile("WARN", message);
    }

    public static void error(SimulationException e) {
        System.err.println("[ERROR] " + e.toString());
        logToFile("ERROR", e.toString());
        if (e.getCause() != null) {
            e.getCause().printStackTrace(System.err);
            logToFile("ERROR CAUSE", e.getCause().toString());
        }
    }
    
    public static void error(String message, Throwable t) {
        System.err.println("[ERROR] " + message + " - " + t.getMessage());
        logToFile("ERROR", message + " - " + t.getMessage());
    }
}
