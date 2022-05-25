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
public class RequestObject {
    
    private String Username;
    private String Password;
    private String CompanyId;
    private String SoftwareId;
    private String File;

    public RequestObject() {
    }

    public RequestObject(String Username, String Password, String CompanyId, String SoftwareId, String File) {
        this.Username = Username;
        this.Password = Password;
        this.CompanyId = CompanyId;
        this.SoftwareId = SoftwareId;
        this.File = File;
    }

    public String getFile() {
        return File;
    }

    public void setFile(String File) {
        this.File = File;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String CompanyId) {
        this.CompanyId = CompanyId;
    }

    public String getSoftwareId() {
        return SoftwareId;
    }

    public void setSoftwareId(String SoftwareId) {
        this.SoftwareId = SoftwareId;
    }
    
    
}
