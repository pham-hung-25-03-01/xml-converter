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
        Wini ini = new Wini(new File("configs/path.ini"));
        org.ini4j.Config cfg = ini.getConfig();
        cfg.setStrictOperator(true);
        ini.setConfig(cfg);
        return ini;

//        return new Wini(file) {{
//            setConfig(new org.ini4j.Config() {{
//                setStrictOperator(true);
//            }});
//        }};
    }

    public static void setConfigPath(String section, String key, String value) throws InvalidFileFormatException, IOException{
        Wini ini = getConfigPath();
        ini.put(section, key, value);
        ini.store();
    }

    public static void removeConfigPath(String section) throws InvalidFileFormatException, IOException{
        Wini ini = getConfigPath();
        ini.remove(ini.get(section));
        ini.store();
    }

    public static Properties getConfigDefaultValues() throws IOException {
        String defaultValuesFilePath = getConfigPath().get("DefaultValues", "PATH");
        return new Properties() {{
            load(new FileInputStream(new File(defaultValuesFilePath)));
        }};
    }
    
    public static void setConfigDefaultValues(HashMap<String, String> configs) throws IOException {
        String defaultValuesFilePath = getConfigPath().get("DefaultValues", "PATH");

        Properties properties = getConfigDefaultValues();

        for(Map.Entry<String, String> entry : configs.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }
        properties.store(new FileOutputStream(defaultValuesFilePath), null);
    }
    
    public static void removeConfigDefaultValues(HashMap<String, String> configs) throws IOException {
        String defaultValuesFilePath = getConfigPath().get("DefaultValues", "PATH");

        Properties properties = getConfigDefaultValues();

        for(Map.Entry<String, String> entry : configs.entrySet()) {
            properties.remove(entry.getKey());
        }
        properties.store(new FileOutputStream(defaultValuesFilePath), null);
    }

    public static Properties getQueries() throws IOException {
        String queriesFilePath = getConfigPath().get("Queries", "PATH");
        return new Properties() {{
            load(new FileInputStream(new File(queriesFilePath)));
        }};
    }

    public static XMLEventReader getTemplate(String templateName) throws IOException, XMLStreamException  {
        String templateFilePath = getConfigPath().get(templateName, "PATH");

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(templateFilePath));

        return reader;
    }

    public static Properties getConfigDatabase() throws IOException {
        String databaseFilePath = getConfigPath().get("DatabaseConfig", "PATH");
        
        Properties properties = new EncryptableProperties(getEncryptor());
        properties.load(new FileInputStream(new File(databaseFilePath)));

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
