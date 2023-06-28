package services;

import java.io.IOException;
import java.util.HashMap;

import common.Config;

public class FolderPath {
    public String setInputFolderPath(String inputFolderPath) throws IOException {
        return setFolderPath("Input", inputFolderPath);
    }

    public String setOutputFolderPath(String outputFolderPath) throws IOException {
        return setFolderPath("Output", outputFolderPath);
    }

    public String setProcessFolderPath(String processFolderPath) throws IOException {
        return setFolderPath("Process", processFolderPath);
    }

    public String setErrorFolderPath(String errorFolderPath) throws IOException {
        return setFolderPath("Error", errorFolderPath);
    }

    private String setFolderPath(String typeFolder, String path) throws IOException {
        path = path.trim();
        if (path.isBlank()) {
            throw new IllegalArgumentException(typeFolder + " must be filled");
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put(typeFolder.toUpperCase(), path);
        Config.setFolder(attributes);
        return "Set " + typeFolder.toLowerCase() + ": " + path;
    }
}
