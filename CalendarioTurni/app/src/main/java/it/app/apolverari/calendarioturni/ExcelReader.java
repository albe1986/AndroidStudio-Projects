package it.app.apolverari.calendarioturni;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
                            if (!cellContent.equals("")) {
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
}
