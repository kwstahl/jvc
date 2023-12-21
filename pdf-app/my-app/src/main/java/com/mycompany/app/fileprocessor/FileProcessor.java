package com.mycompany.app.fileprocessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileProcessor {
    public static Iterator<File> getIteratorOfPdfFiles(File directoryObject) throws IOException {
        String[] extensions = { "pdf", "PDF" };
        Iterator<File> fileObjectIterator = FileUtils.iterateFiles(directoryObject, extensions, false);
        return fileObjectIterator;
    }

    public static ArrayList<CaseFile> getCaseFileArrayList(File directoryObject) throws IOException {
        ArrayList<CaseFile> caseFileArrayList = new ArrayList<CaseFile>();
        Iterator<File> fileIterator = getIteratorOfPdfFiles(directoryObject);
        while (fileIterator.hasNext()) {
            File fileObject = fileIterator.next();
            String fileName = fileObject.getName();
            String filePath = fileObject.getPath();
            CaseFile caseFile = new CaseFile(fileName, filePath);
            caseFileArrayList.add(caseFile);
        }
        return caseFileArrayList;
    }

    public static Iterator<CaseFile> getCaseFileObjectIterator(File directoryObject) throws IOException {
        ArrayList<CaseFile> caseFileArrayList = getCaseFileArrayList(directoryObject);
        return caseFileArrayList.iterator();
    }

    public static Set<String> getParentCaseNumberSet(File directoryObject) throws IOException {
        ArrayList<String> parentCaseNumberList = new ArrayList<String>();
        Iterator<CaseFile> caseFileObjectIterator = getCaseFileObjectIterator(directoryObject);
        Set<String> parentCaseNumberSet = new HashSet<>();

        while (caseFileObjectIterator.hasNext()) {
            CaseFile caseFile = caseFileObjectIterator.next();
            String parentCaseNumber = caseFile.parentCaseNumber;
            parentCaseNumberList.add(parentCaseNumber);
        }

        parentCaseNumberSet.addAll(parentCaseNumberList);
        return parentCaseNumberSet;
    }

    public static Iterator<File> makeFileIteratorFromStringSet(Set<String> stringSet) {
        Iterator<String> stringIterator = stringSet.iterator();
        ArrayList<File> fileArray = new ArrayList<File>();
        while (stringIterator.hasNext()) {
            String stringFromIterator = stringIterator.next();
            File fileFromString = new File(stringFromIterator);
            fileArray.add(fileFromString);
        }
        return fileArray.iterator();
    }

    /*
     * MUST BE RUN BEFORE CREATING CASE FOLDER OBJECTS, AND THE RETURN IS USED WHEN
     * CALLING createCaseFolderObjects()
     */
    public static ArrayList<File> createDirectoriesAndReturnArrayList(Iterator<File> directoriesToCreateIterator,
            File destinationDirectory) throws IOException {
        ArrayList<File> newDirectories = new ArrayList<File>();
        while (directoriesToCreateIterator.hasNext()) {
            String subdirectoryToCreate = directoriesToCreateIterator.next().getName();
            String parentDirectory = destinationDirectory.toString();
            String combinedDirectory = parentDirectory + "/" + subdirectoryToCreate;
            File combinedDirectoryObject = new File(combinedDirectory);
            combinedDirectoryObject.mkdir();
            newDirectories.add(combinedDirectoryObject);
        }

        return newDirectories;
    }

    /*
     * RUN AFTER CASE FOLDERS CREATED, USE OUTPUT FROM
     * createDirectoriesAndReturnArrayList() AS PARAMETER
     */
    public static ArrayList<CaseFolder> createCaseFolderObjects(ArrayList<File> directoryObjects) throws IOException {
        Iterator<File> directoryObjectIterator = directoryObjects.iterator();
        ArrayList<CaseFolder> caseFolderObjects = new ArrayList<CaseFolder>();
        while (directoryObjectIterator.hasNext()) {
            File directoryObject = directoryObjectIterator.next();
            String parentCaseNumber = directoryObject.getName();
            String directoryPath = directoryObject.getPath();
            CaseFolder caseFolder = new CaseFolder(directoryPath, parentCaseNumber);
            caseFolderObjects.add(caseFolder);
        }
        return caseFolderObjects;
    }

    public static void moveCaseFilesFromDirectoryToCaseFolder(Iterator<CaseFile> caseFileIterator,
            CaseFolder caseFolder) throws IOException {
        String caseFolderParentCaseNumber = caseFolder.parentCaseNumber;
        File caseFolderDirectoryObject = caseFolder.directoryObject;

        while (caseFileIterator.hasNext()) {

            CaseFile caseFile = caseFileIterator.next();

            String caseFileParentCaseNumber = caseFile.parentCaseNumber;
            if (caseFileParentCaseNumber.equals(caseFolderParentCaseNumber)) {
                caseFile.sendFileToDirectory(caseFolderDirectoryObject);
            }
        }
    }

    public static void moveAllFilesOutOfPrepFolder(File sourceFolder, File destinationFolder) {
        File[] files = sourceFolder.listFiles();
        for (File file : files) {
            try {
                // Move the file to the destination folder
                Files.move(file.toPath(), destinationFolder.toPath().resolve(file.getName()),
                        StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved file: " + file.getName());
            } catch (IOException e) {
                System.out.println("Failed to move file: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public static String getCurrentDateTimeString() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

};