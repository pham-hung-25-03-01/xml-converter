package services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
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
import utils.SqlDataReader;
import utils.Validator;
import utils.Data.Type;
import utils.Validator.FormatType;

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
            String targetFilePath = Config.getConfigPath().get("DefaultOutputFolder", "PATH") + "/" + targetFileName;
            File targetFile = new File(targetFilePath) {{
                if (!exists()) {
                    getParentFile().mkdirs();
                    createNewFile();
                }
            }};

            Files.writeString(targetFile.toPath(), prettyPrintXml, StandardCharsets.UTF_8);

            String fprocessPath = Config.getConfigPath().get("DefaultProcessFolder", "PATH") + "/" + CurrentValues.SourceFile.getName();
            File fprocess = new File(fprocessPath) {{
                if (!exists()) {
                    getParentFile().mkdirs();
                    createNewFile();
                }
            }};

            Files.move(CurrentValues.SourceFile.toPath(), fprocess.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return targetFile.getAbsolutePath();
        } catch (Exception e) {
            String message = e.getMessage() + "::" + CurrentValues.SourceFile.getName();
            LogWriter.writeLog(message, LogType.SEVERE);
            
            String ferrorPath = Config.getConfigPath().get("DefaultErrorFolder", "PATH") + "/" + CurrentValues.SourceFile.getName();
            File ferror = new File(ferrorPath) {{
                if (!exists()) {
                    getParentFile().mkdirs();
                    createNewFile();
                }
            }};

            Files.move(CurrentValues.SourceFile.toPath(), ferror.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            return "";
        } finally {
            CurrentValues.SourceFile = null;
            CurrentValues.setDefaultAttributes();
        }
    }

    public List<String> convertMultipleFilesToXml(List<String> sourceFilePaths, String templateName, JProgressBar progressBar) throws IOException {
        List<String> targetFilePaths = new ArrayList<String>();

        LogWriter.cleanLog();

        int totalFiles = sourceFilePaths.size();

        for (String sourceFilePath : sourceFilePaths) {
            String targetFilePath = convertSingleFileToXml(sourceFilePath, templateName);
            targetFilePaths.add(targetFilePath);
            progressBar.setValue(progressBar.getValue() + (90 / totalFiles));
        }

        return targetFilePaths;
    }

    public Map<String, String> convertJTreeToXml(JTree tree, String templateName, JProgressBar progressBar) throws XMLStreamException, IOException, TransformerException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = outputFactory.createXMLStreamWriter(out);

        writer.writeStartDocument("utf-8", "1.0");

        progressBar.setValue(10);

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        traverseTree(writer, root);

        progressBar.setValue(50);

        writer.writeEndDocument();

        writer.flush();
        writer.close();

        StringBuilder xml = new StringBuilder(new String(out.toByteArray(), StandardCharsets.UTF_8));

        progressBar.setValue(55);

        StringBuilder prettyPrintXml = formatXml(xml);

        progressBar.setValue(60);

        String targetFileName = templateName.toLowerCase();

        String targetFilePath = Config.getConfigPath().get("DefaultTemplateFolder", "PATH") + "/" + targetFileName + ".xml";
        File targetFile = new File(targetFilePath) {{
            getParentFile().mkdirs();
            createNewFile();
        }};

        Files.writeString(targetFile.toPath(), prettyPrintXml, StandardCharsets.UTF_8);

        progressBar.setValue(95);
        
        Map<String, String> result = new HashMap<String, String>();
        
        templateName = templateName.substring(0, 1).toUpperCase() + templateName.substring(1).toLowerCase();
        result.put("templateName", templateName);

        templateName += "Template";

        Config.setConfigPath(templateName, "PATH", targetFilePath);
        result.put("targetFilePath", targetFilePath);

        progressBar.setValue(100);

        return result;
    }

    private static void traverseTree(XMLStreamWriter writer, DefaultMutableTreeNode node) throws XMLStreamException {
        String nodeText = node.getUserObject().toString();
        
        if (nodeText.matches("^tag \\(\\w+\\)$")) {
            Pattern pattern = Pattern.compile("^tag \\((.+?)\\)$");
            Matcher matcher = pattern.matcher(nodeText);
            if (matcher.find()) {
                writer.writeStartElement(matcher.group(1));
            }
        } else if (nodeText.matches("^attributes \\(.+\\)$")) {
            Pattern pattern = Pattern.compile("^attributes \\((.+?)\\)$");
            Matcher matcher = pattern.matcher(nodeText);
            if (matcher.find()) {
                String[] attributes = matcher.group(1).split(",");
                for (String attribute : attributes) {
                    String[] attributeParts = attribute.trim().split("=", -1);
                    writer.writeAttribute(attributeParts[0], attributeParts[1].substring(1, attributeParts[1].length()-1));
                }
            }
        } else if (nodeText.matches("^value \\(.+\\)$")) {
            Pattern pattern = Pattern.compile("^value \\((.+?)\\)$");
            Matcher matcher = pattern.matcher(nodeText);
            if (matcher.find()) {
                writer.writeCharacters(matcher.group(1));
            }
        }

        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            traverseTree(writer, childNode);
        }
        
        if (nodeText.matches("^tag \\(\\w+\\)$")) {
            writer.writeEndElement();
        }
    }

    private void writeXml(String templateName, HashMap<String, Integer> headers, List<String[]> rows,
            OutputStream outputStream) throws XMLStreamException, IOException, SQLException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(outputStream);

        writer.writeStartDocument("utf-8", "1.0");

        writer.writeStartElement("ApplicationFile");

        XMLEventReader fileHeaderTemplate = Config.getTemplate("FileHeaderTemplate");

        writeData(writer, fileHeaderTemplate, new HashMap<String, Integer>(), new String[] {});

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

    private String getData(HashMap<String, Integer> headers, String[] rowData, String s) throws IOException, SQLException {
        List<String> listExpressionLanguage = Data.getListExpressionLanguage(s);

        String dataNameExtracted = "";
        String data = "";

        for (String expressionLanguage : listExpressionLanguage) {
            switch (Data.determineType(expressionLanguage)) {
                case FROM_FILE:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_FILE);
                    Integer col = headers.get(dataNameExtracted);
                    if (col == null) {
                        throw new InvalidFileFormatException("Invalid data name: " + dataNameExtracted);
                    } else {
                        data = rowData[col] == null ? "" : rowData[col];
                    }
                    break;
                case FROM_DB:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_DB);
                    data = SqlDataReader.getData(dataNameExtracted);
                    break;
                case FROM_GENERATOR:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_GENERATOR);
                    data = generator.generateData(dataNameExtracted);
                    break;
                case FROM_DEFAULT_VALUES:
                    dataNameExtracted = Data.extractDataName(expressionLanguage, Type.FROM_DEFAULT_VALUES);
                    data = Config.getConfigDefaultValues().getProperty(dataNameExtracted);
                    if (data == null) {
                        throw new IllegalArgumentException("Invalid default value type: " + dataNameExtracted);
                    }
                    break;
                default:
                    data = "";
            }

            if (validator.isRequired(CurrentValues.Attributes.get("USE")) && data.isBlank()) {
                throw new InvalidFileFormatException("Required data is empty: " + dataNameExtracted);
            }

            s = s.replace(expressionLanguage, data);
        }

        s = Data.format(s, FormatType.valueOf(CurrentValues.Attributes.get("FORMAT").toUpperCase()));

        return s;
    }

    private void writeData(XMLStreamWriter writer, XMLEventReader template, HashMap<String, Integer> headers,
            String[] rowData) throws XMLStreamException, IOException, SQLException {
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

                    if (validator.validateValue(CurrentValues.Attributes.get("TYPE"), data)) {
                        writer.writeCharacters(data);
                    } else {
                        throw new InvalidFileFormatException("Data is not " + CurrentValues.Attributes.get("TYPE") + " type: " + data);
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