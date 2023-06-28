package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import common.Config;
import common.Database;

public class Configurator {
    public String setConnectToDB(String host, String port, String dbname, String username, String password) throws IOException {
        if (host.isBlank() || port.isBlank() || dbname.isBlank() || username.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        host = host.trim();
        port = port.trim();
        dbname = dbname.trim();
        username = username.trim();

        if (Database.testConnection(host, port, dbname, username, password)) {
            HashMap<String, String> attributes = new HashMap<String, String>();
            attributes.put("DB_HOST", host);
            attributes.put("DB_PORT", port);
            attributes.put("DB_NAME", dbname);
            attributes.put("DB_USER", username);
            attributes.put("DB_PASSWORD", password);
            Config.setDatabase(attributes);
            return "Connected to database successfully";
        }

        throw new RuntimeException("Connection to database failed");
    }

    public HashMap<String, String> createDefaultValue(String name, String value) throws IOException {
        Properties store = Config.getValueFile();
        HashMap<String, String> attributes = prepareCreateItem(store, name, value);
        Config.setValue(attributes);
        return attributes;
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
        return attributes;
    }

    public HashMap<String, String> updateDefaultQuery(String name, String value) throws IOException {
        Properties store = Config.getQueryFile();
        HashMap<String, String> attributes = prepareUpdateItem(store, name, value);
        Config.setQuery(attributes);
        return attributes;
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

    public String deleteDefaultValues(List<String> names) {
        List<String> deleted = new ArrayList<String>();
        List<String> failed = new ArrayList<String>();
        for (String name : names) {
            try {
                deleted.add(deleteDefaultValue(name));
            } catch (Exception e) {
                failed.add(name);
            }
        }
        return "Deleted: " + String.join(", ", deleted) + "\nFailed: " + String.join(", ", failed);
    }

    public String deleteDefaultQueries(List<String> names) {
        List<String> deleted = new ArrayList<String>();
        List<String> failed = new ArrayList<String>();
        for (String name : names) {
            try {
                deleted.add(deleteDefaultQuery(name));
            } catch (Exception e) {
                failed.add(name);
            }
        }
        return "Deleted: " + String.join(", ", deleted) + "\nFailed: " + String.join(", ", failed);
    }

    private HashMap<String, String> prepareCreateItem(Properties store, String name, String value) throws IOException {
        if (name.isBlank() || value.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        name = name.trim().toUpperCase();
        value = value.trim();

        if (store.containsKey(name)) {
            throw new IllegalArgumentException("Name already exists");
        }

        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put(name, value);

        return attributes;
    }

    private HashMap<String, String> prepareUpdateItem(Properties store, String name, String value) throws IOException {
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

    public String prepareDeleteItem(Properties store, String name) throws IOException {
        if (name.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        name = name.trim().toUpperCase();

        if (!store.containsKey(name)) {
            throw new IllegalArgumentException("Name does not exist");
        }

        return name;
    }
}