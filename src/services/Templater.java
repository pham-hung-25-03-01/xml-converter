package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import org.ini4j.Wini;

import common.Config;

public class Templater {
    public enum Type {
        HEADER,
        OBJECT
    }

    private Importer importer = new Importer();

    public DefaultTableModel getListTemplates(Type type) throws IOException {
        Wini store;
        if (type == Type.HEADER) {
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

    public String deleteTemplate(Type type, String templateName) throws IOException {
        templateName = templateName.trim().toLowerCase();
        if (templateName.isBlank()) {
            throw new IllegalArgumentException("Template name must be filled");
        }
        if (type == Type.HEADER) {
            if (!Config.getHeaderFile().containsKey(templateName)) {
                throw new IllegalArgumentException("Template with name " + templateName + " does not exist");
            }
            Files.deleteIfExists(Paths.get(Config.getHeader(templateName, "PATH")));
            Config.removeHeader(templateName);
        } else {
            if (!Config.getObjectFile().containsKey(templateName)) {
                throw new IllegalArgumentException("Template with name " + templateName + " does not exist");
            }
            Config.removeObject(templateName);
        }
        return templateName;
    }

    public HashMap<String, String> duplicateTemplate(Type type, String sourceName, String targetName) throws IOException {
        if (type == Type.HEADER) {
            return this.importer.duplicateHeader(sourceName, targetName);
        } else {
            return this.importer.duplicateObject(sourceName, targetName);
        }
    }
}
