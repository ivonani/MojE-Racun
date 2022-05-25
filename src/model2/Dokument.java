/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.util.Date;

/**
 *
 * @author ivona
 */
public class Dokument {
    private int idDOK;
    private String idRacuna;
    private String customizationID;
    private String profileID;
    private String issueDate;
    private String dueDate;
    private String invoiceTypeCode;
    private String noteVreme;
    private String noteOperater;
    private String noteOdgovorni;
    private String noteNapomena;
    private String documentCurrencyCode;
    private String invoiceStartDate;
    private String invoiceEndDate;
    private String descriptionCode;

    public Dokument() {
    }

    public Dokument(int idDOK, String idRacuna, String customizationID, String profileID, String issueDate, String dueDate, String invoiceTypeCode, String noteVreme, String noteOperater, String noteOdgovorni, String noteNapomena, String documentCurrencyCode, String invoiceStartDate, String invoiceEndDate, String descriptionCode) {
        this.idDOK = idDOK;
        this.idRacuna = idRacuna;
        this.customizationID = customizationID;
        this.profileID = profileID;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.invoiceTypeCode = invoiceTypeCode;
        this.noteVreme = noteVreme;
        this.noteOperater = noteOperater;
        this.noteOdgovorni = noteOdgovorni;
        this.noteNapomena = noteNapomena;
        this.documentCurrencyCode = documentCurrencyCode;
        this.invoiceStartDate = invoiceStartDate;
        this.invoiceEndDate = invoiceEndDate;
        this.descriptionCode = descriptionCode;
    }  

    public int getIdDOK() {
        return idDOK;
    }

    public void setIdDOK(int idDOK) {
        this.idDOK = idDOK;
    }

    public String getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(String idRacuna) {
        this.idRacuna = idRacuna;
    }

    public String getCustomizationID() {
        return customizationID;
    }

    public void setCustomizationID(String customizationID) {
        this.customizationID = customizationID;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    
    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public String getNoteVreme() {
        return noteVreme;
    }

    public void setNoteVreme(String noteVreme) {
        this.noteVreme = noteVreme;
    }

    public String getNoteOperater() {
        return noteOperater;
    }

    public void setNoteOperater(String noteOperater) {
        this.noteOperater = noteOperater;
    }

    public String getNoteNapomena() {
        return noteNapomena;
    }

    public void setNoteNapomena(String noteNapomena) {
        this.noteNapomena = noteNapomena;
    }

    public String getDocumentCurrencyCode() {
        return documentCurrencyCode;
    }

    public void setDocumentCurrencyCode(String documentCurrencyCode) {
        this.documentCurrencyCode = documentCurrencyCode;
    }

    public String getNoteOdgovorni() {
        return noteOdgovorni;
    }

    public void setNoteOdgovorni(String noteOdgovorni) {
        this.noteOdgovorni = noteOdgovorni;
    }


    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getInvoiceStartDate() {
        return invoiceStartDate;
    }

    public void setInvoiceStartDate(String invoiceStartDate) {
        this.invoiceStartDate = invoiceStartDate;
    }

    public String getInvoiceEndDate() {
        return invoiceEndDate;
    }

    public void setInvoiceEndDate(String invoiceEndDate) {
        this.invoiceEndDate = invoiceEndDate;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public void setDescriptionCode(String descriptionCode) {
        this.descriptionCode = descriptionCode;
    }
    
    
}
