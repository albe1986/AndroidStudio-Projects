package it.app.apolverari.calendarioturni;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import it.app.apolverari.db.DBManager;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by a.polverari on 03/05/2016.
 */
public class MiscUtils {

    private static String[] mesi = {"Gennaio",
            "Febbraio",
            "Marzo",
            "Aprile",
            "Maggio",
            "Giugno",
            "Luglio",
            "Agosto",
            "Settembre",
            "Ottobre",
            "Novembre",
            "Dicembre"};

    public static ArrayList readExcel(String path) throws IOException  {
        ArrayList results = new ArrayList();
        File inputWorkbook = new File(path);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            Sheet sheet = w.getSheet(1);
            for (int j = 1; j < sheet.getRows(); j++) {
                Cell[] row = sheet.getRow(j);
                Cell cell = row[1];
                if (cell.getType() != CellType.EMPTY) {
                    if (!cell.getContents().equals("") && !cell.getContents().equals("AGENTI")){
                        ArrayList<String> newRow = new ArrayList<>();
                        for (int i = 1; i<row.length; i++){
                            String cellContent = row[i].getContents();
                            if (!cellContent.equals("") && !cellContent.equals("#ERROR!")) {
                                newRow.add(cellContent);
                            }
                        }
                        results.add(newRow);
                    }
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static boolean saveExcelToDB(ArrayList results, DBManager db){
        boolean res = false;
        ArrayList firstRow = (ArrayList) results.get(0);
        String dataInizio = (String) firstRow.get(0);
        for (int i = 2; i<results.size(); i++){
            ArrayList<String> turniAgente = (ArrayList<String>) results.get(i);
            if (turniAgente.size() == 8) {
                res = db.save(turniAgente, String.valueOf(i-2), dataInizio);
            }
        }
        return res;
    }

    public static String calcolaTurni(DBManager db,
            String agente, ArrayList<String> turniAgente, Integer month, Integer bday){

        String HTML = "";
        Integer daysInMonth = 0;
        String[] turnoWeek = null;
        HashMap<Integer, ArrayList<String>> parzialiMesi = new HashMap<>();
        ArrayList<String> turnoParziale = new ArrayList<>();

        Integer curPos = Integer.parseInt(db.getPosAgente(agente));
        GregorianCalendar gc = new GregorianCalendar();

        int monthsToShow = 12-month;
        for (int m = 0; m<monthsToShow; m++){
            gc.set(2016, month, bday);
            daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
            ArrayList<String> turniMese = new ArrayList<>();
            HashMap<Integer, String> turniGiorni = new HashMap<>();

            int partSize = 0;
            int n = 1;
            if (m == 0) {
                n = bday;
                daysInMonth = daysInMonth - (bday - 1);
            }

            while(turniMese.size()<(daysInMonth)){
                turnoWeek = db.getTurnoByPos(String.valueOf(curPos));
                for (int t = 0;t<turnoWeek.length; t++){
                    turniMese.add(turnoWeek[t]);
                }
                curPos++;
            }

            if (turniMese.size()>daysInMonth){
                turnoParziale = new ArrayList<>();
                int idx = turniMese.size() - (turniMese.size()-daysInMonth);
                int limit = turniMese.size();
                for(int p = idx; p<limit; p++){
                    turnoParziale.add(turniMese.get(p));
                }
                for(int del = limit; del>daysInMonth; del--){
                    turniMese.remove(del-1);
                }
                parzialiMesi.put(month + 1, turnoParziale);
            }

            if (parzialiMesi.containsKey(month)){
                ArrayList<String> tmp = parzialiMesi.get(month);
                partSize = tmp.size();
                for (int part = 0; part<partSize; part++){
                    turniGiorni.put(part+1, tmp.get(part));
                }
                if (month!= 11) {
                    ArrayList<String> parFin = new ArrayList<>();
                    for (int s = turniMese.size() - partSize; s < turniMese.size(); s++) {
                        parFin.add(turniMese.get(s));
                    }
                    parFin.addAll(turnoParziale);
                    parzialiMesi.put(month + 1, parFin);
                }
            }

            for (int d = 0; d<daysInMonth; d++){
                if (m == 0) {
                    turniGiorni.put(n, turniMese.get(d));
                    n++;
                } else {
                    turniGiorni.put(d+partSize, turniMese.get(d));
                }
            }

            HTMLCalendar c = new HTMLCalendar(mesi[month], 2016);
            c.generateHTML(turniGiorni);
            HTML += c.getHTML();

            month++;
        }
        return HTML;
    }
}
