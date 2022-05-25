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
public class PaymentMeans {
    private int idDok;
    private String paymentMeansCode;
    private String instructionNote;
    private String PaymentID;
    private String payeeFinancialAccount;
    private String paymentTermsNote;

    public PaymentMeans() {
    }

    public PaymentMeans(int idDok, String paymentMeansCode, String instructionNote, String PaymentID, String payeeFinancialAccount, String paymentTermsNote) {
        this.idDok = idDok;
        this.paymentMeansCode = paymentMeansCode;
        this.instructionNote = instructionNote;
        this.PaymentID = PaymentID;
        this.payeeFinancialAccount = payeeFinancialAccount;
        this.paymentTermsNote = paymentTermsNote;
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
        return PaymentID;
    }

    public void setPaymentID(String PaymentID) {
        this.PaymentID = PaymentID;
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
    
    
}
