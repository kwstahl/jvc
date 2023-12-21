package com.mycompany.app;

import java.io.File;
import java.io.IOException;

import com.mycompany.app.fileprocessor.CaseFile;
import com.mycompany.app.fileprocessor.CaseFolder;
import com.mycompany.app.fileprocessor.FileProcessor;
import com.mycompany.app.fileprocessor.Gui;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;



public class App {
    public static void main(String[] args) throws IOException {

        // open a GUI 

        // labels

        // button that opens folder to add things into

        // button that processes the files 
        App.processFiles();
    }

    public static void processFiles() throws IOException {

        String dateTimeNow = FileProcessor.getCurrentDateTimeString();

        System.out.println("hello world");
        
        // Create file objects
        File inputDirectoryObject = new File("C:\\Users\\CL11927\\Desktop\\testFolder");
        File destinationDirectory = new File("C:\\Users\\CL11927\\Desktop\\testOutputFolder" + "/" + dateTimeNow);
        File rawDataDirectory = new File("C:\\Users\\CL11927\\Desktop\\testOutputFolder" + "/" + dateTimeNow + "/" + "Raw Data");

        // make iterator of parent casenumbers
        Set<String> parentCaseNumberSet = FileProcessor.getParentCaseNumberSet(inputDirectoryObject);
        Iterator<File> parentCaseNumberIterator = FileProcessor.makeFileIteratorFromStringSet(parentCaseNumberSet);

        // Create the folders of parent case numbers in the destination folder, return an iterator of the created folders
        ArrayList<File> createdCaseDirectories = FileProcessor
                .createDirectoriesAndReturnArrayList(parentCaseNumberIterator, destinationDirectory);
        ArrayList<CaseFolder> createdCaseFolders = FileProcessor.createCaseFolderObjects(createdCaseDirectories);
        Iterator<CaseFolder> createdCaseFoldersIterator = createdCaseFolders.iterator();

        // iterate through created folders of parent cases, for each parent iterate through the files in the input folder and send them appropriately
        while (createdCaseFoldersIterator.hasNext()) {
            Iterator<CaseFile> testIterator = FileProcessor.getCaseFileObjectIterator(inputDirectoryObject);
            CaseFolder caseFolder = createdCaseFoldersIterator.next();
            FileProcessor.moveCaseFilesFromDirectoryToCaseFolder(testIterator, caseFolder);
        }

        // Move the files out of the input directory and into a raw data folder inside of the destination folder
        rawDataDirectory.mkdir();
        FileProcessor.moveAllFilesOutOfPrepFolder(inputDirectoryObject, rawDataDirectory);

    }

}
