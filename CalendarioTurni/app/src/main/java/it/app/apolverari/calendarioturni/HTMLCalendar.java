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
    private String styleAndJavascript = "<head>\n" +
                                        "<style>\n" +
                                        ".cal_calendar\n" +
                                        "{\n" +
                                        "        border:3px solid;\n" +
                                        "        padding:1px;\n" +
                                        "        margin:auto;\n" +
                                        "        border-color: #9fc0fc;\n" +
                                        "        border-style: outset;\n" +
                                        "        align: left;" +
                                        "}\n" +
                                        ".cal_calendar td\n" +
                                        "{\n" +
                                        "        border:2px solid;\n" +
                                        "        border-style: inset;\n" +
                                        "        background-color:#dae0ff;\n" +
                                        "}\n" +
                                        "\n" +
                                        ".cal_calendar_cell\n" +
                                        "{\n" +
                                        "        border:2px solid;\n" +
                                        "        padding:1px;\n" +
                                        "        border-color: #9fc0fc;\n" +
                                        "        border-style: outset;\n" +
                                        "        width: 100%;\n" +
                                        "        height: 100%;\n" +
                                        "}\n" +
                                        "\n" +
                                        ".cal_calendar_cell td\n" +
                                        "{\n" +
                                        "        border: hidden;\n" +
                                        "        border-color: #9fc0fc;\n" +
                                        "        font-size: 13px;\n" +
                                        "        background-color:#dae0ff;\n" +
                                        "}\n" +
                                        "</style>\n" +
                                        "</head>\n" +
                                        "<script type=\"text/javascript\">\n" +
                                        "    function pressDay(obj){\n" +
                                        "        obj.setAttribute(\"style\", \"border-style:inset\");\n" +
                                        "        setInterval(function(obj){\n" +
                                        "            obj.setAttribute(\"style\", \"border-style:outset\");\n" +
                                        "        }, 1000);" +
                                        "    }\n" +
                                        "</script>";
    private String HTML = "";
    private String openHTML =   "<html>" +
                                "<body style=\"font-family:sans-serif;\">" +
                                    styleAndJavascript +
                                "<div>";
    private String header;
    private String table =  "<tr>\n" +
                            "  <td style=\"font-size:17px;color:#303f9f;\"><b>LUN</b></td>\n" +
                            "  <td style=\"font-size:17px;color:#303f9f;\"><b>MAR</b></td>\n" +
                            "  <td style=\"font-size:17px;color:#303f9f;\"><b>MER</b></td>\n" +
                            "  <td style=\"font-size:17px;color:#303f9f;\"><b>GIO</b></td>\n" +
                            "  <td style=\"font-size:17px;color:#303f9f;\"><b>VEN</b></td>\n" +
                            "  <td style=\"font-size:17px;color:#303f9f;\"><b>SAB</b></td>\n" +
                            "  <td style=\"font-size:17px;color:#BF1313;\"><b>DOM</b></td>\n" +
                            "</tr>";

    private String closeHTML =  "</table>\n" +
                                "</div>\n" +
                                "</body>\n" +
                                "</html>";

    public HTMLCalendar(String month, Integer year){
        setUpMonthsMap();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(year, months.get(month), 1);
        header =    "<header align=\"center\">\n" +
                    "<h2 style=\"font-size:35px;color:#303f9f;\">" +
                    month + " " + String.valueOf(year) +
                    "</h2>\n" +
                    "</header>" +
                    "<table class=\"cal_calendar\" >";
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

    public void generateHTML(HashMap<Integer, String> turniGG){
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
        String turnoCol = "#e68a09;";
        for (int i = 0; i<blankDays; i++){
            firstWeekTR += "<td></td>";
        }
        for (int t = 1; t<8-blankDays; t++){
            if (turniGG.get(t) != null){
                if (turniGG.get(t).equals("R")) {
                    turnoCol = "#FF0000;";
                } else {
                    turnoCol = "#e68a09;";
                }
            }
            firstWeekTR += "<td align=\"center\">" +
                    "<table onclick=\"pressDay(this)\"class=\"cal_calendar_cell\" >" +
                    "<tr>" +
                    "<td style=\"font-weight: bold;color:#303f9f;\" align=\"right\">" +
                    String.valueOf(t) +
                    "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"font-weight: bold;color:" + turnoCol + "\">" +
                    (turniGG.get(t) != null ? turniGG.get(t): "///") +
                    "</td>" +
                    "</tr>" +
                    "</table></td>";
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
                    if (turniGG.get(day) != null){
                        if (turniGG.get(day).equals("R")) {
                            turnoCol = "#FF0000;";
                        } else {
                            turnoCol = "#e68a09;";
                        }
                    }
                    week += "<td align=\"center\">" +
                            "<table onclick=\"pressDay(this)\"class=\"cal_calendar_cell\" >" +
                            "<tr>" +
                            "<td style=\"font-weight: bold;;color:#303f9f;\" align=\"right\">" +
                            String.valueOf(day) +
                            "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td style=\"font-weight: bold;color:" + turnoCol + "\">" +
                            (turniGG.get(day) != null ? turniGG.get(day): "///") +
                            "</td>" +
                            "</tr>" +
                            "</table></td></tr>";
                } else {
                    week += "<td></td></tr>";
                }
                HTML += week;
                week = "<tr>";
                if (j==27) lastDay = day;
            } else {
                if (day<=monthDays) {
                    if (turniGG.get(day) != null){
                        if (turniGG.get(day).equals("R")) {
                            turnoCol = "#FF0000;";
                        } else {
                            turnoCol = "#e68a09;";
                        }
                    }
                    week += "<td align=\"center\">" +
                            "<table onclick=\"pressDay(this)\"class=\"cal_calendar_cell\" >" +
                            "<tr>" +
                            "<td style=\"font-weight: bold;;color:#303f9f;\" align=\"right\">" +
                            String.valueOf(day) +
                            "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td style=\"font-weight: bold;color:" + turnoCol + "\">" +
                            (turniGG.get(day) != null ? turniGG.get(day): "///") +
                            "</td>" +
                            "</tr>" +
                            "</table></td>";
                } else {
                    week += "<td></td>";
                }
            }
            day++;
        }

        String lastWeekTR = "<tr>";
        int lastWeekDays = monthDays - lastDay;
        for (int k = 0; k<lastWeekDays; k++){
            if (turniGG.get(lastDay+1) != null){
                if (turniGG.get(lastDay+1).equals("R")) {
                    turnoCol = "#FF0000;";
                } else {
                    turnoCol = "#e68a09;";
                }
            }
            lastWeekTR += "<td align=\"center\">" +
                    "<table onclick=\"pressDay(this)\"class=\"cal_calendar_cell\" >" +
                    "<tr>" +
                    "<td style=\"font-weight: bold;;color:#303f9f;\" align=\"right\">" +
                    String.valueOf(lastDay+1) +
                    "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"font-weight: bold;color:" + turnoCol + "\">" +
                    (turniGG.get(lastDay+1) != null ? turniGG.get(lastDay+1): "///") +
                    "</td>" +
                    "</tr>" +
                    "</table></td>";
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
