package utils;

import java.io.File;
import java.util.HashMap;

public class CurrentValues {
    public static File SourceFile;
    public static HashMap<String, String> Attributes = new HashMap<String, String>() {{
        put("TYPE", "DEFAULT");
        put("USE", "DEFAULT");
        put("REF", "[]");
    }};

    public static void setDefaultAttributes() {
        Attributes.put("TYPE", "DEFAULT");
        Attributes.put("USE", "DEFAULT");
        Attributes.put("REF", "[]");
    }
}
