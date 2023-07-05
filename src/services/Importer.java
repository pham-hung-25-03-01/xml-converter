package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.ini4j.Wini;
import common.Config;
import common.Data;
import common.TemplateType;
import common.Validator;
import views.ProgressDialog;

public class Importer {
    private Validator validator = new Validator();

    public String[] importMultipleHeaders(String[] headerFilePaths, ProgressDialog progress) throws IOException {
        String headerFolderPath = "config/header";
        return importMultipleTemplates(Config.getHeaderFile(), headerFolderPath, headerFilePaths, TemplateType.HEADER, progress);
    }

    public String[] importMultipleObjects(String[] objectFilePaths, ProgressDialog progress) throws IOException {
        String objectFolderPath = "config/object";
        return importMultipleTemplates(Config.getObjectFile(), objectFolderPath, objectFilePaths, TemplateType.OBJECT, progress);
    }

    private String[] importMultipleTemplates(Wini store, String templateFolderPath, String[] templateFilePaths, TemplateType type, ProgressDialog progress) {
        progress.setProgress(10);

        int totalFiles = templateFilePaths.length;
        String[] templates = new String[totalFiles];

        Logger.cleanLogError();

        for (int i = 0; i < templateFilePaths.length; i++) {
            String template = importSingleTemplate(store, templateFolderPath, templateFilePaths[i], type);
            templates[i] = template;
            progress.setProgress(progress.getProgress() + 90 / totalFiles);
        }

        return templates;
    }

    private String importSingleTemplate(Wini store, String templateFolderPath, String templatePath, TemplateType type) {
        try {
            File sourceFile = new File(templatePath);

            String templateName = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf(".")).toLowerCase();

            if (!templateName.matches("^\\w+$")) {
                throw new IllegalArgumentException("Template name must be alphanumeric or underscore");
            }

            if (store.containsKey(templateName)) {
                String message = "Template " + templateName + " already exists";
                throw new IllegalArgumentException(message);
            }

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(sourceFile));
            validateTemplate(reader, type);

            String outputFilePath = templateFolderPath + "/" + templateName + ".xml";
            File outputFile = new File(outputFilePath) {{
                if (!exists()) {
                    getParentFile().mkdirs();
                    createNewFile();
                }
            }};

            Files.copy(sourceFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Config.setIniItem(store, templateName, new HashMap<String, String>() {{
                put("PATH", outputFilePath);
            }});

            return templateName;
        } catch (IllegalArgumentException | XMLStreamException | IOException e) {
            String message = e.getMessage() + "::" + templatePath;
            Logger.writeLogError(message, Logger.LogType.SEVERE);
            return "";
        }
    }

    public HashMap<String, String> duplicateHeader(String sourceHeaderName, String targetHeaderName) throws IOException {
        String headerFolderPath = "config/header";
        return duplicateTemplate(Config.getHeaderFile(), headerFolderPath, sourceHeaderName, targetHeaderName);
    }

    public HashMap<String, String> duplicateObject(String sourceObjectName, String targetObjectName) throws IOException {
        String objectFolderPath = "config/object";
        return duplicateTemplate(Config.getObjectFile(), objectFolderPath, sourceObjectName, targetObjectName);
    }

    private HashMap<String, String> duplicateTemplate(Wini store, String templateFolderPath, String sourceTemplateName, String targetTemplateName) {
        try {
            String sourceTemplatePath = store.get(sourceTemplateName, "PATH");
            File sourceFile = new File(sourceTemplatePath);
            targetTemplateName = targetTemplateName.toLowerCase();
            String targetTemplatePath = templateFolderPath + "/" + targetTemplateName + ".xml";
            File targetFile = new File(targetTemplatePath) {{
                if (!exists()) {
                    getParentFile().mkdirs();
                    createNewFile();
                }
            }};
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Config.setIniItem(store, targetTemplateName, new HashMap<String, String>() {{
                put("PATH", targetTemplatePath);
            }});
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("NAME", targetTemplateName);
            result.put("PATH", targetTemplatePath);
            return result;
        } catch (IOException e) {
            Logger.writeLogError(e.getMessage(), Logger.LogType.SEVERE);
            return null;
        }
    }

    private boolean validateTemplate(XMLEventReader template, TemplateType type) throws XMLStreamException {
        if (!template.hasNext()) {
            throw new IllegalArgumentException("Template is empty");
        }
        Stack<String> stack = new Stack<String>();
        while (template.hasNext()) {
            XMLEvent event = template.nextEvent();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                String tagName = element.getName().getLocalPart();
                if (!tagName.matches("^[a-zA-Z]\\w*$")) {
                    throw new IllegalArgumentException("Tag name must be start with a letter and not contain special characters or spaces except underscore");
                }
                Iterator<Attribute> attributes = element.getAttributes();
                while (attributes.hasNext()) {
                    Attribute attribute = attributes.next();
                    String attributeName = attribute.getName().getLocalPart().toUpperCase();
                    String attributeValue = attribute.getValue().toUpperCase();
                    validator.validateAttribute(attributeName, attributeValue, type);
                }
                stack.push(tagName);
                continue;
            }

            if (event.isCharacters() && type == TemplateType.HEADER) {
                Characters characters = event.asCharacters();
                if (!characters.isWhiteSpace()) {
                    List<String> listExpressionLanguage = Data.getListExpressionLanguage(characters.getData());
                    for (String expressionLanguage : listExpressionLanguage) {
                        if (Data.determineType(expressionLanguage) == Data.Type.FROM_FILE) {
                            throw new IllegalArgumentException("Invalid expression language: " + expressionLanguage);
                        }
                    }
                }
            }

            if (event.isEndElement()) {
                EndElement element = event.asEndElement();
                if (!element.getName().getLocalPart().matches("^[a-zA-Z]\\w*$")) {
                    throw new IllegalArgumentException("Tag name must be start with a letter and not contain special characters or spaces except underscore");
                }
                if (!stack.pop().equals(element.getName().getLocalPart())) {
                    throw new IllegalArgumentException("Invalid template");
                }
            }
        }
        template.close();
        return true;
    }
}