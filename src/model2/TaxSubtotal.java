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
public class TaxSubtotal {
    private int idDok;
    private BigDecimal taxableAmount;
    private BigDecimal taxAmount;
    private String taxCategoryID;
    private BigDecimal taxCategoryPercent;
    private String taxExemptionReason;
    private String taxExemptionReasonCode;
    private String taxSchemeID;

    public TaxSubtotal() {
    }

    public TaxSubtotal(int idDok, BigDecimal taxableAmount, BigDecimal taxAmount, String taxCategoryID, BigDecimal taxCategoryPercent, String taxExemptionReason, String taxExemptionReasonCode, String taxSchemeID) {
        this.idDok = idDok;
        this.taxableAmount = taxableAmount;
        this.taxAmount = taxAmount;
        this.taxCategoryID = taxCategoryID;
        this.taxCategoryPercent = taxCategoryPercent;
        this.taxExemptionReason = taxExemptionReason;
        this.taxExemptionReasonCode = taxExemptionReasonCode;
        this.taxSchemeID = taxSchemeID;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }

    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(BigDecimal taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
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

    public String getTaxExemptionReasonCode() {
        return taxExemptionReasonCode;
    }

    public void setTaxExemptionReasonCode(String taxExemptionReasonCode) {
        this.taxExemptionReasonCode = taxExemptionReasonCode;
    }

    
}
