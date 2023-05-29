package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LogWriter {

    private static String logFilePath = "logs/default.log";

    public enum LogType {
        SEVERE,
        WARNING,
        INFO,
        CONFIG,
        FINE,
        FINER,
        FINEST
    }

    public static void writeLog(String message, LogType logType) throws IOException {
        new File(logFilePath) {{
            getParentFile().mkdirs();
            createNewFile();
        }};

        boolean append = true;
        
        try {
            FileHandler handler = new FileHandler(logFilePath, append);
            Logger logger = Logger.getLogger("services");
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
            File logFile = new File(logFilePath) {{
                getParentFile().mkdirs();
                createNewFile();
            }};
            new FileOutputStream(logFile).close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
