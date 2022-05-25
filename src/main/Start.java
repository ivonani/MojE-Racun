/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import bl.Controller;
import com.CreateXML;
import com.HttpRequest;
import com.MakeErrorFile;
import com.ReadTxtFile;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model2.Log;
import model2.Pristup;

/**
 *
 * @author ivona
 */
public class Start extends TimerTask {

    CreateXML cr;
    HttpRequest hr;
    ReadTxtFile rtf;
    LinkedList<Log> logs;
    private String IDRacunara;
    private String kod;
    private String slanjeXML;

    @Override
    public void run() {
        try {
            System.out.println("Started");
            rtf = new ReadTxtFile();
            rtf.readDBName();
            LinkedList<String> putanje = Controller.getInstance().returnPutanjeXML();
            LinkedList<Pristup> params = Controller.getInstance().executeProcedure();
            logs = Controller.getInstance().returnLog();

            for (Log log : logs) {
                if (log.getZaSlanje() == 1) {
                    cr = new CreateXML();
                    hr = new HttpRequest();
                    cr.setPutanje(putanje, log.getIDDOK());
                    hr.setPutanje(putanje, log.getIDDOK());
                    hr.setParams(params, log.getIDDOK());
                    Controller.getInstance().setIDDok(log.getIDDOK());

                    String naziv = cr.create(log.getIDDOK());
                    if (naziv != null) {
                        if (slanjeXML.equals("D")) {
                            hr.httpRequest(naziv, log.getIDDOK());
                        } else {
                            Controller.getInstance().updateLogResponse(new Log(log.getIDDOK(), 0, 0, null, null, null, 0, putanje.get(0) + naziv + ".xml", Integer.MIN_VALUE, ""));
                        }
                    } else {
                        Controller.getInstance().updateLog(new Log(log.getIDDOK(), 0, 0, null, new Date(), null, -3, "Bezuspesno slanje httpRequest-a, naziv PDFa je null", null, null));
                        throw new Exception();
                    }
                }
            }
            Controller.getInstance().updateSeafor(IDRacunara);
        } catch (Exception ex) {
            MakeErrorFile.makeTxtErrorFile(ex.getLocalizedMessage());
        }
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getIDRacunara() {
        return IDRacunara;
    }

    public void setIDRacunara(String IDRacunara) {
        this.IDRacunara = IDRacunara;
    }

    public String getSlanjeXML() {
        return slanjeXML;
    }

    public void setSlanjeXML(String slanjeXML) {
        this.slanjeXML = slanjeXML;
    }

}// && log.getIDDOK() == 4819
