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
public class Narudzbina {
    private int idDok;
    private String idNarudzbine;

    public Narudzbina() {
    }

    public Narudzbina(int idDok, String idNarudzbine) {
        this.idDok = idDok;
        this.idNarudzbine = idNarudzbine;
    }

    public String getIdNarudzbine() {
        return idNarudzbine;
    }

    public void setIdNarudzbine(String idNarudzbine) {
        this.idNarudzbine = idNarudzbine;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }
    
    
}
