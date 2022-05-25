/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbb;

import com.MakeErrorFile;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model2.Avans;
import model2.Dobavljac;
import model2.Dokument;
import model2.DokumentPDF;
import model2.Footer;
import model2.Isporuka;
import model2.Kupac;
import model2.Log;
import model2.Narudzbina;
import model2.Otpremnica;
import model2.PaymentMeans;
import model2.Pristup;
import model2.Stavke;
import model2.StavkeRabat;
import model2.TaxSubtotal;
import model2.Ugovor;

/**
 *
 * @author ivona
 */
public class DBBroker {

    private Connection conn;
    private String nameDB;
    private String serverName;
    private int IDDok = 0;
    Log log;

    public void loadDriver() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to load Driver\n"+ ex.getLocalizedMessage());
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    public void openConnection() throws SQLException {
        String url, user, pass;
        try {
            /*url = "jdbc:sqlserver://" + serverName + ";database=" + nameDB + ";integratedSecurity=true;";
            conn = DriverManager.getConnection(url);*/
            url = "jdbc:sqlserver://" + serverName + ";database=" + nameDB + ";";
            user = "erUser";
            pass = "In$oft60181680";
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Successfully established connection");
        } catch (SQLException ex) {
            System.out.println("Unsuccessful connection");
            MakeErrorFile.makeTxtErrorFile("Unsuccessful connection\n"+ ex.getLocalizedMessage());
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
            throw ex;
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unsuccessful close connection\n"+ ex.getLocalizedMessage());
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    public void commit() {
        try {
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public LinkedList<Log> returnLog() {
        String sql = "select * from erLog";
        LinkedList<Log> logs = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Log l = new Log();

                int iDDOK = rs.getInt(1);
                l.setIDDOK(iDDOK);

                int zaSlanje = rs.getInt(2);
                l.setZaSlanje(zaSlanje);

                int otpremljen = rs.getInt(3);
                l.setOtpremljen(otpremljen);

                Date datumKreiranja;
                if (rs.getDate(4) == null) {
                    datumKreiranja = null;
                } else {
                    datumKreiranja = new Date(rs.getDate(4).getTime());
                }
                l.setDatumKreiranja(datumKreiranja);

                Date datumSlanja;
                if (rs.getDate(5) == null) {
                    datumSlanja = null;
                } else {
                    datumSlanja = new Date(rs.getDate(5).getTime());
                }
                l.setDatumSlanja(datumSlanja);

                Date datumOtpreme;
                if (rs.getDate(6) == null) {
                    datumOtpreme = null;
                } else {
                    datumOtpreme = new Date(rs.getDate(6).getTime());
                }
                l.setDatumOtpreme(datumOtpreme);

                int iDError = rs.getInt(7);
                l.setIDError(iDError);

                String response = rs.getString(8);
                l.setResponse(response);
                logs.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(getIDDok(), 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erLog", null, null);
            updateLog(log);
            return null;
        }

        return logs;
    }

    public LinkedList<Dokument> returnDokument(int id) {
        String sql = "select * from erDokument";
        LinkedList<Dokument> dokuments = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Dokument d = new Dokument();

                int iDDOK = rs.getInt(1);
                d.setIdDOK(iDDOK);

                if (iDDOK == id) {
                    String idRacuna = rs.getString(2);
                    d.setIdRacuna(idRacuna);

                    String customizationID = rs.getString(3);
                    d.setCustomizationID(customizationID);

                    String profileID = rs.getString(4);
                    d.setProfileID(profileID);

                    String issueDate = rs.getString(5);
                    d.setIssueDate(issueDate);

                    String dueDate = rs.getString(6);
                    d.setDueDate(dueDate);

                    String invoiceTypeCode = rs.getString(7);
                    d.setInvoiceTypeCode(invoiceTypeCode);

                    String noteVreme = rs.getString(8);
                    d.setNoteVreme(noteVreme);

                    String noteOperater = rs.getString(9);
                    d.setNoteOperater(noteOperater);

                    String noteOdgovorni = rs.getString(10);
                    d.setNoteOdgovorni(noteOdgovorni);

                    String noteNapomena = rs.getString(11);
                    d.setNoteNapomena(noteNapomena);

                    String documentCurrencyCode = rs.getString(12);
                    d.setDocumentCurrencyCode(documentCurrencyCode);

                    String invoiceStartDate = rs.getString(13);
                    d.setInvoiceStartDate(invoiceStartDate);

                    String invoiceEndDate = rs.getString(14);
                    d.setInvoiceEndDate(invoiceEndDate);
                    
                    String descriptionCode = rs.getString(15);
                    d.setDescriptionCode(descriptionCode);

                    dokuments.add(d);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erDokument", null, null);
            updateLog(log);
            return null;
        }
        return dokuments;
    }

    public LinkedList<Narudzbina> returnNarudzbine(int id) {
        String sql = "select * from erNarudzbine";
        LinkedList<Narudzbina> nars = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Narudzbina n = new Narudzbina();

                int iDDOK = rs.getInt(1);
                n.setIdDok(iDDOK);
                if (iDDOK == id) {
                    String idNarudzbine = rs.getString(2);
                    n.setIdNarudzbine(idNarudzbine);

                    nars.add(n);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erNarudzbine", null, null);
            updateLog(log);
            return null;
        }

        return nars;
    }

    public LinkedList<Otpremnica> returnOtpremnice(int id) {
        String sql = "select * from erOtpremnice";
        LinkedList<Otpremnica> otps = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Otpremnica o = new Otpremnica();

                int iDDOK = rs.getInt(1);
                o.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String idOtpremnice = rs.getString(2);
                    o.setIdOtprenice(idOtpremnice);

                    otps.add(o);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erOtpremnice", null, null);
            updateLog(log);
            return null;
        }

        return otps;
    }

    public LinkedList<Avans> returnAvansi(int id) {
        String sql = "select * from erAvansi";
        LinkedList<Avans> avas = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Avans a = new Avans();

                int iDDOK = rs.getInt(1);
                a.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String idAvansa = rs.getString(2);
                    a.setIdAvansa(idAvansa);
                    
                    String issueDate = rs.getString(3);
                    a.setIssueDateAvansa(issueDate);

                    avas.add(a);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erAvansi", null, null);
            updateLog(log);
            return null;
        }

        return avas;
    }

    public LinkedList<DokumentPDF> returnDokumentPDF(int id) {
        String sql = "select * from erDokumentPDF";
        LinkedList<DokumentPDF> dokuments = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                DokumentPDF d = new DokumentPDF();

                int iDDOK = rs.getInt(1);
                d.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String folderPDF = rs.getString(2);
                    d.setFolderPDF(folderPDF);

                    String nazivPDF = rs.getString(3);
                    d.setNazivPDF(nazivPDF);

                    String documentType = rs.getString(4);
                    d.setDocumentType(documentType);

                    String fileName = rs.getString(5);
                    d.setFileName(fileName);

                    String encodingCode = rs.getString(6);
                    d.setEncodingCode(encodingCode);

                    String mimeCode = rs.getString(7);
                    d.setMimeCode(mimeCode);

                    dokuments.add(d);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erDokumentPDF", null, null);
            updateLog(log);
            return null;
        }

        return dokuments;
    }

    public LinkedList<Ugovor> returnUgovori(int id) {
        String sql = "select * from erUgovori";
        LinkedList<Ugovor> ugrs = new LinkedList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Ugovor u = new Ugovor();

                int iDDOK = rs.getInt(1);
                u.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String idUgovora = rs.getString(2);
                    u.setIdUgovora(idUgovora);

                    ugrs.add(u);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erUgovori", null, null);
            updateLog(log);
            return null;
        }

        return ugrs;
    }

    public LinkedList<Dobavljac> returnDobavljaci(int id) {
        LinkedList<Dobavljac> dobs = new LinkedList<>();
        String sql = "select * from erDobavljac";
        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Dobavljac d = new Dobavljac();

                int iDDOK = rs.getInt(1);
                d.setIdDOk(iDDOK);

                if (iDDOK == id) {
                    String endpoint = rs.getString(2);
                    d.setEndpointID(endpoint);

                    String partyIdentification = rs.getString(3);
                    d.setPartyIdentification(partyIdentification);

                    String partyName = rs.getString(4);
                    d.setPartyName(partyName);

                    String streetName = rs.getString(5);
                    d.setStreetName(streetName);

                    String cityName = rs.getString(6);
                    d.setCityName(cityName);

                    String postalZone = rs.getString(7);
                    d.setPostalZone(postalZone);

                    String addressLine = rs.getString(8);
                    d.setAddressLine(addressLine);

                    String countryID = rs.getString(9);
                    d.setCountryID(countryID);

                    String pib = rs.getString(10);
                    d.setPib(pib);

                    String taxSchemeID = rs.getString(11);
                    d.setTaxSchemeID(taxSchemeID);

                    String registrationName = rs.getString(12);
                    d.setRegistrationName(registrationName);

                    String mbr = rs.getString(13);
                    d.setMbr(mbr);

                    d.setCompanyLegalForm(null);

                    String contactName = rs.getString(14);
                    d.setContactName(contactName);

                    String contactPhone = rs.getString(15);
                    d.setContactPhone(contactPhone);

                    String contactMail = rs.getString(16);
                    d.setContactMail(contactMail);

                    dobs.add(d);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erDobavljac", null, null);
            updateLog(log);
            return null;
        }

        return dobs;
    }

    public LinkedList<Kupac> returnKupci(int id) {
        LinkedList<Kupac> dobs = new LinkedList<>();
        String sql = "select * from erKupac";
        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Kupac d = new Kupac();

                int iDDOK = rs.getInt(1);
                d.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String endpoint = rs.getString(2);
                    d.setEndpointID(endpoint);

                    String partyIdentification = rs.getString(3);
                    d.setPartyIdentification(partyIdentification);

                    String partyName = rs.getString(4);
                    d.setPartyName(partyName);

                    String streetName = rs.getString(5);
                    d.setStreetName(streetName);

                    String cityName = rs.getString(6);
                    d.setCityName(cityName);

                    String postalZone = rs.getString(7);
                    d.setPostalZone(postalZone);

                    String addressLine = rs.getString(8);
                    d.setAddressLine(addressLine);

                    String countryID = rs.getString(9);
                    d.setCountryID(countryID);

                    String pib = rs.getString(10);
                    d.setPib(pib);

                    String taxSchemeID = rs.getString(11);
                    d.setTaxSchemeID(taxSchemeID);

                    String registrationName = rs.getString(12);
                    d.setRegistrationName(registrationName);

                    String mbr = rs.getString(13);
                    d.setMbr(mbr);

                    String contactName = rs.getString(14);
                    d.setContactName(contactName);

                    String contactPhone = rs.getString(15);
                    d.setContactPhone(contactPhone);

                    String contactMail = rs.getString(16);
                    d.setContactMail(contactMail);

                    dobs.add(d);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erKupac", null, null);
            updateLog(log);
            return null;
        }

        return dobs;
    }

    public LinkedList<Isporuka> returnIsporuka(int id) {
        LinkedList<Isporuka> isprs = new LinkedList<>();
        String sql = "select * from erIsporuka";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Isporuka i = new Isporuka();

                int iDDOK = rs.getInt(1);
                i.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String actualDeliveryDate = rs.getString(2);
                    i.setActualDeliveryDate(actualDeliveryDate);

                    String streetNameLocation = rs.getString(3);
                    i.setStreetNameLocation(streetNameLocation);

                    String cityNameLocation = rs.getString(4);
                    i.setCityNameLocation(cityNameLocation);

                    /*String postalZoneLocation = rs.getString(5);
                    i.setPostalZoneLocation(postalZoneLocation);*/
                    String countryIDLocation = rs.getString(5);
                    i.setCountryIDLocation(countryIDLocation);

                    String endpointID = rs.getString(6);
                    i.setEndpointID(endpointID);

                    String partyIdentification = rs.getString(7);
                    i.setPartyIdentification(partyIdentification);

                    String partyName = rs.getString(8);
                    i.setPartyName(partyName);

                    String streetName = rs.getString(9);
                    i.setStreetName(streetName);

                    String cityName = rs.getString(10);
                    i.setCityName(cityName);

                    String postalZone = rs.getString(11);
                    i.setPostalZone(postalZone);

                    String addressLine = rs.getString(12);
                    i.setAddressLine(addressLine);

                    String countryID = rs.getString(13);
                    i.setCountryID(countryID);

                    String pib = rs.getString(14);
                    i.setPib(pib);

                    String taxSchemeID = rs.getString(15);
                    i.setTaxSchemeID(taxSchemeID);

                    String registrationName = rs.getString(16);
                    i.setRegistrationName(registrationName);

                    String mbr = rs.getString(17);
                    i.setMbr(mbr);

                    isprs.add(i);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erIsporuka", null, null);
            updateLog(log);
            return null;
        }

        return isprs;
    }

    public LinkedList<Footer> returnFooter(int id) {
        LinkedList<Footer> foots = new LinkedList<>();
        String sql = "select * from erFooter";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Footer f = new Footer();

                int iDDOK = rs.getInt(1);
                f.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String ci = rs.getString(2);
                    if (ci == null) {
                        f.setChargeIndicator(null);
                    } else {
                        if (ci.equals("T")) {
                            f.setChargeIndicator("true");
                        } else {
                            f.setChargeIndicator("false");
                        }
                    }

                    String allowanceChargeReason = rs.getString(3);
                    f.setAllowanceChargeReason(allowanceChargeReason);

                    BigDecimal multiplierFactorNumeric = rs.getBigDecimal(4);
                    f.setMultiplierFactorNumeric(multiplierFactorNumeric);

                    BigDecimal amount = rs.getBigDecimal(5);
                    f.setAmount(amount);

                    BigDecimal baseAmount = rs.getBigDecimal(6);
                    f.setBaseAmount(baseAmount);

                    BigDecimal taxAmount = rs.getBigDecimal(7);
                    f.setTaxAmount(taxAmount);

                    BigDecimal lineExtensionAmount = rs.getBigDecimal(8);
                    f.setLineExtensionAmount(lineExtensionAmount);

                    BigDecimal taxExclusiveAmount = rs.getBigDecimal(9);
                    f.setTaxExclusiveAmount(taxExclusiveAmount);

                    BigDecimal taxInclusiveAmount = rs.getBigDecimal(10);
                    f.setTaxInclusiveAmount(taxInclusiveAmount);

                    BigDecimal allowanceTotalAmount = rs.getBigDecimal(11);
                    f.setAllowanceTotalAmount(allowanceTotalAmount);

                    BigDecimal chargeTotalAmount = rs.getBigDecimal(12);
                    f.setChargeTotalAmount(chargeTotalAmount);

                    BigDecimal prepaidAmount = rs.getBigDecimal(13);
                    f.setPrepaidAmount(prepaidAmount);

                    BigDecimal payableAmount = rs.getBigDecimal(14);
                    f.setPayableAmount(payableAmount);

                    foots.add(f);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erFooter", null, null);
            updateLog(log);
            return null;
        }

        return foots;
    }

    public LinkedList<PaymentMeans> returnPaymentMeans(int id) {
        LinkedList<PaymentMeans> pys = new LinkedList<>();
        String sql = "select * from erPaymentMeans";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                PaymentMeans pm = new PaymentMeans();

                int iDDOK = rs.getInt(1);
                pm.setIdDok(iDDOK);

                if (iDDOK == id) {
                    String paymentMeansCode = rs.getString(2);
                    pm.setPaymentMeansCode(paymentMeansCode);

                    String instructionNote = rs.getString(3);
                    pm.setInstructionNote(instructionNote);

                    String paymentID = rs.getString(4);
                    pm.setPaymentID(paymentID);

                    String payeeFinancialAccount = rs.getString(5);
                    pm.setPayeeFinancialAccount(payeeFinancialAccount);

                    String paymentTermsNote = rs.getString(6);
                    pm.setPaymentTermsNote(paymentTermsNote);

                    pys.add(pm);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erPaymentMeans", null, null);
            updateLog(log);
            return null;
        }

        return pys;
    }

    public LinkedList<TaxSubtotal> returnTaxSubtotal(int id) {
        LinkedList<TaxSubtotal> tss = new LinkedList<>();
        String sql = "select * from erTaxSubtotal";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                TaxSubtotal t = new TaxSubtotal();

                int iDDOK = rs.getInt(1);
                t.setIdDok(iDDOK);

                if (iDDOK == id) {
                    BigDecimal taxableAmount = rs.getBigDecimal(2);
                    t.setTaxableAmount(taxableAmount);

                    BigDecimal taxAmount = rs.getBigDecimal(3);
                    t.setTaxAmount(taxAmount);

                    String taxCategoryID = rs.getString(4);
                    t.setTaxCategoryID(taxCategoryID);

                    BigDecimal taxCategoryPercent = rs.getBigDecimal(5);
                    t.setTaxCategoryPercent(taxCategoryPercent);

                    String taxExemptionReason = rs.getString(6);
                    t.setTaxExemptionReason(taxExemptionReason);

                    String taxSchemeID = rs.getString(7);
                    t.setTaxSchemeID(taxSchemeID);
                    
                    String taxExemptionReasonCode = rs.getString(8);
                    t.setTaxExemptionReasonCode(taxExemptionReasonCode);

                    tss.add(t);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class
                    .getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erTaxSubtotal", null, null);
            updateLog(log);
            return null;
        }

        return tss;
    }

    public LinkedList<Stavke> returnStavke(int id) {
        LinkedList<Stavke> stvs = new LinkedList<>();
        String sql = "select * from erStavke";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Stavke st = new Stavke();

                int iDDOK = rs.getInt(1);
                st.setIdDok(iDDOK);

                if (iDDOK == id) {
                    int invoiceLine = rs.getInt(2);
                    st.setInvoiceLine(invoiceLine);

                    String unitCode = rs.getString(3);
                    st.setUnitCode(unitCode);

                    BigDecimal quantity = rs.getBigDecimal(4);
                    st.setQuantity(quantity);

                    BigDecimal lineExtensionAmount = rs.getBigDecimal(5);
                    st.setLineExtensionAmount(lineExtensionAmount);

                    String invoiceStartDate = rs.getString(6);
                    st.setInvoiceStartDate(invoiceStartDate);

                    String inoiceEndDate = rs.getString(7);
                    st.setInvoiceEndDate(inoiceEndDate);

                    String itaemName = rs.getString(8);
                    st.setItemName(itaemName);

                    String buyersItemIdentification = rs.getString(9);
                    st.setBuyersItemIdentification(buyersItemIdentification);

                    String sellersItemIdentification = rs.getString(10);
                    st.setSellersItemIdentification(sellersItemIdentification);

                    String standardItemIdentification = rs.getString(11);
                    st.setStandardItemIdentification(standardItemIdentification);

                    String taxCategoryID = rs.getString(12);
                    st.setTaxCategoryID(taxCategoryID);

                    BigDecimal tacCategoryPercent = rs.getBigDecimal(13);
                    st.setTaxCategoryPercent(tacCategoryPercent);

                    String taxExemptionReason = rs.getString(14);
                    st.setTaxExemptionReason(taxExemptionReason);

                    String taxSchemeID = rs.getString(15);
                    st.setTaxSchemeID(taxSchemeID);

                    BigDecimal priceAmount = rs.getBigDecimal(16);
                    st.setPriceAmount(priceAmount);

                    String baseQuantity = rs.getString(17);
                    st.setBaseQuantity(baseQuantity);

                    stvs.add(st);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class
                    .getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erStavke", null, null);
            updateLog(log);
            return null;
        }

        return stvs;
    }

    public LinkedList<StavkeRabat> returnStavkeRabat(int id, int idStavke) {
        LinkedList<StavkeRabat> stvs = new LinkedList<>();
        String sql = "select * from erStavkeRabat";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                StavkeRabat st = new StavkeRabat();

                int iDDOK = rs.getInt(1);
                st.setIdDok(iDDOK);

                if (iDDOK == id) {
                    int invoiceLineID = rs.getInt(2);
                    st.setInvoiceLineID(invoiceLineID);

                    if (invoiceLineID == idStavke) {
                        int rabat = rs.getInt(3);
                        st.setRabat(rabat);

                        String ci = rs.getString(4);
                        if (ci == null) {
                            st.setChargeIndicator(null);
                        } else {
                            if (ci.equals("T")) {
                                st.setChargeIndicator("true");
                            } else {
                                st.setChargeIndicator("false");
                            }
                        }

                        String allowanceChargeReason = rs.getString(5);
                        st.setAllowanceChargeReason(allowanceChargeReason);

                        BigDecimal multiplierFactorNumeric = rs.getBigDecimal(6);
                        st.setMultiplierFactorNumeric(multiplierFactorNumeric);

                        BigDecimal amount = rs.getBigDecimal(7);
                        st.setAmount(amount);

                        BigDecimal baseAmount = rs.getBigDecimal(8);
                        st.setBaseAmount(baseAmount);

                        stvs.add(st);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class
                    .getName()).log(Level.SEVERE, null, ex);
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erStavkeRabat", null, null);
            updateLog(log);
            return null;
        }

        return stvs;
    }

    public boolean updateLog(Log log) {
        boolean flag = false;
        try {
            String sql = "UPDATE erLog SET ZaSlanje = ?, Otpremljen = ?, DatumSlanja = ?, DatumOtpreme = ?, IDError = ?, Response = ?, ElectronicID = ?, StatusID = ? WHERE IDDOK = '" + log.getIDDOK() + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, log.getZaSlanje());
            preparedStatement.setInt(2, log.getOtpremljen());
            if (log.getDatumSlanja() != null) {
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(log.getDatumSlanja().getTime()));
            } else {
                preparedStatement.setTimestamp(3, null);
            }
            if (log.getDatumOtpreme() != null) {
                preparedStatement.setTimestamp(4, new java.sql.Timestamp(log.getDatumOtpreme().getTime()));
            } else {
                preparedStatement.setTimestamp(4, null);
            }
            preparedStatement.setInt(5, log.getIDError());
            preparedStatement.setString(6, log.getResponse());
            if (log.getElectronicID() != null) {
                preparedStatement.setInt(7, log.getElectronicID());
            } else {
                preparedStatement.setNull(7, 0);
            }
            if (log.getStatusID() != null) {
                preparedStatement.setString(8, log.getStatusID());
            } else {
                preparedStatement.setString(8, null);
            }

            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to updateLog\n" + ex.getLocalizedMessage());
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }
    
    public boolean updateLogResponse(Log log) {
        boolean flag = false;
        try {
            String sql = "UPDATE erLog SET IDError = ?, Response = ? WHERE IDDOK = '" + log.getIDDOK() + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setInt(1, log.getIDError());
            preparedStatement.setString(2, log.getResponse());

            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to updateLogResponse\n" + ex.getLocalizedMessage());
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public boolean updateSemafor(String IDRacunara) {
        boolean flag = false;
        try {
            String sql = "UPDATE erSemafor SET ERacun = ? WHERE IDRacunar = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, IDRacunara);

            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            MakeErrorFile.makeTxtErrorFile("Unable to update Semafor\n" + ex.getLocalizedMessage());
            updateLog(log);
            flag = false;
        }
        return flag;
    }

    public LinkedList<String> returnPutanjeXML() {
        LinkedList<String> putanje = new LinkedList<>();
        String sql = "select ncharParam from systblParam where IDParam = '408' or IDParam = '410';";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {

                String putanjaXML = rs.getString("ncharParam");
                putanje.add(putanjaXML.trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class
                    .getName()).log(Level.SEVERE, null, ex);
            log = new Log(getIDDok(), 0, 0, null, new Date(), null, -3, "Vracanje ncharParam nije uspesno", null, null);
            updateLog(log);
            return null;
        }

        return putanje;
    }

    public LinkedList<Pristup> executeProcedure() {
        LinkedList<Pristup> params = new LinkedList<>();
        String sql = "exec erPristupniParametri;";

        try {
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                
                Pristup p = new Pristup();

                int userName = rs.getInt(1);
                p.setUserName(userName);
                
                String pass = rs.getString(2);
                p.setPassword(pass);
                
                String companyID = rs.getString(3);
                p.setCompanyID(companyID);
                
                String softwareID = rs.getString(4);
                p.setSoftwareID(softwareID);
                
                String url = rs.getString(6);
                p.setUrl(url);
                
                params.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class
                    .getName()).log(Level.SEVERE, null, ex);
            log = new Log(getIDDok(), 0, 0, null, new Date(), null, -3, "Execute erPristupniParametri proceure nije uspesno", null, null);
            updateLog(log);
            return null;
        }

        return params;
    }

    public String getNameDB() {
        return nameDB;
    }

    public void setNameDB(String nameDB) {
        this.nameDB = nameDB;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getIDDok() {
        return IDDok;
    }

    public void setIDDok(int IDDok) {
        this.IDDok = IDDok;
    }

}
