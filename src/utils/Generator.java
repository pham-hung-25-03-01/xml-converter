package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.ini4j.InvalidFileFormatException;

public class Generator {
    
    public enum Type {
        CURRENT_DATE,
        CURRENT_TIME,
        REG_NUMBER,
        SOURCE_FILE_NAME
    }

    public String generateData(Type type) throws InvalidFileFormatException, IOException {
        switch (type) {
            case CURRENT_DATE:
                return generateCurrentDate();
            case CURRENT_TIME:
                return generateCurrentTime();
            case REG_NUMBER:
                return generateRegNumber();
            case SOURCE_FILE_NAME:
                return generateSourceFileName(CurrentValues.SourceFile);
            default:
                return "";
        }
    }

    public String generateTargetFileName(String prefix, String suffix) throws InvalidFileFormatException, IOException {
        String sender = Config.getConfigDefaultValues().getProperty("SENDER");
        String number = Config.getConfigDefaultValues().getProperty("NUMBER");
        String dayOfYear = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        return prefix + sender + "_" + number + "." + dayOfYear + "." + suffix;
    }

    private String generateCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    private String generateCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    private String generateRegNumber() throws InvalidFileFormatException, IOException {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    private String generateSourceFileName(File sourceFile) {
        return sourceFile.getName();
    }

}
