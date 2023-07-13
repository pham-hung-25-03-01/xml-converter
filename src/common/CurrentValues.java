package common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CurrentValues {
    public static File SourceFile;
    public static int RowNumber = 0;
    public static String StoreNumber;
    public static boolean IsGeneratedNumber = false;
    public static int AutoIncrement = 0;
    public static HashMap<String, String> Attributes = new HashMap<String, String>() {{
        put("TYPE", "DEFAULT");
        put("USE", "DEFAULT");
        put("REF", "[]");
        put("FORMAT", "DEFAULT");
    }};

    public static void setDefaultAttributes() {
        Attributes.put("TYPE", "DEFAULT");
        Attributes.put("USE", "DEFAULT");
        Attributes.put("REF", "[]");
        Attributes.put("FORMAT", "DEFAULT");
    }

    public static void decreaseNumber() throws IOException {
        if (IsGeneratedNumber) {
            String number = Config.getStoreNumber(StoreNumber);
            int numberInt = 1;
            if (number != null && !number.isBlank()) {
                numberInt = Integer.parseInt(number);
            }
            numberInt--;
            number = String.format("%05d", numberInt);
            HashMap<String, String> result = new HashMap<String, String>();
            result.put(StoreNumber, number);
            Config.setStoreNumber(result);
        }
    }
}