/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import bl.Controller;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import model2.Log;

/**
 *
 * @author ivona
 */
public class ReadTxtFile {
    public void readDBName() {
        try {
        File file = new File("C:\\InSoft\\SysFirma.txt");
        String name = "";
        LinkedList<String> data = new LinkedList<>();
        
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                name = input.nextLine();
                data.add(name);
            }
        Controller.getInstance().setDBName(data.get(0), data.get(1));
        } catch (Exception e) {
            MakeErrorFile.makeTxtErrorFile(e.getLocalizedMessage());
            return;
        }
        
    }
}