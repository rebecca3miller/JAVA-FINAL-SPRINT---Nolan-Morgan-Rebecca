package logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class CustomLogger {
    private static final String LOG_FILE = "app.log";

    private CustomLogger() {
    }

    public static void logInfo(String message) {
        write("Info: " + message);
    }

    public static void logError(String message, Exception exception) {
        String details = exception == null ? "" : " - " + exception.getMessage();
        write("Error: " + message + details);
    }

    public static void log(String message) {
        logInfo(message);
    }

    private static void write(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException exception) {
            System.err.println("Error writing to log file: " + exception.getMessage());
        }
    }
}