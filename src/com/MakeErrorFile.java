/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author ivona
 */
public class MakeErrorFile {

    public static void makeTxtErrorFile(String error) {
        try {
            File file = new File("C:\\InSoft\\error.txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(file);
            
            pw.print(error);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
