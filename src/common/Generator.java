package common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Generator {
    public enum Type {
        CURRENT_DATE,
        CURRENT_TIME,
        REG_NUMBER,
        SOURCE_FILE_NAME
    }

    public String generateData(String dataName) {
        try {
            Type type = Type.valueOf(dataName);
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
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid generate type: " + dataName);
        }
    }

    public String generateTargetFileName(String sourceTemplateName) throws IOException {
        String prefix = sourceTemplateName.replaceAll("Template$", "").toLowerCase();
        String sender = Config.getValue("SENDER");
        String number = Config.getValue("NUMBER");
        String dayOfYear = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        // random unique string
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        return prefix + sender + "_" + number + "." + dayOfYear + "." + generatedString;
    }

    private String generateCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    private String generateCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    private String generateRegNumber() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    private String generateSourceFileName(File sourceFile) {
        return sourceFile.getName();
    }

}