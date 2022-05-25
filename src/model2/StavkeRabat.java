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
public class StavkeRabat {
    private int idDok;
    private int invoiceLineID;
    private int rabat;
    private String chargeIndicator;
    private String allowanceChargeReason;
    private BigDecimal multiplierFactorNumeric;
    private BigDecimal amount;
    private BigDecimal baseAmount;

    public StavkeRabat() {
    }

    public StavkeRabat(int idDok, int invoiceLineID, int rabat, String chargeIndicator, String allowanceChargeReason, BigDecimal multiplierFactorNumeric, BigDecimal amount, BigDecimal baseAmount) {
        this.idDok = idDok;
        this.invoiceLineID = invoiceLineID;
        this.rabat = rabat;
        this.chargeIndicator = chargeIndicator;
        this.allowanceChargeReason = allowanceChargeReason;
        this.multiplierFactorNumeric = multiplierFactorNumeric;
        this.amount = amount;
        this.baseAmount = baseAmount;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }

    public int getInvoiceLineID() {
        return invoiceLineID;
    }

    public void setInvoiceLineID(int invoiceLineID) {
        this.invoiceLineID = invoiceLineID;
    }

    public int getRabat() {
        return rabat;
    }

    public void setRabat(int rabat) {
        this.rabat = rabat;
    }

    public String getChargeIndicator() {
        return chargeIndicator;
    }

    public void setChargeIndicator(String chargeIndicator) {
        this.chargeIndicator = chargeIndicator;
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
    
    
}
