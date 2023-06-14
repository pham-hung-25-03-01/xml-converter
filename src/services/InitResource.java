package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import utils.Config;

public class InitResource {
    public static void init() throws IOException {
        new File("logs/default.log") {{
            getParentFile().mkdirs();
            createNewFile();
        }};

        new File("configs/path.ini") {{
            getParentFile().mkdirs();
            createNewFile();
            if (length() == 0) {
                FileOutputStream fos = new FileOutputStream(this);
                fos.write((
                    "[DefaultValues]\nPATH=configs/default/values.properties\n\n" +
                    "[DatabaseConfig]\nPATH=configs/default/database.properties\n\n" +
                    "[DefaultInputFolder]\nPATH=\n\n" +
                    "[DefaultErrorFolder]\nPATH=logs/ferrors\n\n" +
                    "[DefaultOutputFolder]\nPATH=logs/foutputs\n\n" +
                    "[DefaultProcessFolder]\nPATH=logs/fprocesses\n\n" +
                    "[FileHeaderTemplate]\nPATH=configs/templates/file_header.xml"
                ).getBytes());
                fos.close();
            }
        }};

        String defaultValuesFilePath = Config.getConfigPath().get("DefaultValues", "PATH");

        new File(defaultValuesFilePath) {{
            getParentFile().mkdirs();
            createNewFile();
        }};

        String fileHeaderTemplateFilePath = Config.getConfigPath().get("FileHeaderTemplate", "PATH");

        new File(fileHeaderTemplateFilePath) {{
            getParentFile().mkdirs();
            createNewFile();
            if (length() == 0) {
                FileOutputStream fos = new FileOutputStream(this);
                fos.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<FileHeader>\n</FileHeader>".getBytes());
                fos.close();
            }
        }};

        String databaseFilePath = Config.getConfigPath().get("DatabaseConfig", "PATH");

        new File(databaseFilePath) {{
            getParentFile().mkdirs();
            createNewFile();
            if (length() == 0) {
                FileOutputStream fos = new FileOutputStream(this);
                fos.write("DB_HOST=\nDB_PORT=\nDB_NAME=\nDB_USER=\nDB_PASSWORD=".getBytes());
                fos.close();
            }
        }};
    }
}
