package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import javax.swing.JProgressBar;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.ini4j.Wini;
import common.Config;
import common.Validator;

public class Importer {
    private Validator validator = new Validator();

    public List<String> importMultipleHeaders(List<String> headerFilePaths, JProgressBar progressBar) throws IOException {
        String headerFolderPath = "config/header";
        return importMultipleTemplates(Config.getHeaderFile(), headerFolderPath, headerFilePaths, false, progressBar);
    }

    public List<String> importMultipleObjects(List<String> objectFilePaths, JProgressBar progressBar) throws IOException {
        String objectFolderPath = "config/object";
        return importMultipleTemplates(Config.getObjectFile(), objectFolderPath, objectFilePaths, true, progressBar);
    }

    private List<String> importMultipleTemplates(Wini store, String templateFolderPath, List<String> templateFilePaths, boolean allowRefAttribute, JProgressBar progressBar) {
        progressBar.setValue(10);
        List<String> templates = new ArrayList<String>();
        int totalFiles = templateFilePaths.size();
        Logger.cleanLogError();

        for(String templateFilePath : templateFilePaths) {
            String template = importSingleTemplate(store, templateFolderPath, templateFilePath, allowRefAttribute);
            templates.add(template);
            progressBar.setValue(progressBar.getValue() + 90 / totalFiles);
        }

        return templates;
    }

    private String importSingleTemplate(Wini store, String templateFolderPath, String templatePath, boolean allowRefAttribute) {
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
            validateTemplate(reader, allowRefAttribute);

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

    public String duplicateHeader(String sourceHeaderName, String targetHeaderName) throws IOException {
        String headerFolderPath = "config/header";
        return duplicateTemplate(Config.getHeaderFile(), headerFolderPath, sourceHeaderName, targetHeaderName);
    }

    public String duplicateObject(String sourceObjectName, String targetObjectName) throws IOException {
        String objectFolderPath = "config/object";
        return duplicateTemplate(Config.getObjectFile(), objectFolderPath, sourceObjectName, targetObjectName);
    }

    private String duplicateTemplate(Wini store, String templateFolderPath, String sourceTemplateName, String targetTemplateName) {
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
            return targetTemplateName;
        } catch (IOException e) {
            Logger.writeLogError(e.getMessage(), Logger.LogType.SEVERE);
            return "";
        }
    }

    private boolean validateTemplate(XMLEventReader template, boolean allowRefAttribute) throws XMLStreamException {
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
                    validator.validateAttribute(attributeName, attributeValue, allowRefAttribute);
                }
                stack.push(tagName);
                continue;
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