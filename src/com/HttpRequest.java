/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import bl.Controller;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model2.Log;
import model2.Pristup;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ivona
 */
public class HttpRequest {

    private LinkedList<String> putanje;
    private LinkedList<Pristup> params;

    public void httpRequest(String naziv, int IDDok) throws IOException {
        String xmlString;
        Log log;
        Date datumSlanja;
        File xmlFile = new File(putanje.get(0) + naziv + ".xml");
        //File xmlFile = new File("C:\\insoft\\PFM-3394_21-10.xml");
        try {
            xmlString = readXML(xmlFile);

            HttpURLConnection httpUrlConnection = null;
            //https://demo.moj-eracun.rs/apis/v2/send
            URL url = new URL(params.get(0).getUrl() + "");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Content-Type", "application/json; charset:utf-8");
            httpUrlConnection.addRequestProperty("Accept", "application/json");

            String jsonInputString = createJSONRequest(xmlString);

            //send request
            OutputStream os = httpUrlConnection.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);

            datumSlanja = new Date();

            //read response
            InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());

            BufferedReader responseStreamReader
                    = new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

            String response = stringBuilder.toString();

            JSONObject json;
            try {
                json = new JSONObject(response);
                System.out.println(json.toString());
                try {
                    String datumOtpreme = json.getString("Sent");
                    Date dateOtpreme = stringToDateTime(datumOtpreme);
                    Integer electronicID = json.getInt("ElectronicId");
                    String statusID = json.getString("StatusId");
                    log = new Log(IDDok, 0, 1, null, datumSlanja, dateOtpreme, 0, response, electronicID, statusID);
                    Controller.getInstance().updateLog(log);
                } catch (JSONException | ParseException ex) {                  
                    JSONObject error = json.getJSONObject("ValidationError");
                    String message = error.getString("Messages");
                    message = message.replace("[", "");
                    message = message.replace("]", "");
                    log = new Log(IDDok, 0, 0, null, datumSlanja, null, 1, message, null, null);
                    Controller.getInstance().updateLog(log);
                    Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (JSONException ex) {
                Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
                log = new Log(IDDok, 0, 0, null, datumSlanja, null, -6, "Povratna informacija sa servisa nije u JSON formatu", null, null);
                Controller.getInstance().updateLog(log);
            }
            System.out.println(response);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
            log = new Log(IDDok, 0, 0, null, new Date(), null, -5, "Pristup servisu nije moguc", null, null);
            Controller.getInstance().updateLog(log);
        }
    }

    private String readXML(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        String xmlDoc = "";
        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            xmlDoc = toString(document);
            return xmlDoc;
        } catch (ParserConfigurationException ex) {
            MakeErrorFile.makeTxtErrorFile(ex.getLocalizedMessage());
            throw new ParserConfigurationException(ex.getMessage());
        } catch (SAXException ex) {
            MakeErrorFile.makeTxtErrorFile(ex.getLocalizedMessage());
            throw new SAXException(ex.getMessage());
        } catch (IOException ex) {
            MakeErrorFile.makeTxtErrorFile(ex.getLocalizedMessage());
            throw new IOException(ex.getMessage());
        }

    }

    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            MakeErrorFile.makeTxtErrorFile(ex.getLocalizedMessage());
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    private String createJSONRequest(String xml) {
        String requestJson = "";
        try {
            /*Gson gson = new Gson();
            RequestObject ro = new RequestObject("ivona", "nikolic", "insoft", "Test-002", escape(xml));
            String json = gson.toJson(ro);*/
            JSONObject json = new JSONObject();
            json.put("Username", String.valueOf(params.get(0).getUserName()));
            json.put("Password", params.get(0).getPassword());
            json.put("CompanyId", params.get(0).getCompanyID());
            json.put("SoftwareId", params.get(0).getSoftwareID());
            json.put("File", xml);
            requestJson = json.toString();
            System.out.println(requestJson);

        } catch (JSONException ex) {
            MakeErrorFile.makeTxtErrorFile(ex.getLocalizedMessage());
            Logger.getLogger(HttpRequest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return requestJson;
    }

    private Date stringToDateTime(String datumOtpreme) throws ParseException {
        String[] onT = datumOtpreme.split("T", 0);

        String dateFinal = "";
        dateFinal = dateFinal.concat(onT[0]).concat(" ").concat(onT[1].substring(0, 5));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date d = sdf.parse(dateFinal);
        return d;
    }

    public LinkedList<String> getPutanje() {
        return putanje;
    }

    public void setPutanje(LinkedList<String> putanje, int iddok) {
        try {
            this.putanje = putanje;
        } catch (Exception e) {
            Controller.getInstance().updateLog(new Log(iddok, 0, 0, null, new Date(), null, -7, "Unable to set putanje", null, null));
        }
    }

    public LinkedList<Pristup> getParams() {
        return params;
    }

    public void setParams(LinkedList<Pristup> params, int iddok) {
        try {
            this.params = params;
        } catch (Exception e) {
            Controller.getInstance().updateLog(new Log(iddok, 0, 0, null, new Date(), null, -7, "Unable to set params", null, null));
        }
    }

}
