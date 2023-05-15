package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

public class Config {

    public static Ini getConfigPath() throws InvalidFileFormatException, IOException{
        return new Ini(new File("configs/path.ini"));
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
