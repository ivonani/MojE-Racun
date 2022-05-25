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
public class Avans {
    private int idDok;
    private String idAvansa;
    private String issueDateAvansa;

    public Avans(int idDok, String idAvansa, String issueDateAvansa) {
        this.idDok = idDok;
        this.idAvansa = idAvansa;
        this.issueDateAvansa = issueDateAvansa;
    }

    public Avans() {
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }

    public String getIdAvansa() {
        return idAvansa;
    }

    public void setIdAvansa(String idAvansa) {
        this.idAvansa = idAvansa;
    }

    public String getIssueDateAvansa() {
        return issueDateAvansa;
    }

    public void setIssueDateAvansa(String issueDateAvansa) {
        this.issueDateAvansa = issueDateAvansa;
    }

}
