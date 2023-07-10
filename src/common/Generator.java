package common;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Generator {
    public enum Type {
        CURRENT_DATE_dd_MM_yyyy,
        CURRENT_DATE_yyyy_MM_dd,
        CURRENT_DATE_MM_dd_yyyy,
        CURRENT_DATE_dd_MM_yyyy_HYPHEN,
        CURRENT_DATE_yyyy_MM_dd_HYPHEN,
        CURRENT_DATE_MM_dd_yyyy_HYPHEN,
        CURRENT_DATE_dd_MM_yyyy_SLASH,
        CURRENT_DATE_yyyy_MM_dd_SLASH,
        CURRENT_DATE_MM_dd_yyyy_SLASH,
        CURRENT_TIME_HH_mm_ss,
        CURRENT_TIME_HH_mm_ss_COLON,
        DAY_OF_YEAR,
        NUMBER,
        SOURCE_FILE_NAME
    }

    public String generateData(String dataName) {
        try {
            Type type = Type.valueOf(dataName);
            switch (type) {
                case CURRENT_DATE_dd_MM_yyyy:
                    return generateCurrentDate("ddMMyyyy");
                case CURRENT_DATE_yyyy_MM_dd:
                    return generateCurrentDate("yyyyMMdd");
                case CURRENT_DATE_MM_dd_yyyy:
                    return generateCurrentDate("MMddyyyy");
                case CURRENT_DATE_dd_MM_yyyy_HYPHEN:
                    return generateCurrentDate("dd-MM-yyyy");
                case CURRENT_DATE_yyyy_MM_dd_HYPHEN:
                    return generateCurrentDate("yyyy-MM-dd");
                case CURRENT_DATE_MM_dd_yyyy_HYPHEN:
                    return generateCurrentDate("MM-dd-yyyy");
                case CURRENT_DATE_dd_MM_yyyy_SLASH:
                    return generateCurrentDate("dd/MM/yyyy");
                case CURRENT_DATE_yyyy_MM_dd_SLASH:
                    return generateCurrentDate("yyyy/MM/dd");
                case CURRENT_DATE_MM_dd_yyyy_SLASH:
                    return generateCurrentDate("MM/dd/yyyy");
                case CURRENT_TIME_HH_mm_ss:
                    return generateCurrentTime("HHmmss");
                case CURRENT_TIME_HH_mm_ss_COLON:
                    return generateCurrentTime("HH:mm:ss");
                case DAY_OF_YEAR:
                    return generateDayOfYear();
                case NUMBER:
                    return generateNumber();
                case SOURCE_FILE_NAME:
                    return generateSourceFileName();
                default:
                    return "";
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid generate type: " + dataName);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot generate data: " + dataName);
        }
    }

    public String generateFileNameOutput(String structName) throws IOException, SQLException {
        String fileNameOutputPattern = Config.getStruct(structName, "FILE_NAME_OUTPUT");
        String fileNameOutput = fileNameOutputPattern.substring(1, fileNameOutputPattern.length() - 1);

        List<String> listExpressionLanguage = Data.getListExpressionLanguage(fileNameOutput);

        String dataNameExtracted = "";

        String data = "";

        for (String expressionLanguage : listExpressionLanguage) {
            switch(Data.determineType(expressionLanguage)) {
                case FROM_DB:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Data.Type.FROM_DB);
                    String query = Config.getQuery(dataNameExtracted);
                    if (query == null || query.isBlank()) {
                        throw new IllegalArgumentException("Invalid query type: " + dataNameExtracted);
                    }
                    data = SqlDataReader.getData(query);
                    break;
                case FROM_GENERATOR:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Data.Type.FROM_GENERATOR);
                    data = generateData(dataNameExtracted);
                    break;
                case FROM_DEFAULT_VALUES:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Data.Type.FROM_DEFAULT_VALUES);
                    data = Config.getValue(dataNameExtracted);
                    if (data == null) {
                        throw new IllegalArgumentException("Invalid default value type: " + dataNameExtracted);
                    }
                    break;
                default:
                    data = "";
            }
            fileNameOutput = fileNameOutput.replace(expressionLanguage, data);
        }

        if (!fileNameOutput.matches("^[^~)('!*<>:;,?\\\"*|\\\\/]+$")) {
            throw new IllegalArgumentException("Invalid file name output: " + fileNameOutputPattern);
        }

        return fileNameOutput;
    }

    private String generateCurrentDate(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    private String generateCurrentTime(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    private String generateDayOfYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
    }

    private String generateNumber() throws IOException {
        String number = Config.getStoreNumber(CurrentValues.StoreNumber);
        HashMap<String, String> result = new HashMap<String, String>();
        int numberInt = 0;
        if (number != null && !number.isBlank()) {
            numberInt = Integer.parseInt(number);
        }
        numberInt++;
        number = String.format("%05d", numberInt);
        result.put(CurrentValues.StoreNumber, number);
        Config.setStoreNumber(result);
        return number;
    }

    private String generateSourceFileName() {
        if (CurrentValues.SourceFile == null) {
            throw new IllegalArgumentException("Source file is null");
        }
        return CurrentValues.SourceFile.getName();
    }

}