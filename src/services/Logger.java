package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;

import common.Config;
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

    private static final String LOG_ERROR_PATH = "log/error.log";
    private static final String LOG_DATE_PATH = "log/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".log";

    public static String getLogError() {
        try {
            return new String(Files.readAllBytes(new File(LOG_ERROR_PATH).toPath()));
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + LOG_ERROR_PATH);
        }
    }

    public static String getLogDate() {
        try {
            return new String(Files.readAllBytes(new File(LOG_DATE_PATH).toPath()));
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + LOG_DATE_PATH);
        }
    }

    public static void writeLogError(String message, LogType logType) {
        boolean append = true;
        File file = new File(LOG_ERROR_PATH);
        file.setWritable(true);
        try {
            FileHandler handler = new FileHandler(LOG_ERROR_PATH, append);
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
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + LOG_ERROR_PATH);
        } finally {
            file.setReadOnly();
        }
    }

    public static void writeLogDate(String message) {
        File file = new File(LOG_DATE_PATH);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            file.setWritable(true);
            boolean append = true;
            FileHandler handler = new FileHandler(LOG_DATE_PATH, append);
            handler.setFormatter(new LogFormatter());
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger("SERVICES");
            logger.addHandler(handler);
            logger.info(message);
            handler.close();
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + LOG_DATE_PATH);
        } finally {
            file.setReadOnly();
        }
    }

    public static void cleanLogError() {
        File file = new File(LOG_ERROR_PATH);
        file.setWritable(true);
        try {
            new FileOutputStream(file).close();
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + LOG_ERROR_PATH);
        } finally {
            file.setReadOnly();
        }
    }

}