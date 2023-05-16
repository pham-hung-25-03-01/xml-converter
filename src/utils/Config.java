package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;


public class Config {

    public static Wini getConfigPath() throws InvalidFileFormatException, IOException{
        File file = new File("configs/path.ini");
        file.getParentFile().mkdirs();
        file.createNewFile();
        
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
    
}
