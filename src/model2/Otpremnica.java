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
public class Otpremnica {
    private int idDok;
    private String idOtprenice;

    public Otpremnica() {
    }

    public Otpremnica(int idDok, String idOtprenice) {
        this.idDok = idDok;
        this.idOtprenice = idOtprenice;
    }

    public String getIdOtprenice() {
        return idOtprenice;
    }

    public void setIdOtprenice(String idOtprenice) {
        this.idOtprenice = idOtprenice;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }
    
    
}
