package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.salt.RandomIVGenerator;


public class Config {

    public static Wini getConfigPath() throws InvalidFileFormatException, IOException {
        File file = new File("configs/path.ini") {{
            getParentFile().mkdirs();
            createNewFile();
            if (length() == 0) {
                FileOutputStream fos = new FileOutputStream(this);
                fos.write("[DefaultValues]\nPATH=configs/default/values.properties\n\n[DatabaseConfig]\nPATH=configs/default/database.properties".getBytes());
                fos.close();
            }
        }};

        return new Wini(file) {{
            setConfig(new org.ini4j.Config() {{
                setStrictOperator(true);
            }});
        }};
    }

    public static void setConfigPath(String section, String key, String value) throws InvalidFileFormatException, IOException{
        Wini ini = getConfigPath();
        ini.put(section, key, value);
        ini.store();
    }

    public static Properties getConfigDefaultValues() throws InvalidFileFormatException, IOException {
        String defaultValuesFilePath = getConfigPath().get("DefaultValues", "PATH");

        new File(defaultValuesFilePath) {{
            getParentFile().mkdirs();
            createNewFile();
        }};

        return new Properties() {{
            load(new FileInputStream(defaultValuesFilePath));
        }};
    }

    public static XMLEventReader getTemplate(String templateName) throws InvalidFileFormatException, IOException, XMLStreamException {
        String templateFilePath = getConfigPath().get(templateName, "PATH");

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(templateFilePath));

        return reader;
    }

    public static Properties getConfigDatabase() throws IOException {
        String databaseFilePath = getConfigPath().get("DatabaseConfig", "PATH");

        new File(databaseFilePath) {{
            getParentFile().mkdirs();
            createNewFile();
            if (length() == 0) {
                FileOutputStream fos = new FileOutputStream(this);
                fos.write("HOST=\nPORT=\nNAME=\nUSER=\nPASSWORD=".getBytes());
                fos.close();
            }
        }};

        Properties properties = new EncryptableProperties(getEncryptor());
        properties.load(new FileInputStream(databaseFilePath));

        return properties;
    }

    public static void setConfigDatabase(HashMap<String, String> configs) throws IOException {
        String databaseFilePath = getConfigPath().get("DatabaseConfig", "PATH");

        Properties properties = getConfigDatabase();

        StandardPBEStringEncryptor encryptor = getEncryptor();

        for(Map.Entry<String, String> entry : configs.entrySet()) {
            properties.put(entry.getKey(), "ENC(" + encryptor.encrypt(entry.getValue()) + ")");
        }
        properties.store(new FileOutputStream(databaseFilePath), null);
    }

    private static StandardPBEStringEncryptor getEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();     
        encryptor.setPassword("OpenWayGroup");
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIVGenerator(new RandomIVGenerator());
        
        return encryptor;
    }

}
