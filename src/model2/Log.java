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
public class Log {
    private int IDDOK;
    private int zaSlanje;
    private int otpremljen;
    private Date datumKreiranja;
    private Date datumSlanja;
    private Date datumOtpreme;
    private int IDError;
    private String response;
    private Integer electronicID;
    private String statusID;

    public Log() {
    }

    public Log(int IDDOK, int zaSlanje, int otpremljen, Date datumKreiranja, Date datumSlanja, Date datumOtpreme, int IDError, String response, Integer electronicID, String statusID) {
        this.IDDOK = IDDOK;
        this.zaSlanje = zaSlanje;
        this.otpremljen = otpremljen;
        this.datumKreiranja = datumKreiranja;
        this.datumSlanja = datumSlanja;
        this.datumOtpreme = datumOtpreme;
        this.IDError = IDError;
        this.response = response;
        this.electronicID = electronicID;
        this.statusID = statusID;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getIDDOK() {
        return IDDOK;
    }

    public void setIDDOK(int IDDOK) {
        this.IDDOK = IDDOK;
    }

    public int getZaSlanje() {
        return zaSlanje;
    }

    public void setZaSlanje(int zaSlanje) {
        this.zaSlanje = zaSlanje;
    }

    public int getOtpremljen() {
        return otpremljen;
    }

    public void setOtpremljen(int otpremljen) {
        this.otpremljen = otpremljen;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Date getDatumSlanja() {
        return datumSlanja;
    }

    public void setDatumSlanja(Date datumSlanja) {
        this.datumSlanja = datumSlanja;
    }

    public Date getDatumOtpreme() {
        return datumOtpreme;
    }

    public void setDatumOtpreme(Date datumOtpreme) {
        this.datumOtpreme = datumOtpreme;
    }

    public int getIDError() {
        return IDError;
    }

    public void setIDError(int IDError) {
        this.IDError = IDError;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public Integer getElectronicID() {
        return electronicID;
    }

    public void setElectronicID(Integer electronicID) {
        this.electronicID = electronicID;
    }
    
}
