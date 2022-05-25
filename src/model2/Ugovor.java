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
public class Ugovor {
    private int idDok;
    private String idUgovora;

    public Ugovor() {
    }

    public Ugovor(int idDok, String idUgovora) {
        this.idDok = idDok;
        this.idUgovora = idUgovora;
    }

    public String getIdUgovora() {
        return idUgovora;
    }

    public void setIdUgovora(String idUgovora) {
        this.idUgovora = idUgovora;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }
    
    
}
