/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.math.BigDecimal;

/**
 *
 * @author ivona
 */
public class Stavke {
    private int idDok;
    private int invoiceLine;
    private String unitCode;
    private BigDecimal quantity;
    private BigDecimal lineExtensionAmount;
    private String invoiceStartDate;
    private String invoiceEndDate;
    private String itemName;
    private String buyersItemIdentification;
    private String sellersItemIdentification;
    private String standardItemIdentification;
    private String taxCategoryID;
    private BigDecimal taxCategoryPercent;
    private String taxExemptionReason;
    private String taxSchemeID;
    private BigDecimal priceAmount;
    private String baseQuantity;

    public Stavke() {
    }

    public Stavke(int idDok, int invoiceLine, String unitCode, BigDecimal quantity, BigDecimal lineExtensionAmount, String invoiceStartDate, String invoiceEndDate, String itemName, String buyersItemIdentification, String sellersItemIdentification, String standardItemIdentification, String taxCategoryID, BigDecimal taxCategoryPercent, String taxExemptionReason, String taxSchemeID, BigDecimal priceAmount, String baseQuantity) {
        this.idDok = idDok;
        this.invoiceLine = invoiceLine;
        this.unitCode = unitCode;
        this.quantity = quantity;
        this.lineExtensionAmount = lineExtensionAmount;
        this.invoiceStartDate = invoiceStartDate;
        this.invoiceEndDate = invoiceEndDate;
        this.itemName = itemName;
        this.buyersItemIdentification = buyersItemIdentification;
        this.sellersItemIdentification = sellersItemIdentification;
        this.standardItemIdentification = standardItemIdentification;
        this.taxCategoryID = taxCategoryID;
        this.taxCategoryPercent = taxCategoryPercent;
        this.taxExemptionReason = taxExemptionReason;
        this.taxSchemeID = taxSchemeID;
        this.priceAmount = priceAmount;
        this.baseQuantity = baseQuantity;
    }

    

    

    public String getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(String baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }
    
    

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineExtensionAmount() {
        return lineExtensionAmount;
    }

    public void setLineExtensionAmount(BigDecimal lineExtensionAmount) {
        this.lineExtensionAmount = lineExtensionAmount;
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

    

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBuyersItemIdentification() {
        return buyersItemIdentification;
    }

    public void setBuyersItemIdentification(String buyersItemIdentification) {
        this.buyersItemIdentification = buyersItemIdentification;
    }

    public String getSellersItemIdentification() {
        return sellersItemIdentification;
    }

    public void setSellersItemIdentification(String sellersItemIdentification) {
        this.sellersItemIdentification = sellersItemIdentification;
    }

    public String getTaxCategoryID() {
        return taxCategoryID;
    }

    public void setTaxCategoryID(String taxCategoryID) {
        this.taxCategoryID = taxCategoryID;
    }

    public BigDecimal getTaxCategoryPercent() {
        return taxCategoryPercent;
    }

    public void setTaxCategoryPercent(BigDecimal taxCategoryPercent) {
        this.taxCategoryPercent = taxCategoryPercent;
    }

    public String getTaxExemptionReason() {
        return taxExemptionReason;
    }

    public void setTaxExemptionReason(String taxExemptionReason) {
        this.taxExemptionReason = taxExemptionReason;
    }

    public String getTaxSchemeID() {
        return taxSchemeID;
    }

    public void setTaxSchemeID(String taxSchemeID) {
        this.taxSchemeID = taxSchemeID;
    }

    public BigDecimal getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getStandardItemIdentification() {
        return standardItemIdentification;
    }

    public void setStandardItemIdentification(String standardItemIdentification) {
        this.standardItemIdentification = standardItemIdentification;
    }

    public int getInvoiceLine() {
        return invoiceLine;
    }

    public void setInvoiceLine(int invoiceLine) {
        this.invoiceLine = invoiceLine;
    }
    
    
}
