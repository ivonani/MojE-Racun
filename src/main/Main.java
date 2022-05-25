/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.MakeErrorFile;

/**
 *
 * @author ivona
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String IDRacunara = "0001";
        String slanjeXML = "D";
        String kod = "21";

        if (args.length > 0) {
            IDRacunara = args[0];
        }
        if (args.length > 1) {
            slanjeXML = args[1];
        }
        if (args.length > 2) {
            kod = args[2];
        }

        Start s = new Start();
        s.setIDRacunara(IDRacunara);
        s.setSlanjeXML(slanjeXML);
        s.setKod(kod);
        s.run();
    }

}
