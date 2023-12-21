package com.mycompany.app.fileprocessor;

import java.io.File;
import java.nio.file.Path;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class CaseFolder {
    public String parentCaseNumber;
    public String directoryPath;
    public File directoryObject;
    public String[] fileNameList;
    public File[] fileObjectList;

    public CaseFolder(String directoryPath, String parentCaseNumber) throws IOException {
        this.directoryPath = directoryPath;
        this.parentCaseNumber = parentCaseNumber;
        this.directoryObject = new File(directoryPath);
    }
    
    
}
