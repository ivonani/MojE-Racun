/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

/**
 *
 * @author ivona
 */
public class DokumentPDF {
    private int idDok;
    private String folderPDF;
    private String nazivPDF;
    private String documentType;
    private String fileName;
    private String encodingCode;
    private String mimeCode;

    public DokumentPDF() {
    }

    public DokumentPDF(int idDok, String folderPDF, String nazivPDF, String documentType, String fileName, String encodingCode, String mimeCode) {
        this.idDok = idDok;
        this.folderPDF = folderPDF;
        this.nazivPDF = nazivPDF;
        this.documentType = documentType;
        this.fileName = fileName;
        this.encodingCode = encodingCode;
        this.mimeCode = mimeCode;
    }

    public String getMimeCode() {
        return mimeCode;
    }

    public void setMimeCode(String mimeCode) {
        this.mimeCode = mimeCode;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }

    public String getFolderPDF() {
        return folderPDF;
    }

    public void setFolderPDF(String folderPDF) {
        this.folderPDF = folderPDF;
    }

    public String getNazivPDF() {
        return nazivPDF;
    }

    public void setNazivPDF(String nazivPDF) {
        this.nazivPDF = nazivPDF;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEncodingCode() {
        return encodingCode;
    }

    public void setEncodingCode(String encodingCode) {
        this.encodingCode = encodingCode;
    }
    
    
}
