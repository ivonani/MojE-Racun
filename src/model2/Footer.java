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
public class Footer {
    private int idDok;
    private String paymentMeansCode;
    private String instructionNote;
    private String paymentID;
    private String payeeFinancialAccount;
    private String paymentTermsNote;
    private String chargeIndicator;
    private String allowanceChargeReason;
    private BigDecimal multiplierFactorNumeric;
    private BigDecimal amount;
    private BigDecimal baseAmount;
    private BigDecimal taxAmount;
    private BigDecimal lineExtensionAmount;
    private BigDecimal taxExclusiveAmount;
    private BigDecimal taxInclusiveAmount;
    private BigDecimal allowanceTotalAmount;
    private BigDecimal chargeTotalAmount;
    private BigDecimal prepaidAmount;
    private BigDecimal payableAmount;

    public Footer() {
    }

    public Footer(int idDok, String paymentMeansCode, String instructionNote, String paymentID, String payeeFinancialAccount, String paymentTermsNote, String chargeIndicator, String allowanceChargeReason, BigDecimal multiplierFactorNumeric, BigDecimal amount, BigDecimal baseAmount, BigDecimal taxAmount, BigDecimal lineExtensionAmount, BigDecimal taxExclusiveAmount, BigDecimal taxInclusiveAmount, BigDecimal allowanceTotalAmount, BigDecimal chargeTotalAmount, BigDecimal prepaidAmount, BigDecimal payableAmount) {
        this.idDok = idDok;
        this.paymentMeansCode = paymentMeansCode;
        this.instructionNote = instructionNote;
        this.paymentID = paymentID;
        this.payeeFinancialAccount = payeeFinancialAccount;
        this.paymentTermsNote = paymentTermsNote;
        this.chargeIndicator = chargeIndicator;
        this.allowanceChargeReason = allowanceChargeReason;
        this.multiplierFactorNumeric = multiplierFactorNumeric;
        this.amount = amount;
        this.baseAmount = baseAmount;
        this.taxAmount = taxAmount;
        this.lineExtensionAmount = lineExtensionAmount;
        this.taxExclusiveAmount = taxExclusiveAmount;
        this.taxInclusiveAmount = taxInclusiveAmount;
        this.allowanceTotalAmount = allowanceTotalAmount;
        this.chargeTotalAmount = chargeTotalAmount;
        this.prepaidAmount = prepaidAmount;
        this.payableAmount = payableAmount;
    }



    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }

    public String getPaymentMeansCode() {
        return paymentMeansCode;
    }

    public void setPaymentMeansCode(String paymentMeansCode) {
        this.paymentMeansCode = paymentMeansCode;
    }

    public String getInstructionNote() {
        return instructionNote;
    }

    public void setInstructionNote(String instructionNote) {
        this.instructionNote = instructionNote;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPayeeFinancialAccount() {
        return payeeFinancialAccount;
    }

    public void setPayeeFinancialAccount(String payeeFinancialAccount) {
        this.payeeFinancialAccount = payeeFinancialAccount;
    }

    public String getPaymentTermsNote() {
        return paymentTermsNote;
    }

    public void setPaymentTermsNote(String paymentTermsNote) {
        this.paymentTermsNote = paymentTermsNote;
    }


    public String getAllowanceChargeReason() {
        return allowanceChargeReason;
    }

    public void setAllowanceChargeReason(String allowanceChargeReason) {
        this.allowanceChargeReason = allowanceChargeReason;
    }

    public BigDecimal getMultiplierFactorNumeric() {
        return multiplierFactorNumeric;
    }

    public void setMultiplierFactorNumeric(BigDecimal multiplierFactorNumeric) {
        this.multiplierFactorNumeric = multiplierFactorNumeric;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getLineExtensionAmount() {
        return lineExtensionAmount;
    }

    public void setLineExtensionAmount(BigDecimal lineExtensionAmount) {
        this.lineExtensionAmount = lineExtensionAmount;
    }

    public BigDecimal getTaxExclusiveAmount() {
        return taxExclusiveAmount;
    }

    public void setTaxExclusiveAmount(BigDecimal taxExclusiveAmount) {
        this.taxExclusiveAmount = taxExclusiveAmount;
    }

    public BigDecimal getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    public void setTaxInclusiveAmount(BigDecimal taxInclusiveAmount) {
        this.taxInclusiveAmount = taxInclusiveAmount;
    }

    public BigDecimal getAllowanceTotalAmount() {
        return allowanceTotalAmount;
    }

    public void setAllowanceTotalAmount(BigDecimal allowanceTotalAmount) {
        this.allowanceTotalAmount = allowanceTotalAmount;
    }

    public BigDecimal getChargeTotalAmount() {
        return chargeTotalAmount;
    }

    public void setChargeTotalAmount(BigDecimal chargeTotalAmount) {
        this.chargeTotalAmount = chargeTotalAmount;
    }

    public BigDecimal getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(BigDecimal prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getChargeIndicator() {
        return chargeIndicator;
    }

    public void setChargeIndicator(String chargeIndicator) {
        this.chargeIndicator = chargeIndicator;
    }


    
}
