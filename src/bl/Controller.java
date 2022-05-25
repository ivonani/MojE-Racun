/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import com.MakeErrorFile;
import dbb.DBBroker;
import java.sql.SQLException;
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
public class Controller {

    private static Controller instance;
    private DBBroker dbb;
    private Log log;

    private Controller() {
        dbb = new DBBroker();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public LinkedList<Log> returnLog() {
        LinkedList<Log> logs = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();
            logs = dbb.returnLog();

            dbb.closeConnection();

        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Nepravilni podaci u tabeli erLog\n" + ex.getLocalizedMessage());
            return null;
        }

        return logs;
    }
    
    public LinkedList<Dokument> returnDokument(int id) {
        LinkedList<Dokument> doks = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            doks = dbb.returnDokument(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erDokument", null, null);
            updateLog(log);
            return null;
        }

        return doks;
    }
    
    public LinkedList<Narudzbina> returnNarudzbine(int id) {
        LinkedList<Narudzbina> nars = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            nars = dbb.returnNarudzbine(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erNarudzbine", null, null);
            updateLog(log);
            return null;
        }

        return nars;
    }
    
    public LinkedList<Otpremnica> returnOtpremnica(int id) {
        LinkedList<Otpremnica> otps = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            otps = dbb.returnOtpremnice(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erOtpremnice", null, null);
            updateLog(log);
            return null;
        }

        return otps;
    }
    
    public LinkedList<Avans> returnAvansi(int id) {
        LinkedList<Avans> avns = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            avns = dbb.returnAvansi(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erAvansi", null, null);
            updateLog(log);
            return null;
        }

        return avns;
    }
    
    public LinkedList<DokumentPDF> returnDokumentPDF(int id) {
        LinkedList<DokumentPDF> doks = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            doks = dbb.returnDokumentPDF(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erDokumentPDF", null, null);
            updateLog(log);
            return null;
        }
        return doks;
    }
    
    public LinkedList<Ugovor> returnUgovori(int id) {
        LinkedList<Ugovor> ugrs = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            ugrs = dbb.returnUgovori(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erUgovor", null, null);
            updateLog(log);
            return null;
        }
        return ugrs;
    }
    
    public LinkedList<Dobavljac> returnDobavljaci(int id) {
        LinkedList<Dobavljac> dobs = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            dobs = dbb.returnDobavljaci(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erDobavljac", null, null);
            updateLog(log);
            return null;
        }
        return dobs;
    }
    
    public LinkedList<Kupac> returnKupci(int id) {
        LinkedList<Kupac> kups = new LinkedList<>();
        
        dbb.loadDriver();

        try {
            dbb.openConnection();

            kups = dbb.returnKupci(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erKupac", null, null);
            updateLog(log);
            return null;
        }
        return kups;
    }
    
    public LinkedList<Isporuka> returnIsporuke(int id) {
        LinkedList<Isporuka> isps = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            isps = dbb.returnIsporuka(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erIsporuke", null, null);
            updateLog(log);
            return null;
        }
        return isps;
    }
    
    public LinkedList<Footer> returnFooteri(int id) {
        LinkedList<Footer> foots = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            foots = dbb.returnFooter(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erFooter", null, null);
            updateLog(log);
            return null;
        }
        return foots;
    }
    
    public LinkedList<PaymentMeans> returnPaymentMeans(int id) {
        LinkedList<PaymentMeans> pms = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            pms = dbb.returnPaymentMeans(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erPaymentMeans", null, null);
            updateLog(log);
            return null;
        }
        return pms;
    }
    
    public LinkedList<TaxSubtotal> returnTaxSubtotal(int id) {
        LinkedList<TaxSubtotal> tss = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            tss = dbb.returnTaxSubtotal(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erTaxSubtotal", null, null);
            updateLog(log);
            return null;
        }
        return tss;
    }
    
    public LinkedList<Stavke> returnStavke(int id) {
        LinkedList<Stavke> stvs = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            stvs = dbb.returnStavke(id);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erStavke", null, null);
            updateLog(log);
            return null;
        }
        return stvs;
    }
    
    public LinkedList<StavkeRabat> returnStavkeRabat(int id, int idStavke) {
        LinkedList<StavkeRabat> srs = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            srs = dbb.returnStavkeRabat(id, idStavke);

            dbb.closeConnection();

        } catch (SQLException ex) {
            log = new Log(id, 0, 0, null, new Date(), null, -3, "Nepravilni podaci u tabeli erStavkeRabat", null, null);
            updateLog(log);
            return null;
        }
        return srs;
    }
    
    public boolean updateLog(Log res) {
        boolean feedback = false;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            feedback = dbb.updateLog(res);

            dbb.closeConnection();
        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to updateLog\n" + ex.getLocalizedMessage());
            feedback = false;
        }

        return feedback;
    }
    
    public boolean updateLogResponse(Log res) {
        boolean feedback = false;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            feedback = dbb.updateLogResponse(res);

            dbb.closeConnection();
        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to updateLogResponse\n" + ex.getLocalizedMessage());
            feedback = false;
        }

        return feedback;
    }
    
    public boolean updateSeafor(String IDRacunara) {
        boolean feedback = false;
        dbb.loadDriver();

        try {
            dbb.openConnection();

            feedback = dbb.updateSemafor(IDRacunara);

            dbb.closeConnection();
        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to update Semafor\n" + ex.getLocalizedMessage());
            feedback = false;
        }

        return feedback;
    }
    
    public LinkedList<String> returnPutanjeXML() {
        LinkedList<String> put = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            put = dbb.returnPutanjeXML();

            dbb.closeConnection();

        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to return Putanje XML\n" + ex.getLocalizedMessage());
            return null;
        }
        return put;
    }
    
    public LinkedList<Pristup> executeProcedure() {
        LinkedList<Pristup> params = new LinkedList<>();
        dbb.loadDriver();

        try {
            dbb.openConnection();

            params = dbb.executeProcedure();

            dbb.closeConnection();

        } catch (SQLException ex) {
            MakeErrorFile.makeTxtErrorFile("Unable to return execute Procedure\n" + ex.getLocalizedMessage());
            return null;
        }
        return params;
    }
    
    public void setDBName(String serverName, String dbName) {
        dbb.setNameDB(dbName);
        dbb.setServerName(serverName);
    }

    public DBBroker getDbb() {
        return dbb;
    }

    public void setDbb(DBBroker dbb) {
        this.dbb = dbb;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public void setIDDok(int IDDok) {
        try {
            dbb.setIDDok(IDDok);
        } catch (Exception e) {
            MakeErrorFile.makeTxtErrorFile("Unable to set IDDok\n" + e.getLocalizedMessage());
        }
        
    }    
}
