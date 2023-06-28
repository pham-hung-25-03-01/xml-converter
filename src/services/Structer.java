package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import org.ini4j.Wini;

import common.Config;

public class Structer {
    private interface Condition {
        public void execute() throws IOException;
    }

    public String createStruct(String structName, String typeFile, String header, String typeList, String object) throws IOException {
        String result = setStruct(structName, typeFile, header, typeList, object, () -> {
            if (Config.getStructFile().containsKey(structName)) {
                throw new IllegalArgumentException("Struct with name " + structName + " already exist");
            }
        });
        return "Created struct: " + result;
    }

    public HashMap<String, String> readStruct(String structName) throws IOException {
        structName = structName.trim().toLowerCase();
        if (structName.isBlank()) {
            throw new IllegalArgumentException("Struct name must be filled");
        }
        if (!Config.getStructFile().containsKey(structName)) {
            throw new IllegalArgumentException("Struct with name " + structName + " does not exist");
        }
        return new HashMap<String, String>(Config.getStructFile().get(structName));
    }

    public String updateStruct(String structName, String typeFile, String header, String typeList, String object) throws IOException {
        String result = setStruct(structName, typeFile, header, typeList, object, () -> {
            if (!Config.getStructFile().containsKey(structName)) {
                throw new IllegalArgumentException("Struct with name " + structName + " does not exist");
            }
        });
        return "Updated struct: " + result;
    }

    public String deleteStruct(String structName) throws IOException {
        structName = structName.trim().toLowerCase();
        if (structName.isBlank()) {
            throw new IllegalArgumentException("Struct name must be filled");
        }
        if (!Config.getStructFile().containsKey(structName)) {
            throw new IllegalArgumentException("Struct with name " + structName + " does not exist");
        }
        Config.removeStruct(structName);
        return "Deleted struct: " + structName;
    }

    public String deleteStructs(List<String> structNames) {
        List<String> deletedStructs = new ArrayList<String>();
        List<String> notDeletedStructs = new ArrayList<String>();
        for (String structName : structNames) {
            try {
                String result = deleteStruct(structName);
                deletedStructs.add(result);
            } catch (Exception e) {
                notDeletedStructs.add(structName);
            }
        }
        return "Deleted structs: " + String.join(", ", deletedStructs) +
                "\nNot deleted structs: " + String.join(", ", notDeletedStructs);
    }

    public DefaultTableModel getListStructs() throws IOException {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Struct name");
        tableModel.addColumn("Type file");
        tableModel.addColumn("Header");
        tableModel.addColumn("Type list");
        tableModel.addColumn("Object");
        Wini structFile = Config.getStructFile();
        Set<String> structNames = structFile.keySet();
        for (String structName : structNames) {
            HashMap<String, String> struct = new HashMap<String, String>(structFile.get(structName));
            tableModel.addRow(new Object[] { structName, struct.get("TYPE_FILE"), struct.get("HEADER"),
                    struct.get("TYPE_LIST"), struct.get("OBJECT") });
        }
        return tableModel;
    }

    public String duplicateStruct(String structName, String newStructName) throws IOException {
        structName = structName.trim().toLowerCase();
        newStructName = newStructName.trim().toLowerCase();
        if (structName.isBlank() || newStructName.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        if (!Config.getStructFile().containsKey(structName)) {
            throw new IllegalArgumentException("Struct with name " + structName + " does not exist");
        }
        if (Config.getStructFile().containsKey(newStructName)) {
            throw new IllegalArgumentException("Struct with name " + newStructName + " already exist");
        }
        Config.setStruct(newStructName, new HashMap<String, String>(Config.getStructFile().get(structName)));
        return newStructName;
    }

    private String setStruct(String structName, String typeFile, String header, String typeList, String object,
            Condition condition) throws IOException {
        if (structName.isBlank() || typeFile.isBlank() || header.isBlank() || typeList.isBlank() || object.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        structName = structName.trim().toLowerCase();
        typeFile = typeFile.trim();
        header = header.trim().toLowerCase();
        typeList = typeList.trim();
        object = object.trim().toLowerCase();

        isNameValid(structName, "Struct name");
        isNameValid(typeFile, "Type file");
        isNameValid(header, "Header");
        isNameValid(typeList, "Type list");
        isNameValid(object, "Object");

        condition.execute();

        if (!Config.getHeaderFile().containsKey(header)) {
            throw new IllegalArgumentException("Header with name " + header + " does not exist");
        }
        if (!Config.getObjectFile().containsKey(object)) {
            throw new IllegalArgumentException("Object with name " + object + " does not exist");
        }

        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put("TYPE_FILE", typeFile);
        attributes.put("HEADER", header);
        attributes.put("TYPE_LIST", typeList);
        attributes.put("OBJECT", object);

        Config.setStruct(structName, attributes);

        return structName;
    }

    private boolean isNameValid(String name, String type) {
        if (!name.matches("\\w+")) {
            throw new IllegalArgumentException(type + " must be contain only letters, numbers and underscore");
        }
        return true;
    }

}
