package common;

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
import org.ini4j.Wini;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.salt.RandomIVGenerator;


public class Config {
    private static Wini getIniFile(String path) throws IOException {
        return new Wini(new File(path)) {{
            setConfig(new org.ini4j.Config() {{
                setStrictOperator(true);
            }});
        }};
    }

    private static void setIniItem(Wini ini, String section, HashMap<String, String> keyValues) throws IOException {
        ini.getFile().setWritable(true);
        for(Map.Entry<String, String> entry : keyValues.entrySet()) {
            ini.put(section, entry.getKey(), entry.getValue());
        }
        ini.store();
        ini.getFile().setReadOnly();
    }

    private static void removeIniItem(Wini ini, String section) throws IOException {
        ini.getFile().setWritable(true);
        ini.remove(ini.get(section));
        ini.store();
        ini.getFile().setReadOnly();
    }

    private static Wini getStructFile() throws IOException {
        return getIniFile("config/.struct.ini");
    }

    public static String getStruct(String section, String key) throws IOException {
        return getStructFile().get(section, key);
    }

    public static void setStruct(String section, HashMap<String, String> keyValues) throws IOException{
        setIniItem(getStructFile(), section, keyValues);
    }

    public static void removeStruct(String section) throws IOException{
        removeIniItem(getStructFile(), section);
    }

    private static Wini getHeaderFile() throws IOException {
        return getIniFile("config/header/.header.ini");
    }

    public static String getHeader(String section, String key) throws IOException {
        return getHeaderFile().get(section, key);
    }

    public static void setHeader(String section, HashMap<String, String> keyValues) throws IOException{
        setIniItem(getHeaderFile(), section, keyValues);
    }

    public static void removeHeader(String section) throws IOException{
        removeIniItem(getHeaderFile(), section);
    }

    private static Wini getObjectFile() throws IOException {
        return getIniFile("config/object/.object.ini");
    }

    public static String getObject(String section, String key) throws IOException {
        return getObjectFile().get(section, key);
    }

    public static void setObject(String section, HashMap<String, String> keyValues) throws IOException{
        setIniItem(getObjectFile(), section, keyValues);
    }

    public static void removeObject(String section) throws IOException{
        removeIniItem(getObjectFile(), section);
    }

    private static StandardPBEStringEncryptor getEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();     
        encryptor.setPassword("OpenWayGroup");
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIVGenerator(new RandomIVGenerator());
        
        return encryptor;
    }

    private static Properties getPropertiesFile(String path) throws IOException {
        Properties properties = new EncryptableProperties(getEncryptor());
        properties.load(new FileInputStream(new File(path)));
        return properties;
    }

    private static void setPropertiesItem(String path, Properties properties, HashMap<String, String> keyValues) throws IOException {
        StandardPBEStringEncryptor encryptor = getEncryptor();

        for(Map.Entry<String, String> entry : keyValues.entrySet()) {
            properties.put(entry.getKey(), "ENC(" + encryptor.encrypt(entry.getValue()) + ")");
        }
        properties.store(new FileOutputStream(path), null);
    }

    private static void removePropertiesItem(String path, Properties properties, HashMap<String, String> keyValues) throws IOException {
        for(Map.Entry<String, String> entry : keyValues.entrySet()) {
            properties.remove(entry.getKey());
        }
        properties.store(new FileOutputStream(path), null);
    }

    private static Properties getFolderFile() throws IOException {
        return getPropertiesFile("config/default/folder.properties");
    }

    public static String getFolder(String key) throws IOException {
        return getFolderFile().getProperty(key);
    }

    public static void setFolder(HashMap<String, String> keyValues) throws IOException {
        setPropertiesItem("config/default/folder.properties", getFolderFile(), keyValues);
    }

    public static void removeFolder(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem("config/default/folder.properties", getFolderFile(), keyValues);
    }

    private static Properties getDatabaseFile() throws IOException {
        return getPropertiesFile("config/default/database.properties");
    }

    public static String getDatabase(String key) throws IOException {
        return getDatabaseFile().getProperty(key);
    }

    public static void setDatabase(HashMap<String, String> keyValues) throws IOException {
        setPropertiesItem("config/default/database.properties", getDatabaseFile(), keyValues);
    }

    public static void removeDatabase(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem("config/default/database.properties", getDatabaseFile(), keyValues);
    }

    private static Properties getQueryFile() throws IOException {
        return getPropertiesFile("config/default/query.properties");
    }

    public static String getQuery(String key) throws IOException {
        return getQueryFile().getProperty(key);
    }

    public static void setQuery(HashMap<String, String> keyValues) throws IOException {
        setPropertiesItem("config/default/query.properties", getQueryFile(), keyValues);
    }

    public static void removeQuery(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem("config/default/query.properties", getQueryFile(), keyValues);
    }

    private static Properties getValueFile() throws IOException {
        return getPropertiesFile("config/default/value.properties");
    }

    public static String getValue(String key) throws IOException {
        return getValueFile().getProperty(key);
    }

    public static void setValue(HashMap<String, String> keyValues) throws IOException {
        setPropertiesItem("config/default/value.properties", getValueFile(), keyValues);
    }

    public static void removeValue(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem("config/default/value.properties", getValueFile(), keyValues);
    }

    private static XMLEventReader getTemplate(Wini ini, String templateName) throws IOException, XMLStreamException  {
        String templateFilePath = ini.get(templateName, "PATH");

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(templateFilePath));

        return reader;
    }

    public static XMLEventReader getHeaderTemplate(String templateName) throws IOException, XMLStreamException  {
        return getTemplate(getHeaderFile(), templateName);
    }

    public static XMLEventReader getObjectTemplate(String templateName) throws IOException, XMLStreamException  {
        return getTemplate(getObjectFile(), templateName);
    }

    // public static Wini getConfigPath() throws InvalidFileFormatException, IOException {        
    //     return new Wini(new File("config/.path.ini")) {{
    //         setConfig(new org.ini4j.Config() {{
    //             setStrictOperator(true);
    //         }});
    //     }};
    // }

    // public static void setConfigPath(String section, String key, String value) throws InvalidFileFormatException, IOException{
    //     Wini ini = getConfigPath();
    //     ini.getFile().setWritable(true);
    //     ini.put(section, key, value);
    //     ini.store();
    //     ini.getFile().setReadOnly();
    // }

    // public static void removeConfigPath(String section) throws InvalidFileFormatException, IOException{
    //     Wini ini = getConfigPath();
    //     ini.getFile().setWritable(true);
    //     ini.remove(ini.get(section));
    //     ini.store();
    //     ini.getFile().setReadOnly();
    // }

    // public static Properties getConfigDefaultValues() throws IOException {
    //     String defaultValuesFilePath = getConfigPath().get("DefaultValues", "PATH");
    //     return new Properties() {{
    //         load(new FileInputStream(new File(defaultValuesFilePath)));
    //     }};
    // }
    
    // public static void setConfigDefaultValues(HashMap<String, String> configs) throws IOException {
    //     String defaultValuesFilePath = getConfigPath().get("DefaultValues", "PATH");

    //     Properties properties = getConfigDefaultValues();

    //     for(Map.Entry<String, String> entry : configs.entrySet()) {
    //         properties.put(entry.getKey(), entry.getValue());
    //     }
    //     properties.store(new FileOutputStream(defaultValuesFilePath), null);
    // }
    
    // public static void removeConfigDefaultValues(HashMap<String, String> configs) throws IOException {
    //     String defaultValuesFilePath = getConfigPath().get("DefaultValues", "PATH");

    //     Properties properties = getConfigDefaultValues();

    //     for(Map.Entry<String, String> entry : configs.entrySet()) {
    //         properties.remove(entry.getKey());
    //     }
    //     properties.store(new FileOutputStream(defaultValuesFilePath), null);
    // }

    // public static Properties getQueries() throws IOException {
    //     String queriesFilePath = getConfigPath().get("Queries", "PATH");
        
    //     Properties properties = new EncryptableProperties(getEncryptor());
    //     properties.load(new FileInputStream(new File(queriesFilePath)));

    //     return properties;
    // }

    // public static void setQueries(HashMap<String, String> queries) throws IOException {
    //     String defaultQueriesFilePath = getConfigPath().get("Queries", "PATH");

    //     Properties properties = getQueries();

    //     StandardPBEStringEncryptor encryptor = getEncryptor();

    //     for(Map.Entry<String, String> entry : queries.entrySet()) {
    //         properties.put(entry.getKey(), "ENC(" + encryptor.encrypt(entry.getValue()) + ")");
    //     }
    //     properties.store(new FileOutputStream(defaultQueriesFilePath), null);
    // }

    // public static void removeQueries(HashMap<String, String> queries) throws IOException {
    //     String defaultQueriesFilePath = getConfigPath().get("Queries", "PATH");

    //     Properties properties = getQueries();

    //     for (Map.Entry<String, String> entry : queries.entrySet()) {
    //         properties.remove(entry.getKey());
    //     }

    //     properties.store(new FileOutputStream(defaultQueriesFilePath), null);
    // }

    // public static XMLEventReader getTemplate(String templateName) throws IOException, XMLStreamException  {
    //     String templateFilePath = getConfigPath().get(templateName, "PATH");

    //     XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    //     XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(templateFilePath));

    //     return reader;
    // }

    // public static Properties getConfigDatabase() throws IOException {
    //     String databaseFilePath = getConfigPath().get("DatabaseConfig", "PATH");
        
    //     Properties properties = new EncryptableProperties(getEncryptor());
    //     properties.load(new FileInputStream(new File(databaseFilePath)));

    //     return properties;
    // }

    // public static void setConfigDatabase(HashMap<String, String> configs) throws IOException {
    //     String databaseFilePath = getConfigPath().get("DatabaseConfig", "PATH");

    //     Properties properties = getConfigDatabase();

    //     StandardPBEStringEncryptor encryptor = getEncryptor();

    //     for(Map.Entry<String, String> entry : configs.entrySet()) {
    //         properties.put(entry.getKey(), "ENC(" + encryptor.encrypt(entry.getValue()) + ")");
    //     }
    //     properties.store(new FileOutputStream(databaseFilePath), null);
    // }

    // private static StandardPBEStringEncryptor getEncryptor() {
    //     StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();     
    //     encryptor.setPassword("OpenWayGroup");
    //     encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
    //     encryptor.setIVGenerator(new RandomIVGenerator());
        
    //     return encryptor;
    // }

}