package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
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
    private static final String SYSTEM_PATH = "config/.system.properties";
    private static final String STRUCT_PATH = "config/.struct.ini";
    private static final String HEADER_PATH = "config/header/.header.ini";
    private static final String OBJECT_PATH = "config/object/.object.ini";
    private static final String DEFAULT_FOLDER_PATH = "config/default/folder.properties";
    private static final String DEFAULT_DATABASE_PATH = "config/default/database.properties";
    private static final String DEFAULT_QUERY_PATH = "config/default/query.properties";
    private static final String DEFAULT_VALUE_PATH = "config/default/value.properties";
    private static Wini getIniFile(String path) throws IOException {
//        return new Wini(new File(path)) {{
//            setConfig(new org.ini4j.Config() {{
//                setStrictOperator(true);
//            }});
//        }};
        Wini ini = new Wini(new File(path));
        org.ini4j.Config cfg = ini.getConfig();
        cfg.setStrictOperator(true);
        ini.setConfig(cfg);
        return ini;
    }

    public static void setIniItem(Wini ini, String section, HashMap<String, String> keyValues) throws IOException {
        ini.getFile().setWritable(true);
        Files.setAttribute(ini.getFile().toPath(), "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
        for(Map.Entry<String, String> entry : keyValues.entrySet()) {
            ini.put(section, entry.getKey(), entry.getValue());
        }
        ini.store();
        Files.setAttribute(ini.getFile().toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        ini.getFile().setReadOnly();
    }

    public static void removeIniItem(Wini ini, String section) throws IOException {
        ini.getFile().setWritable(true);
        Files.setAttribute(ini.getFile().toPath(), "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
        ini.remove(ini.get(section));
        ini.store();
        Files.setAttribute(ini.getFile().toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        ini.getFile().setReadOnly();
    }

    public static Wini getStructFile() throws IOException {
        return getIniFile(STRUCT_PATH);
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

    public static Wini getHeaderFile() throws IOException {
        return getIniFile(HEADER_PATH);
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

    public static Wini getObjectFile() throws IOException {
        return getIniFile(OBJECT_PATH);
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

    private static Properties getEncryptPropertiesFile(String path) throws IOException {
        Properties properties = new EncryptableProperties(getEncryptor());
        properties.load(new FileInputStream(new File(path)));
        return properties;
    }

    private static Properties getPropertiesFile(String path) throws IOException {
        return new Properties() {{
            load(new FileInputStream(new File(path)));
        }};
    }

    private static void setEncryptPropertiesItem(String path, Properties properties, HashMap<String, String> keyValues) throws IOException {
        StandardPBEStringEncryptor encryptor = getEncryptor();

        for(Map.Entry<String, String> entry : keyValues.entrySet()) {
            properties.put(entry.getKey(), "ENC(" + encryptor.encrypt(entry.getValue()) + ")");
        }
        properties.store(new FileOutputStream(path), null);
    }

    private static void setPropertiesItem(String path, Properties properties, HashMap<String, String> keyValues) throws IOException {
        for(Map.Entry<String, String> entry : keyValues.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }
        properties.store(new FileOutputStream(path), null);
    }

    private static void removePropertiesItem(String path, Properties properties, HashMap<String, String> keyValues) throws IOException {
        for(Map.Entry<String, String> entry : keyValues.entrySet()) {
            properties.remove(entry.getKey());
        }
        properties.store(new FileOutputStream(path), null);
    }

    private static Properties getSystemFile() throws IOException {
        return getPropertiesFile(SYSTEM_PATH);
    }

    public static String getSystem(String key) throws IOException {
        return getSystemFile().getProperty(key);
    }

    public static void setSystem(HashMap<String, String> keyValues) throws IOException {
        File file = new File(SYSTEM_PATH);
        file.setWritable(true);
        Files.setAttribute(file.toPath(), "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
        setPropertiesItem(SYSTEM_PATH, getSystemFile(), keyValues);
        Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        file.setReadOnly();
    }

    public static void removeSystem(HashMap<String, String> keyValues) throws IOException {
        File file = new File(SYSTEM_PATH);
        file.setWritable(true);
        Files.setAttribute(file.toPath(), "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
        removePropertiesItem(SYSTEM_PATH, getSystemFile(), keyValues);
        Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        file.setReadOnly();
    }

    private static Properties getFolderFile() throws IOException {
        return getPropertiesFile(DEFAULT_FOLDER_PATH);
    }

    public static String getFolder(String key) throws IOException {
        return getFolderFile().getProperty(key);
    }

    public static void setFolder(HashMap<String, String> keyValues) throws IOException {
        setPropertiesItem(DEFAULT_FOLDER_PATH, getFolderFile(), keyValues);
    }

    public static void removeFolder(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem(DEFAULT_FOLDER_PATH, getFolderFile(), keyValues);
    }

    public static Properties getDatabaseFile() throws IOException {
        return getEncryptPropertiesFile(DEFAULT_DATABASE_PATH);
    }

    public static String getDatabase(String key) throws IOException {
        return getDatabaseFile().getProperty(key);
    }

    public static void setDatabase(HashMap<String, String> keyValues) throws IOException {
        setEncryptPropertiesItem(DEFAULT_DATABASE_PATH, getDatabaseFile(), keyValues);
    }

    public static void removeDatabase(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem(DEFAULT_DATABASE_PATH, getDatabaseFile(), keyValues);
    }

    public static Properties getQueryFile() throws IOException {
        return getEncryptPropertiesFile(DEFAULT_QUERY_PATH);
    }

    public static String getQuery(String key) throws IOException {
        return getQueryFile().getProperty(key);
    }

    public static void setQuery(HashMap<String, String> keyValues) throws IOException {
        setEncryptPropertiesItem(DEFAULT_QUERY_PATH, getQueryFile(), keyValues);
    }

    public static void removeQuery(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem(DEFAULT_QUERY_PATH, getQueryFile(), keyValues);
    }

    public static Properties getValueFile() throws IOException {
        return getPropertiesFile(DEFAULT_VALUE_PATH);
    }

    public static String getValue(String key) throws IOException {
        return getValueFile().getProperty(key);
    }

    public static void setValue(HashMap<String, String> keyValues) throws IOException {
        setPropertiesItem(DEFAULT_VALUE_PATH, getValueFile(), keyValues);
    }

    public static void removeValue(HashMap<String, String> keyValues) throws IOException {
        removePropertiesItem(DEFAULT_VALUE_PATH, getValueFile(), keyValues);
    }

    private static XMLEventReader getTemplate(Wini ini, String templateName) throws IOException, XMLStreamException  {
        String templateFilePath = ini.get(templateName, "PATH");

        if (templateFilePath == null || templateFilePath.isBlank()) {
            throw new IllegalArgumentException("Template " + templateName + " not found");
        }

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

}