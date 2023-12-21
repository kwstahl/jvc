package com.mycompany.app.fileprocessor;

import java.io.File;
import java.nio.file.Path;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.commons.io.FileUtils;

public class CaseFile {
    public String fileName;
    public String caseNumber;
    public String pdfText;
    public Path filePath;
    public File fileObject;
    public String parentCaseNumber;

    public CaseFile(String fileName, String filePath) throws IOException {
        this.fileName = fileName;
        this.fileObject = new File(filePath);
        this.filePath = this.fileObject.toPath();

        /*
         * Check first if returns null, if yes, then perform constructor on file name
         * "THCS"
         */

        String pdfText = this.getPDFText();

        if (pdfText.trim().length() > 0) {
            this.pdfText = this.getPDFText();
            this.caseNumber = this.getCaseNumberFromPDF();
            this.parentCaseNumber = this.getParentCaseNumber();
        } else {
            this.pdfText = "null";
            this.caseNumber = this.getCaseNumberFromFileName();
            this.parentCaseNumber = this.getParentCaseNumber();
        }
    }

    private String[] splitTextOnSpaces(String text) throws IOException {
        String[] splitText = text.split("\\s+");
        return splitText;
    }

    private String findSplitStringWithTwoDashes(String[] splitText) throws IOException {
        for (String element : splitText) {
            if (element.matches(".*-.*-.*")) {
                return element;
            }
        }
        return "";
    }

    public String getPDFText() throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        PDDocument document = PDDocument.load(this.fileObject);
        String pdfText = stripper.getText(document);
        document.close();
        return pdfText;
    }

    public String getCaseNumberFromPDF() throws IOException {
        String[] splitText = this.splitTextOnSpaces(this.pdfText);
        String caseNumber = this.findSplitStringWithTwoDashes(splitText);
        return caseNumber;
    }

    public String getCaseNumberFromFileName() throws IOException {
        String[] splitText = this.splitTextOnSpaces(this.fileName);
        String caseNumber = this.findSplitStringWithTwoDashes(splitText);
        return caseNumber;
    }

    public void sendFileToDirectory(File destination) throws IOException {
        FileUtils.copyFileToDirectory(this.fileObject, destination);
    }

    public String getParentCaseNumber() throws IOException {
        String caseNumber = this.caseNumber;
        String[] caseNumberSplitsOnDash = caseNumber.split("-");
        String labPrefix = caseNumberSplitsOnDash[0];
        String caseYear = caseNumberSplitsOnDash[1];
        String caseId = caseNumberSplitsOnDash[2];
        String parentCaseNumber = labPrefix + "-" + caseYear + "-" + caseId;
        return parentCaseNumber;
    }
}
