package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class CustomLogger {
    private static final String LOG_FILE = "app.log";
    private static final Logger LOGGER = Logger.getLogger(CustomLogger.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException | SecurityException exception) {
            System.err.println("Error configuring log file: " + exception.getMessage());
        }
    }

    private CustomLogger() {
    }

    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    public static void logError(String message, Exception exception) {
        LOGGER.log(Level.SEVERE, message, exception);
    }

    public static void log(String message) {
        logInfo(message);
    }
}