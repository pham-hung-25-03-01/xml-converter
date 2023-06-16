package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import utils.Config;
import utils.LogFormatter;

public class LogWriter {
    public enum LogType {
        SEVERE,
        WARNING,
        INFO,
        CONFIG,
        FINE,
        FINER,
        FINEST
    }

    public static void writeLog(String message, LogType logType) {
        boolean append = true;
        try {
            String logFilePath = Config.getConfigPath().get("DefaultLogFile", "PATH");
            FileHandler handler = new FileHandler(logFilePath, append);
            handler.setFormatter(new LogFormatter());
            Logger logger = Logger.getLogger("SERVICES");
            logger.addHandler(handler);
            switch (logType) {
                case SEVERE:
                    logger.severe(message);
                    break;
                case WARNING:
                    logger.warning(message);
                    break;
                case INFO:
                    logger.info(message);
                    break;
                case CONFIG:
                    logger.config(message);
                    break;
                case FINE:
                    logger.fine(message);
                    break;
                case FINER:
                    logger.finer(message);
                    break;
                case FINEST:
                    logger.finest(message);
                    break;
            }
            handler.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void cleanLog() {
        try {
            String logFilePath = Config.getConfigPath().get("DefaultLogFile", "PATH");
            new FileOutputStream(new File(logFilePath)).close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
