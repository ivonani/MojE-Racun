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
public class Isporuka {
    private int idDok;
    private String actualDeliveryDate;
    private String streetNameLocation;
    private String cityNameLocation;
    private String postalZoneLocation;
    private String countryIDLocation;
    private String endpointID;
    private String partyIdentification;
    private String partyName;
    private String streetName;
    private String cityName;
    private String postalZone;
    private String addressLine;
    private String countryID;
    private String pib;
    private String taxSchemeID;
    private String registrationName;
    private String mbr;

    public Isporuka() {
    }

    public Isporuka(int idDok, String actualDeliveryDate, String streetNameLocation, String cityNameLocation, String postalZoneLocation, String countryIDLocation, String endpointID, String partyIdentification, String partyName, String streetName, String cityName, String postalZone, String addressLine, String countryID, String pib, String taxSchemeID, String registrationName, String mbr) {
        this.idDok = idDok;
        this.actualDeliveryDate = actualDeliveryDate;
        this.streetNameLocation = streetNameLocation;
        this.cityNameLocation = cityNameLocation;
        this.postalZoneLocation = postalZoneLocation;
        this.countryIDLocation = countryIDLocation;
        this.endpointID = endpointID;
        this.partyIdentification = partyIdentification;
        this.partyName = partyName;
        this.streetName = streetName;
        this.cityName = cityName;
        this.postalZone = postalZone;
        this.addressLine = addressLine;
        this.countryID = countryID;
        this.pib = pib;
        this.taxSchemeID = taxSchemeID;
        this.registrationName = registrationName;
        this.mbr = mbr;
    }

    public String getMbr() {
        return mbr;
    }

    public void setMbr(String mbr) {
        this.mbr = mbr;
    }

    public int getIdDok() {
        return idDok;
    }

    public void setIdDok(int idDok) {
        this.idDok = idDok;
    }

    public String getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(String actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getStreetNameLocation() {
        return streetNameLocation;
    }

    public void setStreetNameLocation(String streetNameLocation) {
        this.streetNameLocation = streetNameLocation;
    }

    public String getCityNameLocation() {
        return cityNameLocation;
    }

    public void setCityNameLocation(String cityNameLocation) {
        this.cityNameLocation = cityNameLocation;
    }

    public String getPostalZoneLocation() {
        return postalZoneLocation;
    }

    public void setPostalZoneLocation(String postalZoneLocation) {
        this.postalZoneLocation = postalZoneLocation;
    }

    public String getCountryIDLocation() {
        return countryIDLocation;
    }

    public void setCountryIDLocation(String countryIDLocation) {
        this.countryIDLocation = countryIDLocation;
    }

    public String getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(String endpointID) {
        this.endpointID = endpointID;
    }

    public String getPartyIdentification() {
        return partyIdentification;
    }

    public void setPartyIdentification(String partyIdentification) {
        this.partyIdentification = partyIdentification;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalZone() {
        return postalZone;
    }

    public void setPostalZone(String postalZone) {
        this.postalZone = postalZone;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getTaxSchemeID() {
        return taxSchemeID;
    }

    public void setTaxSchemeID(String taxSchemeID) {
        this.taxSchemeID = taxSchemeID;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }
    
    
}
