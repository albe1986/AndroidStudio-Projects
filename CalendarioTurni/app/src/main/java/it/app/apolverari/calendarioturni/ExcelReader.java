package it.app.apolverari.calendarioturni;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import it.app.apolverari.db.DBManager;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by a.polverari on 03/05/2016.
 */
public class ExcelReader {

    public static ArrayList read(String path) throws IOException  {
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

    public static boolean saveToDB(ArrayList results, DBManager db){
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

    public static void calculate(ArrayList<String> turniAgente, ArrayList ordineTurni){
        ArrayList row = (ArrayList) ordineTurni.get(0);
        String dataInizio = (String) row.get(0);
        DateFormat format = new SimpleDateFormat("d-MMM-yy");
        Date dataIn = new Date();
        try {
            dataIn = format.parse(dataInizio);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar gc = new java.util.GregorianCalendar();
        gc.setTime(dataIn);
        gc.getTime();
    }
}
