package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.FileHandler;
import common.LogFormatter;

public class Logger {
    public enum LogType {
        SEVERE,
        WARNING,
        INFO,
        CONFIG,
        FINE,
        FINER,
        FINEST
    }

    private static String logErrorFilePath = "log/error.log";
    private static String logDateFilePath = "log/date.log";

    public static String getLogError() {
        try {
            return new String(Files.readAllBytes(new File(logErrorFilePath).toPath()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String getLogDate() {
        try {
            return new String(Files.readAllBytes(new File(logDateFilePath).toPath()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static void writeLogError(String message, LogType logType) {
        boolean append = true;
        try {
            FileHandler handler = new FileHandler(logErrorFilePath, append);
            handler.setFormatter(new LogFormatter());
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger("SERVICES");
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

    public static void writeLogDate(String message, LogType logType) {
        boolean append = true;
        try {
            FileHandler handler = new FileHandler(logDateFilePath, append);
            handler.setFormatter(new LogFormatter());
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger("SERVICES");
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

    public static void cleanLogError() {
        try {
            new FileOutputStream(new File(logErrorFilePath)).close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void cleanLogDate() {
        try {
            new FileOutputStream(new File(logDateFilePath)).close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}