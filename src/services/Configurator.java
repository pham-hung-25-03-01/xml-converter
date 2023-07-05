package services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import common.Config;
import common.Database;
import views.ProgressDialog;

public class Configurator {
    public String setConnectToDB(String host, String port, String dbname, String username, String password, ProgressDialog progress) throws IOException {
        if (host.isBlank() || port.isBlank() || dbname.isBlank() || username.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        host = host.trim();
        port = port.trim();
        dbname = dbname.trim();
        username = username.trim();

        progress.setProgress(45);

        if (Database.testConnection(host, port, dbname, username, password)) {
            progress.setProgress(75);

            HashMap<String, String> attributes = new HashMap<String, String>();
            attributes.put("DB_HOST", host);
            attributes.put("DB_PORT", port);
            attributes.put("DB_NAME", dbname);
            attributes.put("DB_USER", username);
            attributes.put("DB_PASSWORD", password);
            Config.setDatabase(attributes);

            progress.setProgress(100);

            return "Connected to database successfully!";
        }

        throw new RuntimeException("Connection to database failed!");
    }

    public HashMap<String, String> getConfigDatabase() throws IOException {
        Properties store = Config.getDatabaseFile();
        return getListItem(store);
    }

    public DefaultTableModel getConfigDefaultValue() throws IOException {
        Properties store = Config.getValueFile();
        HashMap<String, String> list = getListItem(store);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Value");
        for (Map.Entry<String, String> entry : list.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            model.addRow(new Object[] { key, value });
        }
        return model;
    }

    public DefaultTableModel getConfigQuery() throws IOException {
        Properties store = Config.getQueryFile();
        HashMap<String, String> list = getListItem(store);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Query");
        for (Map.Entry<String, String> entry : list.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            model.addRow(new Object[] { key, value });
        }
        return model;
    }

    public HashMap<String, String> createDefaultValue(String name, String value) throws IOException {
        Properties store = Config.getValueFile();
        HashMap<String, String> attributes = prepareCreateItem(store, name, value);
        Config.setValue(attributes);
        return new HashMap<String, String>() {{
            put("NAME", name);
            put("VALUE", value);
        }};
    }

    public HashMap<String, String> createDefaultQuery(String name, String value) throws IOException {
        Properties store = Config.getQueryFile();
        HashMap<String, String> attributes = prepareCreateItem(store, name, value);
        Config.setQuery(attributes);
        return attributes;
    }

    public HashMap<String, String> updateDefaultValue(String name, String value) throws IOException {
        Properties store = Config.getValueFile();
        HashMap<String, String> attributes = prepareUpdateItem(store, name, value);
        Config.setValue(attributes);
        return new HashMap<String, String>() {{
            put("NAME", name);
            put("VALUE", value);
        }};
    }

    public HashMap<String, String> updateDefaultQuery(String name, String value) throws IOException {
        Properties store = Config.getQueryFile();
        HashMap<String, String> attributes = prepareUpdateItem(store, name, value);
        Config.setQuery(attributes);
        return new HashMap<String, String>() {{
            put("NAME", name);
            put("VALUE", value);
        }};
    }

    public String deleteDefaultValue(String name) throws IOException {
        Properties store = Config.getValueFile();
        name = prepareDeleteItem(store, name);
        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put(name, "");
        Config.removeValue(attributes);
        return name;
    }

    public String deleteDefaultQuery(String name) throws IOException {
        Properties store = Config.getQueryFile();
        name = prepareDeleteItem(store, name);
        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put(name, "");
        Config.removeQuery(attributes);
        return name;
    }

    private HashMap<String, String> prepareCreateItem(Properties store, String name, String value) throws IOException {
        name = name.replaceAll("\\s+", " ");
        value = value.replaceAll("\\s+", " ");
        if (name.isBlank() || value.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        name = name.trim().toUpperCase();
        if (!name.matches("\\w+")) {
            throw new IllegalArgumentException("Name must be contain only letters, numbers, and underscore");
        }

        value = value.trim();

        if (store.containsKey(name)) {
            throw new IllegalArgumentException("Name already exists");
        }

        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put(name, value);

        return attributes;
    }

    private HashMap<String, String> prepareUpdateItem(Properties store, String name, String value) throws IOException {
        name = name.replaceAll("\\s+", " ");
        value = value.replaceAll("\\s+", " ");
        if (name.isBlank() || value.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        name = name.trim().toUpperCase();

        value = value.trim();

        if (!store.containsKey(name)) {
            throw new IllegalArgumentException("Name does not exist");
        }

        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put(name, value);

        return attributes;
    }

    private String prepareDeleteItem(Properties store, String name) throws IOException {
        name = name.replaceAll("\\s+", " ");
        if (name.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        name = name.trim().toUpperCase();

        if (!store.containsKey(name)) {
            throw new IllegalArgumentException("Name does not exist");
        }

        return name;
    }

    private HashMap<String, String> getListItem(Properties store) {
        Set<String> keys =  store.stringPropertyNames();
        HashMap<String, String> attributes = new HashMap<String, String>();
        for (String key : keys) {
            attributes.put(key, store.getProperty(key));
        }
        return attributes;
    }

    public void setDelimiter(String delimiter) throws IOException {
        if (delimiter.length() != 1) {
            throw new IllegalArgumentException("Delimiter must be a single character");
        }

        Config.setSystem(new HashMap<String, String>() {{
            put("DELIMITER", delimiter);
        }});
    }
}