package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.ini4j.Wini;

import common.Config;
import common.TemplateType;
import views.ProgressDialog;

public class Templater {
    private Importer importer = new Importer();
    private Converter converter = new Converter();

    public DefaultTableModel getListTemplates(TemplateType type) throws IOException {
        Wini store;
        if (type == TemplateType.HEADER) {
            store = Config.getHeaderFile();
        } else {
            store = Config.getObjectFile();
        }
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Path");
        Set<String> names = store.keySet();
        for (String name : names) {
            tableModel.addRow(new Object[] { name, store.get(name, "PATH") });
        }
        return tableModel;
    }

    public String deleteTemplate(TemplateType type, String templateName) throws IOException {
        templateName = templateName.trim().toLowerCase();
        if (templateName.isBlank()) {
            throw new IllegalArgumentException("Template name must be filled");
        }
        if (type == TemplateType.HEADER) {
            if (!Config.getHeaderFile().containsKey(templateName)) {
                throw new IllegalArgumentException("Template with name " + templateName + " does not exist");
            }
            Files.deleteIfExists(Paths.get(Config.getHeader(templateName, "PATH")));
            Config.removeHeader(templateName);
        } else {
            if (!Config.getObjectFile().containsKey(templateName)) {
                throw new IllegalArgumentException("Template with name " + templateName + " does not exist");
            }
            Files.deleteIfExists(Paths.get(Config.getObject(templateName, "PATH")));
            Config.removeObject(templateName);
        }
        return templateName;
    }

    public HashMap<String, String> duplicateTemplate(TemplateType type, String sourceName, String targetName) throws IOException {
        if (type == TemplateType.HEADER) {
            return this.importer.duplicateHeader(sourceName, targetName);
        } else {
            return this.importer.duplicateObject(sourceName, targetName);
        }
    }

    public boolean isTemplateExist(TemplateType type, String templateName) throws IOException {
        templateName = templateName.trim().toLowerCase();
        if (type == TemplateType.HEADER) {
            return Config.getHeaderFile().containsKey(templateName);
        } else {
            return Config.getObjectFile().containsKey(templateName);
        }
    }

    public HashMap<String, String> saveTemplate(TemplateType type, JTree tree, String templateName, ProgressDialog progress) throws IOException, XMLStreamException, TransformerException {
        switch (type) {
        case HEADER:
            return this.converter.convertJTreeToHeader(tree, templateName, progress);
        case OBJECT:
            return this.converter.convertJTreeToObject(tree, templateName, progress);
        default:
            throw new IllegalArgumentException("Template type is not supported");
        }
    }

    public String[] importTemplates(TemplateType type, String[] paths, ProgressDialog progress) throws IOException {
        switch (type) {
        case HEADER:
            return this.importer.importMultipleHeaders(paths, progress);
        case OBJECT:
            return this.importer.importMultipleObjects(paths, progress);
        default:
            throw new IllegalArgumentException("Template type is not supported");
        }
    }
}
