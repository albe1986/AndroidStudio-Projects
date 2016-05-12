package it.app.apolverari.calendarioturni;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by a.polverari on 11/05/2016.
 */
public class HTMLCalendar {

    private HashMap<String, Integer> months;
    private String month;
    private Integer monthDays;
    private Integer firstDayOfWeek;
    private Integer dayOfTheWeek;
    private String HTML = "";
    private String openHTML =   "<html>" +
                                "<div style=\"font-family:fantasy; font-size:x-large;\">";
    private String header;
    private String table =  "  <tr>\n" +
                            "    <td>LUN</td>\n" +
                            "    <td>MAR</td>\n" +
                            "    <td>MER</td>\n" +
                            "    <td>GIO</td>\n" +
                            "    <td>VEN</td>\n" +
                            "    <td>SAB</td>\n" +
                            "    <td>DOM</td>\n" +
                            "  </tr>";

    private String closeHTML =  "</table>\n" +
                                "</div>\n" +
                                "</body>\n" +
                                "</html>";

    public HTMLCalendar(String month, Integer year){
        setUpMonthsMap();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(year, months.get(month), 1);
        header =    "<header align=\"center\">\n" +
                    "<h2>" +
                    month + " " + String.valueOf(year) +
                    "</h2>\n" +
                    "</header>" +
                    "<table align=\"center\" border=\"2px\">";
        this.dayOfTheWeek = gc.getTime().getDay();
        switch (dayOfTheWeek){
            case 1:
                this.firstDayOfWeek = 1;
                break;
            case 2:
                this.firstDayOfWeek = 7;
                break;
            case 3:
                this.firstDayOfWeek = 6;
                break;
            case 4:
                this.firstDayOfWeek = 5;
                break;
            case 5:
                this.firstDayOfWeek = 4;
                break;
            case 6:
                this.firstDayOfWeek = 3;
                break;
            case 0:
                this.firstDayOfWeek = 2;
                break;
            default:
                this.firstDayOfWeek = 1;
                break;
        }
        this.month = month;
        this.monthDays = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void setUpMonthsMap(){
        months = new HashMap<>();
        months.put("Gennaio", 0);
        months.put("Febbraio", 1);
        months.put("Marzo", 2);
        months.put("Aprile", 3);
        months.put("Maggio", 4);
        months.put("Giugno", 5);
        months.put("Luglio", 6);
        months.put("Agosto", 7);
        months.put("Settembre", 8);
        months.put("Ottobre", 9);
        months.put("Novembre", 10);
        months.put("Dicembre", 11);
    }

    public void generateHTML(){
        HTML = openHTML + header;
        HTML += table;
        Integer blankDays;
        switch (firstDayOfWeek) {
            case 2:
                blankDays = 6;
                break;
            case 3:
                blankDays = 5;
                break;
            case 4:
                blankDays = 4;
                break;
            case 5:
                blankDays = 3;
                break;
            case 6:
                blankDays = 2;
                break;
            case 7:
                blankDays = 1;
                break;
            case 8:
                blankDays = 0;
                break;
            default:
                blankDays = 0;
        }

        String firstWeekTR = "<tr>";

        for (int i = 0; i<blankDays; i++){
            firstWeekTR += "<td></td>";
        }
        for (int t = 1; t<8-blankDays; t++){
            firstWeekTR += "<td>" + String.valueOf(t) + "</td>";
        }

        HTML += firstWeekTR + "</tr>";

        int day = firstDayOfWeek;
        int lastDay = 0;
        String week = "<tr>";
        for (int j = 0; j<monthDays; j++){
            if (blankDays == 0 && day<=7) {
                day++;
                continue;
            }
            if (j == 6 || j == 13 || j == 20 || j == 27){
                if (day<=monthDays) {
                    week += "<td>" + String.valueOf(day) + "</td>" + "</tr>";
                } else {
                    week += "<td></td></tr>";
                }
                HTML += week;
                week = "<tr>";
                if (j==27) lastDay = day;
            } else {
                if (day<=monthDays) {
                    week += "<td>" + String.valueOf(day) + "</td>";
                } else {
                    week += "<td></td>";
                }
            }
//            if (day<monthDays) {
//                day++;
//            }
            day++;
        }

        String lastWeekTR = "<tr>";
        int lastWeekDays = monthDays - lastDay;
        for (int k = 0; k<lastWeekDays; k++){
            lastWeekTR += "<td>" + String.valueOf(lastDay+1) + "</td>";
            lastDay++;
        }
        if (lastWeekDays > 0) {
            for (int z = 0; z < 7 - lastWeekDays; z++) {
                lastWeekTR += "<td></td>";
            }
        }
        HTML += lastWeekTR + "</tr>";
        HTML += closeHTML;
    }



    public String getHTML(){
        return this.HTML;
    }
}
