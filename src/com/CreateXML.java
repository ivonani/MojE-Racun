/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import bl.Controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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
import model2.Stavke;
import model2.StavkeRabat;
import model2.TaxSubtotal;
import model2.Ugovor;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ivona
 */
public class CreateXML {

    //public static String SCHEMA_LOCATION = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 file:///C:\\\\InSoft\\\\XSD-EU\\\\UBL-Invoice-2.1.xsd";
    //public static String SCHEMA_LOCATION = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 file:///D:/Eposlovanje/UBL2.1/xsd/maindoc/UBL-Invoice-2.1.xsd";
    public static String XMLNS = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
    public static String XADES = "http://uri.etsi.org/01903/v1.3.2#";
    public static String DS = "http://www.w3.org/2000/09/xmldsig#";
    public static String CAC = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
    public static String CBC = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
    public static String CEC = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2";
    public static String N0 = "urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2";
    public static String QDT = "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDataTypes-2";
    public static String SAC = "urn:oasis:names:specification:ubl:schema:xsd:SignatureAggregateComponents-2";
    public static String SBC = "urn:oasis:names:specification:ubl:schema:xsd:SignatureBasicComponents-2";
    public static String UDT = "urn:oasis:names:specification:ubl:schema:xsd:UnqualifiedDataTypes-2";
    public static String CCTS_CCT = "urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2";
    public static String SIG = "urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2";
    public static String XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static String XSD = "http://www.w3.org/2001/XMLSchema";
    public static String SBT = "http://mfin.gov.rs/srbdt/srbdtext";
    public static String SCHEME_ID = "9948";
    public static String CURRENCY_ID = "RSD";
    public static String SCHEME_ID_UN = "UN/ECE 5305";
    public static String SCHEME_AGENCY_ID = "6";
    public static String SCHEME_URI = "http://www.unece.org/trade/untdid/d07a/tred/tred5305.htm";
    public static String SCHEME_ID_ITEM = "0160";

    LinkedList<Log> logs;
    Log log;
    private LinkedList<String> putanje;

    public CreateXML() {
    }

    public String create(int iddok) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        DOMImplementation domImpl = db.getDOMImplementation();
        Document document = domImpl.createDocument("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", null);
        //Invoice
        Element element = document.getDocumentElement();

        makeInvoiceWithAttr(element);

        LinkedList<Dokument> doks = Controller.getInstance().returnDokument(iddok);
        if (doks == null) {
            return null;
        }
        if (doks.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erDokument ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        LinkedList<Narudzbina> nars = Controller.getInstance().returnNarudzbine(iddok);
        LinkedList<Otpremnica> otps = Controller.getInstance().returnOtpremnica(iddok);
        LinkedList<Avans> avns = Controller.getInstance().returnAvansi(iddok);
        LinkedList<DokumentPDF> pdfs = Controller.getInstance().returnDokumentPDF(iddok);
        if (pdfs == null) {
            return null;
        }
        if (pdfs.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erDokumentPDF ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        LinkedList<Ugovor> ugrs = Controller.getInstance().returnUgovori(iddok);
        //podaciORacunu
        boolean flag = makePodaciORacunu(document, element, doks.get(0), nars, otps, avns, ugrs, pdfs);
        if (!flag) {
            return null;
        }

        LinkedList<Dobavljac> dobs = Controller.getInstance().returnDobavljaci(iddok);
        if (dobs == null) {
            return null;
        }
        if (dobs.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erDobavljac ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        //podaciOPosiljaocuDobavljacu
        dobs.forEach((dob) -> {
            makePodaciOPosiljaocuDobavljacu(document, element, dob);
        });

        LinkedList<Kupac> kups = Controller.getInstance().returnKupci(iddok);
        if (kups == null) {
            return null;
        }
        if (kups.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erKupac ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        //podaciOPrimaocuRacuna
        kups.forEach((kup) -> {
            makePodaciOPrimaocuRacuna(document, element, kup);
        });

        LinkedList<Isporuka> isps = Controller.getInstance().returnIsporuke(iddok);
        //podaciOIsporuci
        if (!isps.isEmpty()) {
            isps.forEach((p) -> {
                makePodaciOIsporuci(document, element, p);
            });
        }

        LinkedList<PaymentMeans> pys = Controller.getInstance().returnPaymentMeans(iddok);
        if (pys == null) {
            return null;
        }
        if (pys.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erPaymentMeans ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        //makeNacinPlacanja
        pys.forEach((py) -> {
            makeNaciniPlacanja(document, element, py);
        });

        LinkedList<Footer> foots = Controller.getInstance().returnFooteri(iddok);
        if (foots == null) {
            return null;
        }
        if (foots.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erFooter ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        LinkedList<TaxSubtotal> tss = Controller.getInstance().returnTaxSubtotal(iddok);
        if (tss == null) {
            return null;
        }
        if (tss.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erTaxSubtotal ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        //nacinIUsloviPlacanja
        foots.forEach((foot) -> {
            makeNacinIUsloviPlacanja(document, element, foot, tss);
        });

        LinkedList<Stavke> stvs = Controller.getInstance().returnStavke(iddok);
        if (stvs == null) {
            return null;
        }
        if (stvs.isEmpty()) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -1, "U tabeli erStavke ne postoje podaci", null, null);
            Controller.getInstance().updateLog(log);
            return null;
        }
        //podaciOStavkamaRacuna
        stvs.forEach((stv) -> {
            LinkedList<StavkeRabat> srs = Controller.getInstance().returnStavkeRabat(iddok, stv.getInvoiceLine());
            makePodaciOStavkamaRacuna(document, element, stv, srs);
        });

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        /*boolean feedback = validateXML(source, iddok);
        if (!feedback) {
            return null;
        } else {*/
        System.out.println("true " + putanje.get(0) + pdfs.get(0).getNazivPDF() + ".xml");
        StreamResult streamResult = new StreamResult(new File(putanje.get(0) + pdfs.get(0).getNazivPDF() + ".xml"));
        transformer.transform(source, streamResult);
        System.out.println("" + pdfs.get(0).getNazivPDF());
        //}
        return pdfs.get(0).getNazivPDF();
    }

    private void makeInvoiceWithAttr(Element element) {

        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", XMLNS);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cec", CEC);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", CAC);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", CBC);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", XSI);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", XSD);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sbt", SBT);
    }

    private boolean makePodaciORacunu(Document document, Element element, Dokument dok, LinkedList<Narudzbina> nars, LinkedList<Otpremnica> otps, LinkedList<Avans> avns, LinkedList<Ugovor> ugrs, LinkedList<DokumentPDF> pdfs) {
        /*Comment comment = document.createComment("Fiksno polje");
        element.appendChild(comment);*/

        Element customizationID = document.createElementNS(CBC, "cbc:CustomizationID");
        customizationID.appendChild(document.createTextNode(dok.getCustomizationID()));
        element.appendChild(customizationID);

        Element profileID = document.createElementNS(CBC, "cbc:ProfileID");
        profileID.appendChild(document.createTextNode(dok.getProfileID()));
        element.appendChild(profileID);

        Element id = document.createElementNS(CBC, "cbc:ID");
        id.appendChild(document.createTextNode(dok.getIdRacuna()));
        element.appendChild(id);

        Element issueDate = document.createElementNS(CBC, "cbc:IssueDate");
        issueDate.appendChild(document.createTextNode(dok.getIssueDate()));
        element.appendChild(issueDate);

        Element dueDate = document.createElementNS(CBC, "cbc:DueDate");
        dueDate.appendChild(document.createTextNode(dok.getDueDate()));
        element.appendChild(dueDate);

        Element invoiceTypeCode = document.createElementNS(CBC, "cbc:InvoiceTypeCode");
        invoiceTypeCode.appendChild(document.createTextNode(String.valueOf(dok.getInvoiceTypeCode())));
        element.appendChild(invoiceTypeCode);

        if (dok.getNoteVreme() != null) {
            Element noteVIR = document.createElementNS(CBC, "cbc:Note");
            noteVIR.appendChild(document.createTextNode(dok.getNoteVreme()));
            element.appendChild(noteVIR);
        }

        if (dok.getNoteOperater() != null) {
            Element noteOO = document.createElementNS(CBC, "cbc:Note");
            noteOO.appendChild(document.createTextNode(dok.getNoteOperater()));
            element.appendChild(noteOO);
        }

        if (dok.getNoteOdgovorni() != null) {
            Element noteIPOO = document.createElementNS(CBC, "cbc:Note");
            noteIPOO.appendChild(document.createTextNode(dok.getNoteOdgovorni()));
            element.appendChild(noteIPOO);
        }

        if (dok.getNoteNapomena() != null) {
            Element noteNNND = document.createElementNS(CBC, "cbc:Note");
            noteNNND.appendChild(document.createTextNode(dok.getNoteNapomena()));
            element.appendChild(noteNNND);
        }

        Element documentCurrencyCode = document.createElementNS(CBC, "cbc:DocumentCurrencyCode");
        documentCurrencyCode.appendChild(document.createTextNode(dok.getDocumentCurrencyCode()));
        element.appendChild(documentCurrencyCode);

        Element invoicePeriod = document.createElementNS(CAC, "cac:InvoicePeriod");
        element.appendChild(invoicePeriod);

        if (dok.getInvoiceStartDate() != null) {
            Element startDate = document.createElementNS(CBC, "cbc:StartDate");
            startDate.appendChild(document.createTextNode(dok.getInvoiceStartDate()));
            invoicePeriod.appendChild(startDate);
        }

        if (dok.getInvoiceEndDate() != null) {
            Element endDate = document.createElementNS(CBC, "cbc:EndDate");
            endDate.appendChild(document.createTextNode(dok.getInvoiceEndDate()));
            invoicePeriod.appendChild(endDate);
        }

        Element descriptionCode = document.createElementNS(CBC, "cbc:DescriptionCode");
        descriptionCode.appendChild(document.createTextNode(dok.getDescriptionCode()));
        invoicePeriod.appendChild(descriptionCode);

        if (!nars.isEmpty()) {
            Element orderReference = document.createElementNS(CAC, "cac:OrderReference");
            element.appendChild(orderReference);

            for (Narudzbina n : nars) {
                Element orderReferenceID = document.createElementNS(CBC, "cbc:ID");
                orderReferenceID.appendChild(document.createTextNode(n.getIdNarudzbine()));
                orderReference.appendChild(orderReferenceID);
            }
        }

        if (!otps.isEmpty()) {
            Element despatchDocumentReference = document.createElementNS(CAC, "cac:DespatchDocumentReference");
            element.appendChild(despatchDocumentReference);

            for (Otpremnica otp : otps) {
                Element despatchDocumentReferenceID = document.createElementNS(CBC, "cbc:ID");
                despatchDocumentReferenceID.appendChild(document.createTextNode(otp.getIdOtprenice()));
                despatchDocumentReference.appendChild(despatchDocumentReferenceID);
            }
        }

        if (!avns.isEmpty()) {
            Element billingReference = document.createElementNS(CAC, "cac:BillingReference");
            element.appendChild(billingReference);

            for (Avans avn : avns) {
                Element invoiceDocumentReference = document.createElementNS(CAC, "cac:InvoiceDocumentReference");
                billingReference.appendChild(invoiceDocumentReference);

                Element invoiceDocumentReferenceID = document.createElementNS(CBC, "cbc:ID");
                invoiceDocumentReferenceID.appendChild(document.createTextNode(avn.getIdAvansa()));
                invoiceDocumentReference.appendChild(invoiceDocumentReferenceID);

                Element originatorDocumentReferenceIssueDate = document.createElementNS(CBC, "cbc:IssueDate");
                originatorDocumentReferenceIssueDate.appendChild(document.createTextNode(avn.getIssueDateAvansa()));
                invoiceDocumentReference.appendChild(originatorDocumentReferenceIssueDate);
            }

        }

        if (!ugrs.isEmpty()) {
            Element contractDocumentReference = document.createElementNS(CAC, "cac:ContractDocumentReference");
            element.appendChild(contractDocumentReference);

            for (Ugovor ugr : ugrs) {
                Element contractDocumentReferenceID = document.createElementNS(CBC, "cbc:ID");
                contractDocumentReferenceID.appendChild(document.createTextNode(ugr.getIdUgovora()));
                contractDocumentReference.appendChild(contractDocumentReferenceID);
            }
        }

        for (DokumentPDF pdf : pdfs) {

            Element additionalDocumentReference = document.createElementNS(CAC, "cac:AdditionalDocumentReference");
            element.appendChild(additionalDocumentReference);

            Element additionalDocumentReferenceID = document.createElementNS(CBC, "cbc:ID");
            additionalDocumentReferenceID.appendChild(document.createTextNode(pdf.getNazivPDF()));
            additionalDocumentReference.appendChild(additionalDocumentReferenceID);

            if (pdf.getDocumentType() != null) {
                Element documentType = document.createElementNS(CBC, "cbc:DocumentType");
                documentType.appendChild(document.createTextNode(pdf.getDocumentType()));
                additionalDocumentReference.appendChild(documentType);
            }

            Element attachment = document.createElementNS(CAC, "cac:Attachment");
            additionalDocumentReference.appendChild(attachment);

            String att;
            try {
                att = base64EncodePDF(pdf.getFileName());

            } catch (IOException ex) {
                Logger.getLogger(CreateXML.class
                        .getName()).log(Level.SEVERE, null, ex);
                log = new Log(pdf.getIdDok(), 0, 0, null, new Date(), null, -2, "Nije pronadjen PDF file", null, null);
                Controller.getInstance().updateLog(log);
                return false;
            }
            Element ebeddedDocumentBinaryObject = document.createElementNS(CBC, "cbc:EmbeddedDocumentBinaryObject");
            ebeddedDocumentBinaryObject.appendChild(document.createTextNode(att));
            attachment.appendChild(ebeddedDocumentBinaryObject);

            Attr attr1 = document.createAttribute("filename");
            attr1.setValue(pdf.getFileName());
            ebeddedDocumentBinaryObject.setAttributeNode(attr1);

            Attr attr2 = document.createAttribute("encodingCode");
            attr2.setValue(pdf.getEncodingCode());
            ebeddedDocumentBinaryObject.setAttributeNode(attr2);

            Attr attr3 = document.createAttribute("mimeCode");
            attr3.setValue(pdf.getMimeCode());
            ebeddedDocumentBinaryObject.setAttributeNode(attr3);
        }
        return true;

    }

    private void makePodaciOPosiljaocuDobavljacu(Document document, Element element, Dobavljac dob) {
        Element accountingSupplierParty = document.createElementNS(CAC, "cac:AccountingSupplierParty");
        element.appendChild(accountingSupplierParty);

        Element party = document.createElementNS(CAC, "cac:Party");
        accountingSupplierParty.appendChild(party);

        if (dob.getEndpointID() != null) {
            Element endpointID = document.createElementNS(CBC, "cbc:EndpointID");
            endpointID.appendChild(document.createTextNode(dob.getEndpointID()));
            party.appendChild(endpointID);

            Attr attr1 = document.createAttribute("schemeID");
            attr1.setValue(SCHEME_ID);
            endpointID.setAttributeNode(attr1);
        }

        if (dob.getPartyIdentification() != null) {
            Element partyIdentification = document.createElementNS(CAC, "cac:PartyIdentification");
            party.appendChild(partyIdentification);

            Element partyIdentificationID = document.createElementNS(CBC, "cbc:ID");
            partyIdentificationID.appendChild(document.createTextNode(dob.getPartyIdentification()));
            partyIdentification.appendChild(partyIdentificationID);
        }

        Element partyName = document.createElementNS(CAC, "cac:PartyName");
        party.appendChild(partyName);

        Element name = document.createElementNS(CBC, "cbc:Name");
        name.appendChild(document.createTextNode(dob.getPartyName()));
        partyName.appendChild(name);

        Element postalAddress = document.createElementNS(CAC, "cac:PostalAddress");
        party.appendChild(postalAddress);

        if (dob.getStreetName() != null) {
            Element streetName = document.createElementNS(CBC, "cbc:StreetName");
            streetName.appendChild(document.createTextNode(dob.getStreetName()));
            postalAddress.appendChild(streetName);
        }

        if (dob.getCityName() != null) {
            Element cityName = document.createElementNS(CBC, "cbc:CityName");
            cityName.appendChild(document.createTextNode(dob.getCityName()));
            postalAddress.appendChild(cityName);
        }

        if (dob.getPostalZone() != null) {
            Element postalZone = document.createElementNS(CBC, "cbc:PostalZone");
            postalZone.appendChild(document.createTextNode(dob.getPostalZone()));
            postalAddress.appendChild(postalZone);
        }

        if (dob.getAddressLine() != null) {
            Element addressLine = document.createElementNS(CAC, "cac:AddressLine");
            postalAddress.appendChild(addressLine);

            Element line = document.createElementNS(CBC, "cbc:Line");
            line.appendChild(document.createTextNode(dob.getAddressLine()));
            addressLine.appendChild(line);
        }

        if (dob.getCountryID() != null) {
            Element country = document.createElementNS(CAC, "cac:Country");
            postalAddress.appendChild(country);

            Element identificationCode = document.createElementNS(CBC, "cbc:IdentificationCode");
            identificationCode.appendChild(document.createTextNode(dob.getCountryID()));
            country.appendChild(identificationCode);
        }

        Element partyTaxScheme = document.createElementNS(CAC, "cac:PartyTaxScheme");
        party.appendChild(partyTaxScheme);

        Element companyID = document.createElementNS(CBC, "cbc:CompanyID");
        companyID.appendChild(document.createTextNode(dob.getCountryID() + dob.getPib()));
        partyTaxScheme.appendChild(companyID);

        Element taxScheme = document.createElementNS(CAC, "cac:TaxScheme");
        partyTaxScheme.appendChild(taxScheme);

        Element taxSchemeID = document.createElementNS(CBC, "cbc:ID");
        taxSchemeID.appendChild(document.createTextNode(dob.getTaxSchemeID()));
        taxScheme.appendChild(taxSchemeID);

        Element partyLegalEntity = document.createElementNS(CAC, "cac:PartyLegalEntity");
        party.appendChild(partyLegalEntity);

        Element registrationName = document.createElementNS(CBC, "cbc:RegistrationName");
        registrationName.appendChild(document.createTextNode(dob.getRegistrationName()));
        partyLegalEntity.appendChild(registrationName);

        Element pLEcompanyID = document.createElementNS(CBC, "cbc:CompanyID");
        pLEcompanyID.appendChild(document.createTextNode(dob.getMbr()));
        partyLegalEntity.appendChild(pLEcompanyID);

        if (dob.getCompanyLegalForm() != null) {
            Element companyLegalForm = document.createElementNS(CBC, "cbc:CompanyLegalForm");
            companyLegalForm.appendChild(document.createTextNode(dob.getCompanyLegalForm()));
            partyLegalEntity.appendChild(companyLegalForm);
        }

        if (dob.getContactName() != null || dob.getContactPhone() != null || dob.getContactMail() != null) {
            Element contact = document.createElementNS(CAC, "cac:Contact");
            party.appendChild(contact);

            if (dob.getContactName() != null) {
                Element contactName = document.createElementNS(CBC, "cbc:Name");
                contactName.appendChild(document.createTextNode(dob.getContactName()));
                contact.appendChild(contactName);
            }

            if (dob.getContactPhone() != null) {
                Element contactTelephone = document.createElementNS(CBC, "cbc:Telephone");
                contactTelephone.appendChild(document.createTextNode(dob.getContactPhone()));
                contact.appendChild(contactTelephone);
            }

            Element contactElectronicMail = document.createElementNS(CBC, "cbc:ElectronicMail");
            contactElectronicMail.appendChild(document.createTextNode(dob.getContactMail()));
            contact.appendChild(contactElectronicMail);
        }
    }

    private void makePodaciOPrimaocuRacuna(Document document, Element element, Kupac kup) {
        Element accountingSupplierParty = document.createElementNS(CAC, "cac:AccountingCustomerParty");
        element.appendChild(accountingSupplierParty);

        Element party = document.createElementNS(CAC, "cac:Party");
        accountingSupplierParty.appendChild(party);

        if (kup.getEndpointID() != null) {
            Element endpointID = document.createElementNS(CBC, "cbc:EndpointID");
            endpointID.appendChild(document.createTextNode(kup.getEndpointID()));
            party.appendChild(endpointID);

            Attr attr1 = document.createAttribute("schemeID");
            attr1.setValue(SCHEME_ID);
            endpointID.setAttributeNode(attr1);
        }

        if (kup.getPartyIdentification() != null) {
            Element partyIdentification = document.createElementNS(CAC, "cac:PartyIdentification");
            party.appendChild(partyIdentification);

            Element partyIdentificationID = document.createElementNS(CBC, "cbc:ID");
            partyIdentificationID.appendChild(document.createTextNode(kup.getPartyIdentification()));
            partyIdentification.appendChild(partyIdentificationID);
        }

        Element partyName = document.createElementNS(CAC, "cac:PartyName");
        party.appendChild(partyName);

        Element name = document.createElementNS(CBC, "cbc:Name");
        name.appendChild(document.createTextNode(kup.getPartyName()));
        partyName.appendChild(name);

        Element postalAddress = document.createElementNS(CAC, "cac:PostalAddress");
        party.appendChild(postalAddress);

        if (kup.getStreetName() != null) {
            Element streetName = document.createElementNS(CBC, "cbc:StreetName");
            streetName.appendChild(document.createTextNode(kup.getStreetName()));
            postalAddress.appendChild(streetName);
        }

        if (kup.getCityName() != null) {
            Element cityName = document.createElementNS(CBC, "cbc:CityName");
            cityName.appendChild(document.createTextNode(kup.getCityName()));
            postalAddress.appendChild(cityName);
        }

        if (kup.getPostalZone() != null) {
            Element postalZone = document.createElementNS(CBC, "cbc:PostalZone");
            postalZone.appendChild(document.createTextNode(kup.getPostalZone()));
            postalAddress.appendChild(postalZone);
        }

        if (kup.getAddressLine() != null) {
            Element addressLine = document.createElementNS(CAC, "cac:AddressLine");
            postalAddress.appendChild(addressLine);

            Element line = document.createElementNS(CBC, "cbc:Line");
            line.appendChild(document.createTextNode(kup.getAddressLine()));
            addressLine.appendChild(line);
        }

        if (kup.getCountryID() != null) {
            Element country = document.createElementNS(CAC, "cac:Country");
            postalAddress.appendChild(country);

            Element identificationCode = document.createElementNS(CBC, "cbc:IdentificationCode");
            identificationCode.appendChild(document.createTextNode(kup.getCountryID()));
            country.appendChild(identificationCode);
        }

        if (kup.getCountryID() != null || kup.getPib() != null || kup.getTaxSchemeID() != null) {
            Element partyTaxScheme = document.createElementNS(CAC, "cac:PartyTaxScheme");
            party.appendChild(partyTaxScheme);

            if (kup.getCountryID() != null && kup.getPib() != null) {
                Element companyID = document.createElementNS(CBC, "cbc:CompanyID");
                companyID.appendChild(document.createTextNode(kup.getCountryID() + kup.getPib()));
                partyTaxScheme.appendChild(companyID);
            }

            if (kup.getTaxSchemeID() != null) {
                Element taxScheme = document.createElementNS(CAC, "cac:TaxScheme");
                partyTaxScheme.appendChild(taxScheme);

                Element taxSchemeID = document.createElementNS(CBC, "cbc:ID");
                taxSchemeID.appendChild(document.createTextNode(kup.getTaxSchemeID()));
                taxScheme.appendChild(taxSchemeID);
            }
        }

        Element partyLegalEntity = document.createElementNS(CAC, "cac:PartyLegalEntity");
        party.appendChild(partyLegalEntity);

        Element registrationName = document.createElementNS(CBC, "cbc:RegistrationName");
        registrationName.appendChild(document.createTextNode(kup.getRegistrationName()));
        partyLegalEntity.appendChild(registrationName);

        Element pLEcompanyID = document.createElementNS(CBC, "cbc:CompanyID");
        pLEcompanyID.appendChild(document.createTextNode(kup.getMbr()));
        partyLegalEntity.appendChild(pLEcompanyID);

        Element contact = document.createElementNS(CAC, "cac:Contact");
        party.appendChild(contact);

        if (kup.getContactName() != null) {
            Element contactName = document.createElementNS(CBC, "cbc:Name");
            contactName.appendChild(document.createTextNode(kup.getContactName()));
            contact.appendChild(contactName);
        }

        if (kup.getContactPhone() != null) {
            Element contactTelephone = document.createElementNS(CBC, "cbc:Telephone");
            contactTelephone.appendChild(document.createTextNode(kup.getContactPhone()));
            contact.appendChild(contactTelephone);
        }

        Element contactElectronicMail = document.createElementNS(CBC, "cbc:ElectronicMail");
        contactElectronicMail.appendChild(document.createTextNode(kup.getContactMail()));
        contact.appendChild(contactElectronicMail);
    }

    private void makePodaciOIsporuci(Document document, Element element, Isporuka i) {
        if (i.getActualDeliveryDate() != null) {
            Element delivery = document.createElementNS(CAC, "cac:Delivery");
            element.appendChild(delivery);

            Element actualDeliveryDate = document.createElementNS(CBC, "cbc:ActualDeliveryDate");
            actualDeliveryDate.appendChild(document.createTextNode(i.getActualDeliveryDate()));
            delivery.appendChild(actualDeliveryDate);

            if (i.getStreetNameLocation() != null || i.getCityNameLocation() != null || i.getPostalZoneLocation() != null || i.getCountryIDLocation() != null) {
                Element deliveryLocation = document.createElementNS(CAC, "cac:DeliveryLocation");
                delivery.appendChild(deliveryLocation);

                Element address = document.createElementNS(CAC, "cac:Address");
                deliveryLocation.appendChild(address);

                if (i.getStreetName() != null) {
                    Element streetName = document.createElementNS(CBC, "cbc:StreetName");
                    streetName.appendChild(document.createTextNode(i.getStreetNameLocation()));
                    address.appendChild(streetName);
                }

                if (i.getCityNameLocation() != null) {
                    Element cityName = document.createElementNS(CBC, "cbc:CityName");
                    cityName.appendChild(document.createTextNode(i.getCityNameLocation()));
                    address.appendChild(cityName);
                }

                if (i.getPostalZoneLocation() != null) {
                    Element postalZone = document.createElementNS(CBC, "cbc:PostalZone");
                    postalZone.appendChild(document.createTextNode(i.getPostalZoneLocation()));
                    address.appendChild(postalZone);
                }

                if (i.getCountryIDLocation() != null) {
                    Element country = document.createElementNS(CAC, "cac:Country");
                    address.appendChild(country);

                    Element identificationCode = document.createElementNS(CBC, "cbc:IdentificationCode");
                    identificationCode.appendChild(document.createTextNode(i.getCountryIDLocation()));
                    country.appendChild(identificationCode);
                }
            }

            if (i.getEndpointID() != null) {
                Element deliveryParty = document.createElementNS(CAC, "cac:DeliveryParty");
                delivery.appendChild(deliveryParty);

                Element endpointID = document.createElementNS(CBC, "cbc:EndpointID");
                endpointID.appendChild(document.createTextNode(i.getEndpointID()));
                deliveryParty.appendChild(endpointID);

                if (i.getPartyIdentification() != null) {
                    Element partyIdentification = document.createElementNS(CAC, "cac:PartyIdentification");
                    deliveryParty.appendChild(partyIdentification);

                    Element partyIdentificationID = document.createElementNS(CBC, "cbc:ID");
                    partyIdentificationID.appendChild(document.createTextNode(i.getPartyIdentification()));
                    partyIdentification.appendChild(partyIdentificationID);
                }

                if (i.getPartyName() != null) {
                    Element partyName = document.createElementNS(CAC, "cac:PartyName");
                    deliveryParty.appendChild(partyName);

                    Element name = document.createElementNS(CBC, "cbc:Name");
                    name.appendChild(document.createTextNode(i.getPartyName()));
                    partyName.appendChild(name);
                }

                if (i.getStreetName() != null) {
                    Element postalAddress = document.createElementNS(CAC, "cac:PostalAddress");
                    deliveryParty.appendChild(postalAddress);

                    Element dPStreetName = document.createElementNS(CBC, "cbc:StreetName");
                    dPStreetName.appendChild(document.createTextNode(i.getStreetName()));
                    postalAddress.appendChild(dPStreetName);

                    Element dPCityName = document.createElementNS(CBC, "cbc:CityName");
                    dPCityName.appendChild(document.createTextNode(i.getCityName()));
                    postalAddress.appendChild(dPCityName);

                    Element dPPostalZone = document.createElementNS(CBC, "cbc:PostalZone");
                    dPPostalZone.appendChild(document.createTextNode(i.getPostalZone()));
                    postalAddress.appendChild(dPPostalZone);

                    if (i.getAddressLine() != null) {
                        Element dPAddressLine = document.createElementNS(CAC, "cac:AddressLine");
                        postalAddress.appendChild(dPAddressLine);

                        Element line = document.createElementNS(CBC, "cbc:Line");
                        line.appendChild(document.createTextNode(i.getAddressLine()));
                        dPAddressLine.appendChild(line);

                    }

                    if (i.getCountryID() != null) {
                        Element dpCountry = document.createElementNS(CAC, "cac:Country");
                        postalAddress.appendChild(dpCountry);

                        Element dPIdentificationCode = document.createElementNS(CBC, "cbc:IdentificationCode");
                        dPIdentificationCode.appendChild(document.createTextNode(i.getCountryID()));
                        dpCountry.appendChild(dPIdentificationCode);
                    }
                }

                if (i.getPib() != null) {
                    Element partyTaxScheme = document.createElementNS(CAC, "cac:PartyTaxScheme");
                    deliveryParty.appendChild(partyTaxScheme);

                    Element companyID = document.createElementNS(CBC, "cbc:CompanyID");
                    companyID.appendChild(document.createTextNode(i.getPib()));
                    partyTaxScheme.appendChild(companyID);

                    if (i.getTaxSchemeID() != null) {
                        Element taxScheme = document.createElementNS(CAC, "cac:TaxScheme");
                        partyTaxScheme.appendChild(taxScheme);

                        Element taxSchemeID = document.createElementNS(CBC, "cbc:ID");
                        taxSchemeID.appendChild(document.createTextNode(i.getTaxSchemeID()));
                        taxScheme.appendChild(taxSchemeID);
                    }
                }

                if (i.getRegistrationName() != null || i.getMbr() != null) {
                    Element partyLegalEntity = document.createElementNS(CAC, "cac:PartyLegalEntity");
                    deliveryParty.appendChild(partyLegalEntity);

                    Element registrationName = document.createElementNS(CBC, "cbc:RegistrationName");
                    registrationName.appendChild(document.createTextNode(i.getRegistrationName()));
                    partyLegalEntity.appendChild(registrationName);

                    if (i.getMbr() != null) {
                        Element pleCompanyID = document.createElementNS(CBC, "cbc:CompanyID");
                        pleCompanyID.appendChild(document.createTextNode(i.getMbr()));
                        partyLegalEntity.appendChild(pleCompanyID);
                    }
                }
            }

        }

    }

    private void makeNaciniPlacanja(Document document, Element element, PaymentMeans py) {
        Element paymentMeans = document.createElementNS(CAC, "cac:PaymentMeans");
        element.appendChild(paymentMeans);

        Element paymentMeansCode = document.createElementNS(CBC, "cbc:PaymentMeansCode");
        paymentMeansCode.appendChild(document.createTextNode(py.getPaymentMeansCode()));
        paymentMeans.appendChild(paymentMeansCode);

        if (py.getInstructionNote() != null) {
            Element instructionNote = document.createElementNS(CBC, "cbc:InstructionNote");
            instructionNote.appendChild(document.createTextNode(py.getInstructionNote()));
            paymentMeans.appendChild(instructionNote);
        }

        if (py.getPaymentID() != null) {
            Element paymentID = document.createElementNS(CBC, "cbc:PaymentID");
            paymentID.appendChild(document.createTextNode(py.getPaymentID()));
            paymentMeans.appendChild(paymentID);
        }

        Element payeeFinancialAccount = document.createElementNS(CAC, "cac:PayeeFinancialAccount");
        paymentMeans.appendChild(payeeFinancialAccount);

        Element payeeFinancialAccountID = document.createElementNS(CBC, "cbc:ID");
        payeeFinancialAccountID.appendChild(document.createTextNode(py.getPayeeFinancialAccount()));
        payeeFinancialAccount.appendChild(payeeFinancialAccountID);

        if (py.getPaymentTermsNote() != null) {
            Element paymentTerms = document.createElementNS(CAC, "cac:PaymentTerms");
            element.appendChild(paymentTerms);

            Element note = document.createElementNS(CBC, "cbc:Note");
            note.appendChild(document.createTextNode(py.getPaymentTermsNote()));
            paymentTerms.appendChild(note);
        }
    }

    private void makeNacinIUsloviPlacanja(Document document, Element element, Footer f, LinkedList<TaxSubtotal> tss) {
        if (f.getChargeIndicator() != null || f.getAllowanceChargeReason() != null || f.getMultiplierFactorNumeric() != null || f.getAmount() != null || f.getBaseAmount() != null) {
            Element allowanceCharge = document.createElementNS(CAC, "cac:AllowanceCharge");
            element.appendChild(allowanceCharge);

            if (f.getChargeIndicator() != null) {
                Element chargeIndicator = document.createElementNS(CBC, "cbc:ChargeIndicator");
                chargeIndicator.appendChild(document.createTextNode(f.getChargeIndicator()));
                allowanceCharge.appendChild(chargeIndicator);
            }

            if (f.getAllowanceChargeReason() != null) {
                Element allowanceChargeReason = document.createElementNS(CBC, "cbc:AllowanceChargeReason");
                allowanceChargeReason.appendChild(document.createTextNode(f.getAllowanceChargeReason()));
                allowanceCharge.appendChild(allowanceChargeReason);
            }

            if (f.getMultiplierFactorNumeric() != null) {
                Element multiplierFactorNumeric = document.createElementNS(CBC, "cbc:MultiplierFactorNumeric");
                multiplierFactorNumeric.appendChild(document.createTextNode(String.valueOf(f.getMultiplierFactorNumeric())));
                allowanceCharge.appendChild(multiplierFactorNumeric);
            }

            if (f.getAmount() != null) {
                Element amount = document.createElementNS(CBC, "cbc:Amount");
                amount.appendChild(document.createTextNode(String.valueOf(f.getAmount())));
                allowanceCharge.appendChild(amount);

                Attr attr1 = document.createAttribute("currencyID");
                attr1.setValue(CURRENCY_ID);
                amount.setAttributeNode(attr1);
            }

            if (f.getBaseAmount() != null) {
                Element baseAmount = document.createElementNS(CBC, "cbc:BaseAmount");
                baseAmount.appendChild(document.createTextNode(String.valueOf(f.getBaseAmount())));
                allowanceCharge.appendChild(baseAmount);

                Attr attr2 = document.createAttribute("currencyID");
                attr2.setValue(CURRENCY_ID);
                baseAmount.setAttributeNode(attr2);
            }
        }

        Element taxTotal = document.createElementNS(CAC, "cac:TaxTotal");
        element.appendChild(taxTotal);

        Element taxAmount = document.createElementNS(CBC, "cbc:TaxAmount");
        taxAmount.appendChild(document.createTextNode(String.valueOf(f.getTaxAmount())));
        taxTotal.appendChild(taxAmount);

        Attr attr1 = document.createAttribute("currencyID");
        attr1.setValue(CURRENCY_ID);
        taxAmount.setAttributeNode(attr1);

        for (TaxSubtotal ts : tss) {
            Element taxSubtotal = document.createElementNS(CAC, "cac:TaxSubtotal");
            taxTotal.appendChild(taxSubtotal);

            Element taxableAmount = document.createElementNS(CBC, "cbc:TaxableAmount");
            taxableAmount.appendChild(document.createTextNode(String.valueOf(ts.getTaxableAmount())));
            taxSubtotal.appendChild(taxableAmount);

            Attr attr2 = document.createAttribute("currencyID");
            attr2.setValue(CURRENCY_ID);
            taxableAmount.setAttributeNode(attr2);

            Element subTaxAmount = document.createElementNS(CBC, "cbc:TaxAmount");
            subTaxAmount.appendChild(document.createTextNode(String.valueOf(ts.getTaxAmount())));
            taxSubtotal.appendChild(subTaxAmount);

            Attr attr3 = document.createAttribute("currencyID");
            attr3.setValue(CURRENCY_ID);
            subTaxAmount.setAttributeNode(attr3);

            Element taxCategory = document.createElementNS(CAC, "cac:TaxCategory");
            taxSubtotal.appendChild(taxCategory);

            Element taxCategoryID = document.createElementNS(CBC, "cbc:ID");
            taxCategoryID.appendChild(document.createTextNode(ts.getTaxCategoryID()));
            taxCategory.appendChild(taxCategoryID);

            Attr attr7 = document.createAttribute("schemeID");
            attr7.setValue(SCHEME_ID_UN);
            taxCategoryID.setAttributeNode(attr7);

            Attr attr4 = document.createAttribute("schemeAgencyID");
            attr4.setValue(SCHEME_AGENCY_ID);
            taxCategoryID.setAttributeNode(attr4);

            Attr attr5 = document.createAttribute("schemeURI");
            attr5.setValue(SCHEME_URI);
            taxCategoryID.setAttributeNode(attr5);

            Element percent = document.createElementNS(CBC, "cbc:Percent");
            percent.appendChild(document.createTextNode(String.valueOf(ts.getTaxCategoryPercent())));
            taxCategory.appendChild(percent);

            if (ts.getTaxExemptionReasonCode() != null) {
                Element taxExemptionReasonCode = document.createElementNS(CBC, "cbc:TaxExemptionReasonCode");
                taxExemptionReasonCode.appendChild(document.createTextNode(ts.getTaxExemptionReasonCode()));
                taxCategory.appendChild(taxExemptionReasonCode);
            }

            if (ts.getTaxExemptionReason() != null) {
                Element taxExemptionReason = document.createElementNS(CBC, "cbc:TaxExemptionReason");
                taxExemptionReason.appendChild(document.createTextNode(ts.getTaxExemptionReason()));
                taxCategory.appendChild(taxExemptionReason);
            }

            Element taxScheme = document.createElementNS(CAC, "cac:TaxScheme");
            taxCategory.appendChild(taxScheme);

            Element taxSchemeID = document.createElementNS(CBC, "cbc:ID");
            taxSchemeID.appendChild(document.createTextNode(ts.getTaxSchemeID()));
            taxScheme.appendChild(taxSchemeID);
        }

        Element legalMonetaryTotal = document.createElementNS(CAC, "cac:LegalMonetaryTotal");
        element.appendChild(legalMonetaryTotal);

        Element lineExtensionAmount = document.createElementNS(CBC, "cbc:LineExtensionAmount");
        lineExtensionAmount.appendChild(document.createTextNode(String.valueOf(f.getLineExtensionAmount())));
        legalMonetaryTotal.appendChild(lineExtensionAmount);

        Attr attr6 = document.createAttribute("currencyID");
        attr6.setValue(CURRENCY_ID);
        lineExtensionAmount.setAttributeNode(attr6);

        Element taxExclusiveAmount = document.createElementNS(CBC, "cbc:TaxExclusiveAmount");
        taxExclusiveAmount.appendChild(document.createTextNode(String.valueOf(f.getTaxExclusiveAmount())));
        legalMonetaryTotal.appendChild(taxExclusiveAmount);

        Attr attr9 = document.createAttribute("currencyID");
        attr9.setValue(CURRENCY_ID);
        taxExclusiveAmount.setAttributeNode(attr9);

        Element taxInclusiveAmount = document.createElementNS(CBC, "cbc:TaxInclusiveAmount");
        taxInclusiveAmount.appendChild(document.createTextNode(String.valueOf(f.getTaxInclusiveAmount())));
        legalMonetaryTotal.appendChild(taxInclusiveAmount);

        Attr attr10 = document.createAttribute("currencyID");
        attr10.setValue(CURRENCY_ID);
        taxInclusiveAmount.setAttributeNode(attr10);

        Element allowanceTotalAmount = document.createElementNS(CBC, "cbc:AllowanceTotalAmount");
        allowanceTotalAmount.appendChild(document.createTextNode(String.valueOf(f.getAllowanceTotalAmount())));
        legalMonetaryTotal.appendChild(allowanceTotalAmount);

        Attr attr11 = document.createAttribute("currencyID");
        attr11.setValue(CURRENCY_ID);
        allowanceTotalAmount.setAttributeNode(attr11);

        if (f.getChargeTotalAmount() != null) {
            Element chargeTotalAmount = document.createElementNS(CBC, "cbc:ChargeTotalAmount");
            chargeTotalAmount.appendChild(document.createTextNode(String.valueOf(f.getChargeTotalAmount())));
            legalMonetaryTotal.appendChild(chargeTotalAmount);

            Attr attr12 = document.createAttribute("currencyID");
            attr12.setValue(CURRENCY_ID);
            chargeTotalAmount.setAttributeNode(attr12);
        }

        if (f.getPrepaidAmount() != null) {
            Element prepaidAmount = document.createElementNS(CBC, "cbc:PrepaidAmount");
            prepaidAmount.appendChild(document.createTextNode(String.valueOf(f.getPrepaidAmount())));
            legalMonetaryTotal.appendChild(prepaidAmount);

            Attr attr13 = document.createAttribute("currencyID");
            attr13.setValue(CURRENCY_ID);
            prepaidAmount.setAttributeNode(attr13);
        }

        Element payableAmount = document.createElementNS(CBC, "cbc:PayableAmount");
        payableAmount.appendChild(document.createTextNode(String.valueOf(f.getPayableAmount())));
        legalMonetaryTotal.appendChild(payableAmount);

        Attr attr12 = document.createAttribute("currencyID");
        attr12.setValue(CURRENCY_ID);
        payableAmount.setAttributeNode(attr12);

    }

    private void makePodaciOStavkamaRacuna(Document document, Element element, Stavke s, LinkedList<StavkeRabat> srs) {
        Element invoiceLine = document.createElementNS(CAC, "cac:InvoiceLine");
        element.appendChild(invoiceLine);

        Element invoiceLineID = document.createElementNS(CBC, "cbc:ID");
        invoiceLineID.appendChild(document.createTextNode(String.valueOf(s.getInvoiceLine())));
        invoiceLine.appendChild(invoiceLineID);

        Element invoicedQuantity = document.createElementNS(CBC, "cbc:InvoicedQuantity");
        invoicedQuantity.appendChild(document.createTextNode(String.valueOf(s.getQuantity())));
        invoiceLine.appendChild(invoicedQuantity);

        Attr attr1 = document.createAttribute("unitCode");
        attr1.setValue(s.getUnitCode());
        invoicedQuantity.setAttributeNode(attr1);

        Element lineExtensionAmount = document.createElementNS(CBC, "cbc:LineExtensionAmount");
        lineExtensionAmount.appendChild(document.createTextNode(String.valueOf(s.getLineExtensionAmount())));
        invoiceLine.appendChild(lineExtensionAmount);

        Attr attr2 = document.createAttribute("currencyID");
        attr2.setValue(CURRENCY_ID);
        lineExtensionAmount.setAttributeNode(attr2);

        if (s.getInvoiceStartDate() != null || s.getInvoiceEndDate() != null) {
            Element invoicePeriod = document.createElementNS(CAC, "cac:InvoicePeriod");
            invoiceLine.appendChild(invoicePeriod);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Element startDate = document.createElementNS(CBC, "cbc:StartDate");
            String sDate = sdf.format(s.getInvoiceStartDate());
            startDate.appendChild(document.createTextNode(sDate));
            invoicePeriod.appendChild(startDate);

            Element endDate = document.createElementNS(CBC, "cbc:EndDate");
            String eDate = sdf.format(s.getInvoiceEndDate());
            endDate.appendChild(document.createTextNode(eDate));
            invoicePeriod.appendChild(endDate);
        }

        if (srs != null && !srs.isEmpty()) {
            for (StavkeRabat sr : srs) {
                Element allowanceCharge = document.createElementNS(CAC, "cac:AllowanceCharge");
                invoiceLine.appendChild(allowanceCharge);

                if (sr.getChargeIndicator() != null) {
                    Element chargeIndicator = document.createElementNS(CBC, "cbc:ChargeIndicator");
                    chargeIndicator.appendChild(document.createTextNode(String.valueOf(sr.getChargeIndicator())));
                    allowanceCharge.appendChild(chargeIndicator);
                }

                Element allowanceChargeReason = document.createElementNS(CBC, "cbc:AllowanceChargeReason");
                if (sr.getAllowanceChargeReason() == null) {
                    allowanceChargeReason.appendChild(document.createTextNode(""));
                    allowanceCharge.appendChild(allowanceChargeReason);
                } else {
                    allowanceChargeReason.appendChild(document.createTextNode(sr.getAllowanceChargeReason()));
                    allowanceCharge.appendChild(allowanceChargeReason);
                }

                if (sr.getMultiplierFactorNumeric() != null) {
                    Element multiplierFactorNumeric = document.createElementNS(CBC, "cbc:MultiplierFactorNumeric");
                    multiplierFactorNumeric.appendChild(document.createTextNode(String.valueOf(sr.getMultiplierFactorNumeric())));
                    allowanceCharge.appendChild(multiplierFactorNumeric);
                }

                if (sr.getAmount() != null) {
                    Element amount = document.createElementNS(CBC, "cbc:Amount");
                    amount.appendChild(document.createTextNode(String.valueOf(sr.getAmount())));
                    allowanceCharge.appendChild(amount);

                    Attr attr3 = document.createAttribute("currencyID");
                    attr3.setValue(CURRENCY_ID);
                    amount.setAttributeNode(attr3);
                }

                if (sr.getBaseAmount() != null) {
                    Element baseAmount = document.createElementNS(CBC, "cbc:BaseAmount");
                    baseAmount.appendChild(document.createTextNode(String.valueOf(sr.getBaseAmount())));
                    allowanceCharge.appendChild(baseAmount);

                    Attr attr4 = document.createAttribute("currencyID");
                    attr4.setValue(CURRENCY_ID);
                    baseAmount.setAttributeNode(attr4);
                }
            }
        }

        Element item = document.createElementNS(CAC, "cac:Item");
        invoiceLine.appendChild(item);

        Element name = document.createElementNS(CBC, "cbc:Name");
        name.appendChild(document.createTextNode(s.getItemName()));
        item.appendChild(name);

        if (s.getBuyersItemIdentification() != null) {
            Element buyersItemIdentification = document.createElementNS(CAC, "cac:BuyersItemIdentification");
            item.appendChild(buyersItemIdentification);

            Element buyersItemIdentificationID = document.createElementNS(CBC, "cbc:ID");
            buyersItemIdentificationID.appendChild(document.createTextNode(s.getBuyersItemIdentification()));
            buyersItemIdentification.appendChild(buyersItemIdentificationID);
        }

        if (s.getSellersItemIdentification() != null) {
            Element sellersItemIdentification = document.createElementNS(CAC, "cac:SellersItemIdentification");
            item.appendChild(sellersItemIdentification);

            Element sellersItemIdentificationID = document.createElementNS(CBC, "cbc:ID");
            sellersItemIdentificationID.appendChild(document.createTextNode(s.getSellersItemIdentification().trim()));
            sellersItemIdentification.appendChild(sellersItemIdentificationID);
        }

        if (s.getStandardItemIdentification() != null) {
            Element standardItemIdentification = document.createElementNS(CAC, "cac:StandardItemIdentification");
            item.appendChild(standardItemIdentification);

            Element standardItemIdentificationID = document.createElementNS(CBC, "cbc:ID");
            standardItemIdentificationID.appendChild(document.createTextNode(s.getStandardItemIdentification()));
            standardItemIdentification.appendChild(standardItemIdentificationID);

            Attr attr5 = document.createAttribute("schemeID");
            attr5.setValue(SCHEME_ID_ITEM);
            standardItemIdentificationID.setAttributeNode(attr5);
        }

        Element classifiedTaxCategory = document.createElementNS(CAC, "cac:ClassifiedTaxCategory");
        item.appendChild(classifiedTaxCategory);

        Element taxCategoryID = document.createElementNS(CBC, "cbc:ID");
        taxCategoryID.appendChild(document.createTextNode(s.getTaxCategoryID()));
        classifiedTaxCategory.appendChild(taxCategoryID);

        Element percent = document.createElementNS(CBC, "cbc:Percent");
        percent.appendChild(document.createTextNode(String.valueOf(s.getTaxCategoryPercent())));
        classifiedTaxCategory.appendChild(percent);

        if (s.getTaxExemptionReason() != null) {
            Element taxExemptionReason = document.createElementNS(CBC, "cbc:TaxExemptionReason");
            taxExemptionReason.appendChild(document.createTextNode(s.getTaxExemptionReason()));
            classifiedTaxCategory.appendChild(taxExemptionReason);
        }

        Element taxScheme = document.createElementNS(CAC, "cac:TaxScheme");
        classifiedTaxCategory.appendChild(taxScheme);

        Element taxSchemeID = document.createElementNS(CBC, "cbc:ID");
        taxSchemeID.appendChild(document.createTextNode(s.getTaxSchemeID()));
        taxScheme.appendChild(taxSchemeID);

        Element price = document.createElementNS(CAC, "cac:Price");
        invoiceLine.appendChild(price);

        Element priceAmount = document.createElementNS(CBC, "cbc:PriceAmount");
        priceAmount.appendChild(document.createTextNode(String.valueOf(s.getPriceAmount())));
        price.appendChild(priceAmount);

        Attr attr6 = document.createAttribute("currencyID");
        attr6.setValue(CURRENCY_ID);
        priceAmount.setAttributeNode(attr6);

        Element baseQuantity = document.createElementNS(CBC, "cbc:BaseQuantity");
        baseQuantity.appendChild(document.createTextNode(String.valueOf(s.getBaseQuantity())));
        price.appendChild(baseQuantity);

        Attr attr7 = document.createAttribute("unitCode");
        attr7.setValue(s.getUnitCode());
        baseQuantity.setAttributeNode(attr7);
    }

    private String base64EncodePDF(String filename) throws IOException {
        File file = new File(filename);
        byte[] bytes = Files.readAllBytes(file.toPath());
        String b64 = Base64.getEncoder().encodeToString(bytes);

        return b64;
    }

    private boolean validateXML(DOMSource source, int iddok) throws Exception {
        File schemaFile = new File(putanje.get(1) + "UBL-Invoice-2.1.xsd");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        boolean flag;
        Validator validator = schema.newValidator();
        DOMResult result = new DOMResult();
        try {
            validator.validate(source, result);
            System.out.println("XML file is valid");

            flag = true;
        } catch (SAXException e) {
            System.out.println("XML file is not valid because " + e.getLocalizedMessage());
            log = new Log(iddok, 0, 0, null, new Date(), null, -4, e.getLocalizedMessage(), null, null);
            Controller.getInstance().updateLog(log);
            flag = false;
        }
        return flag;
    }

    public LinkedList<String> getPutanje() {
        return putanje;
    }

    public void setPutanje(LinkedList<String> putanje, int iddok) {
        try {
            this.putanje = putanje;
        } catch (Exception e) {
            log = new Log(iddok, 0, 0, null, new Date(), null, -7, "Unable to set putanje", null, null);
            Controller.getInstance().updateLog(log);
        }
        
    }

}
