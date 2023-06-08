/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import utils.Config;
import utils.Generator;

/**
 *
 * @author ASUS RG
 */
public class FileTemplate {
    public boolean saveFileTemplate(String templateName, String content) throws IOException{
        try 
        {
            String fileName = templateName.toLowerCase().replaceAll("\\s", "");
            String filePath = "configs/templates/" + fileName + ".xml";
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
            Files.writeString(file.toPath(), content, StandardCharsets.UTF_8);
            Generator generator = new Generator();
            Config.setConfigPath(generator.generateTemplateName(fileName), "PATH", filePath);
            return true;

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return false;
    }
}
