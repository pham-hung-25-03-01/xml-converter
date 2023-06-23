package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
import utils.Config;
import utils.Validator;

public class Importer {
    private static Validator validator = new Validator();

    public List<String> importTemplates(List<String> templateFilePaths, JProgressBar progressBar) {
        progressBar.setValue(10);
        List<String> templates = new ArrayList<String>();
        int totalFiles = templateFilePaths.size();
        LogWriter.cleanLog();

        for(String templateFilePath : templateFilePaths) {
            // String templateName = templateFilePath.substring(templateFilePath.lastIndexOf("/") + 1, templateFilePath.lastIndexOf("."));
            String template = importTemplate(templateFilePath);
            templates.add(template);
            progressBar.setValue(progressBar.getValue() + 90 / totalFiles);
        }

        return templates;
    }

    public String importTemplate(String templatePath) {
        try {
            File sourceFile = new File(templatePath);

            String targetFileName = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf("."));

            if (!targetFileName.matches("^\\w+$")) {
                throw new IllegalArgumentException("Template name must be alphanumeric or underscore");
            }

            String template = targetFileName.substring(0, 1).toUpperCase() + targetFileName.substring(1);
            String templateName = template + "Template";

            if (Config.getConfigPath().containsKey(templateName)) {
                String message = "Template " + template + " already exists";
                throw new IllegalArgumentException(message);
            }

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(sourceFile));
            validateTemplate(reader);

            String targetFilePath = Config.getConfigPath().get("DefaultTemplateFolder", "PATH") + "/" + targetFileName + ".xml";
            File targetFile = new File(targetFilePath) {{
                if (!exists()) {
                    getParentFile().mkdirs();
                    createNewFile();
                }
            }};

            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Config.setConfigPath(templateName, "PATH", targetFilePath);

            return template;
        } catch (IllegalArgumentException | XMLStreamException | IOException e) {
            String message = e.getMessage() + "::" + templatePath;
            LogWriter.writeLog(message, LogWriter.LogType.SEVERE);
            return "";
        }
    }

    public String duplicateTemplate(String sourceTemplateName, String targetTemplateName) {
        try {
            String sourceTemplatePath = Config.getConfigPath().get(sourceTemplateName + "Template", "PATH");
            File sourceFile = new File(sourceTemplatePath);
            String targetTemplatePath = Config.getConfigPath().get("DefaultTemplateFolder", "PATH") + "/" + targetTemplateName.toLowerCase() + ".xml";
            File targetFile = new File(targetTemplatePath) {{
                if (!exists()) {
                    getParentFile().mkdirs();
                    createNewFile();
                }
            }};
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            String template = targetTemplateName.substring(0, 1).toUpperCase() + targetTemplateName.substring(1).toLowerCase();
            targetTemplateName = template + "Template";
            Config.setConfigPath(targetTemplateName, "PATH", targetTemplatePath);
            return template;
        } catch (IOException e) {
            LogWriter.writeLog(e.getMessage(), LogWriter.LogType.SEVERE);
            return "";
        }
    }

    private boolean validateTemplate(XMLEventReader template) throws XMLStreamException {
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
                    validator.validateAttribute(attributeName, attributeValue);
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
        return true;
    }
}
