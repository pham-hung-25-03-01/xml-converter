package services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.ini4j.InvalidFileFormatException;
import services.LogWriter.LogType;
import utils.Config;
import utils.CurrentValues;
import utils.Data;
import utils.Generator;
import utils.Validator;
import utils.Data.Type;

public class Converter {

    private static IFileReader fileReader = new FileReader();
    private static Validator validator = new Validator();
    private static Generator generator = new Generator();

    private String convertSingleFileToXml(String sourceFilePath, String templateName) throws IOException {
        try {
            CurrentValues.SourceFile = new File(sourceFilePath);

            Map<String, Object> data = fileReader.readFile(CurrentValues.SourceFile);

            HashMap<String, Integer> headers = (HashMap<String, Integer>) data.get("headers");
            List<String[]> rows = (List<String[]>) data.get("rows");

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            writeXml(templateName, headers, rows, out);

            StringBuilder xml = new StringBuilder(new String(out.toByteArray(), StandardCharsets.UTF_8));

            StringBuilder xmlCleaned = removeEmptyTags(xml);

            StringBuilder prettyPrintXml = formatXml(xmlCleaned);

            String targetFileName = generator.generateTargetFileName(templateName);
            String targetFilePath = "logs/foutputs/" + targetFileName;
            File file = new File(targetFilePath) {{
                getParentFile().mkdirs();
                createNewFile();
            }};

            Files.writeString(file.toPath(), prettyPrintXml, StandardCharsets.UTF_8);

            String fprocessPath = "logs/fprocesses/" + CurrentValues.SourceFile.getName();
            File fprocess = new File(fprocessPath) {{
                getParentFile().mkdirs();
                createNewFile();
            }};

            Files.copy(CurrentValues.SourceFile.toPath(), fprocess.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Error=" + e.getMessage() + ";File=" + CurrentValues.SourceFile.getName() + ";";
            LogWriter.writeLog(message, LogType.SEVERE);
            
            String ferrorPath = "logs/ferrors/" + CurrentValues.SourceFile.getName();
            File ferror = new File(ferrorPath) {{
                getParentFile().mkdirs();
                createNewFile();
            }};

            Files.copy(CurrentValues.SourceFile.toPath(), ferror.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            return "";
        } finally {
            CurrentValues.SourceFile = null;
        }
    }

    public List<String> convertToXml(List<String> sourceFilePaths, String templateName) throws IOException {
        List<String> targetFilePaths = new ArrayList<String>();

        LogWriter.cleanLog();

        for (String sourceFilePath : sourceFilePaths) {
            String targetFilePath = convertSingleFileToXml(sourceFilePath, templateName);
            targetFilePaths.add(targetFilePath);
        }

        return targetFilePaths;
    }

    private void writeXml(String templateName, HashMap<String, Integer> headers, List<String[]> rows,
            OutputStream outputStream)
            throws XMLStreamException, InvalidFileFormatException, IOException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(outputStream);

        writer.writeStartDocument("utf-8", "1.0");

        writer.writeStartElement("ApplicationFile");

        XMLEventReader fileHeaderTemplate = Config.getTemplate("FileHeaderTemplate");

        writeData(writer, fileHeaderTemplate, null, null);

        writer.writeStartElement("ApplicationsList");

        XMLEventReader applicationTemplate = Config.getTemplate(templateName);

        for (String[] row : rows) {
            writeData(writer, applicationTemplate, headers, row);

            applicationTemplate = Config.getTemplate(templateName);
        }

        writer.writeEndElement();

        writer.writeEndElement();

        writer.writeEndDocument();

        writer.flush();
        writer.close();
    }

    private String getData(HashMap<String, Integer> headers, String[] rowData, String s)
            throws InvalidFileFormatException, IOException {
        List<String> listExpressionLanguage = Data.getListExpressionLanguage(s);

        String dataNameExtracted = "";
        String data = "";

        for (String expressionLanguage : listExpressionLanguage) {
            switch (Data.determineType(expressionLanguage)) {
                case FROM_FILE:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_FILE);
                    Integer col = headers.get(dataNameExtracted);
                    if (col == null) {
                        // throw new InvalidFileFormatException("Invalid data name: " + dataNameExtracted);
                        data = "";
                    } else {
                        data = rowData[col] == null ? "" : rowData[col];
                    }
                    break;
                case FROM_DB:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_DB);
                    data = "";
                    break;
                case FROM_GENERATOR:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_GENERATOR);
                    data = generator.generateData(dataNameExtracted);
                    break;
                case FROM_DEFAULT_VALUES:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_DEFAULT_VALUES);
                    data = Config.getConfigDefaultValues().getProperty(dataNameExtracted);
                    if (data == null) {
                        throw new IllegalArgumentException("INVALID DEFAULT VALUE TYPE: " + dataNameExtracted);
                    }
                    break;
                default:
                    data = "";
            }
            s = s.replace(expressionLanguage, data);
        }
        
        return s;
    }

    private void writeData(XMLStreamWriter writer, XMLEventReader template, HashMap<String, Integer> headers,
            String[] rowData) throws XMLStreamException, InvalidFileFormatException, IOException {
        while (template.hasNext()) {
            XMLEvent event = template.nextEvent();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();

                Iterator<Attribute> attributes = element.getAttributes();
                while (attributes.hasNext()) {
                    Attribute attribute = attributes.next();
                    String attributeName = attribute.getName().getLocalPart().toUpperCase();
                    String attributeValue = attribute.getValue().toUpperCase();
                    if (validator.validateAttribute(attributeName, attributeValue)) {
                        CurrentValues.Attributes.put(attributeName, attributeValue);
                    }
                }

                writer.writeStartElement(element.getName().getLocalPart());

                if (!validator.validateRefArray(headers, rowData)) {
                    skipChildTags(template);
                }

                continue;
            }

            if (event.isCharacters()) {
                Characters characters = event.asCharacters();

                if (!characters.isWhiteSpace()) {
                    String data = getData(headers, rowData, characters.getData());

                    if (validator.isRequired(CurrentValues.Attributes.get("USE")) && data.isBlank()) {
                        throw new InvalidFileFormatException("REQUIRED DATA IS EMPTY");
                    }

                    if (validator.validateValue(CurrentValues.Attributes.get("TYPE"), data)) {
                        writer.writeCharacters(data);
                    } else {
                        throw new InvalidFileFormatException("DATA IS NOT " + CurrentValues.Attributes.get("TYPE") + " TYPE: " + data);
                    }
                }

                CurrentValues.setDefaultAttributes();
                
                continue;
            }

            if (event.isEndElement()) {
                writer.writeEndElement();

                continue;
            }
        }
    }

    private void skipChildTags(XMLEventReader template) throws XMLStreamException {
        int count = 0;
        while (template.hasNext()) {
            XMLEvent event = template.nextEvent();

            if (event.isStartElement()) {
                count++;
            }

            if (event.isEndElement()) {
                count--;
            }

            if (count == 0) {
                break;
            }
        }
    }

    private StringBuilder removeEmptyTags(StringBuilder xml){
        String regex = "<\\w+></\\w+>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(xml);
        while (matcher.find()) {
            xml = new StringBuilder(xml.toString().replaceAll(regex, ""));
            matcher = pattern.matcher(xml);
        }
        return xml;
    }

    // private StringBuilder formatXml(StringBuilder xml) throws TransformerException {
    //     TransformerFactory transformerFactory = TransformerFactory.newInstance();
    //     Transformer transformer = transformerFactory.newTransformer();
    //     transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    //     transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
    //     StreamSource source = new StreamSource(new StringReader(xml.toString()));
    //     StringWriter output = new StringWriter();
    //     transformer.transform(source, new StreamResult(output));
    //     return new StringBuilder(output.toString());
    // }

    private StringBuilder formatXml(StringBuilder xml) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        StreamSource source = new StreamSource(new StringReader(xml.toString()));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));
        return new StringBuilder(output.toString());
    }

}