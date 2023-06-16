package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import utils.Config;

public class LogReader {
    public static String getLog() {
        try {
            String logFilePath = Config.getConfigPath().get("DefaultLogFile", "PATH");
            return new String(Files.readAllBytes(new File(logFilePath).toPath()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
