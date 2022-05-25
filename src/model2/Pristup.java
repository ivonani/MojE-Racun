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
public class Pristup {
    private int userName;
    private String password;
    private String companyID;
    private String softwareID;
    private String file;
    private String url;

    public Pristup(int userName, String password, String companyID, String softwareID, String file, String url) {
        this.userName = userName;
        this.password = password;
        this.companyID = companyID;
        this.softwareID = softwareID;
        this.file = file;
        this.url = url;
    }

    

    public Pristup() {
    }

    public int getUserName() {
        return userName;
    }

    public void setUserName(int userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getSoftwareID() {
        return softwareID;
    }

    public void setSoftwareID(String softwareID) {
        this.softwareID = softwareID;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
