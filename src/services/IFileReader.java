package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IFileReader {

    public Map<String, Object> readFile(File sourceFile) throws UnsupportedEncodingException, FileNotFoundException;
    
}
