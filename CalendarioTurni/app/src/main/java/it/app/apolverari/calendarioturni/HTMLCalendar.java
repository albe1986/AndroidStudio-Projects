package it.app.apolverari.calendarioturni;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by a.polverari on 11/05/2016.
 */
public class HTMLCalendar {

    public static String test(){
        String HTMLTest = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Javascript Calendar</title>\n" +
                "        <style>\n" +
                "            .cal_calendar\n" +
                "            {\n" +
                "                    border:1px solid black;\n" +
                "                    padding:1px;\n" +
                //"                    background-color:#4594ff;\n" +
                "                    width:300px;\n" +
                //"                    margin:auto;\n" +
                "                    height:246px;\n" +
                "                    background-image:url('http://www.webestools.com/ftp/ybouane/scripts_tutorials/javascript/calendar/bak-300-250.gif');\n" +
                "            }\n" +
                "            .cal_calendar th\n" +
                "            {\n" +
                "                    border:1px solid black;\n" +
                "                    background-color:#ffffff;\n" +
                "                            width:36px;\n" +
                "            }\n" +
                "            .cal_calendar td\n" +
                "            {\n" +
                "                    border:1px solid black;\n" +
                "                    background-color:#ffffff;\n" +
                "                    text-align:center;\n" +
                "                            width:36px;\n" +
                "                            height:36px;\n" +
                "            }\n" +
                "            .cal_today\n" +
                "            {\n" +
                "                    color:#ff0000;\n" +
                "                            font-weight:bold;\n" +
                "            }\n" +
                "            .cal_days_bef_aft\n" +
                "            {\n" +
                "                    color:#5a779e;\n" +
                "            }\n" +
                "        </style>        \n" +
                "        <script type=\"text/javascript\">\n" +
                "            function setStyle(id,style,value)\n" +
                "            {\n" +
                "                id.style[style] = value;\n" +
                "            }\n" +
                "            function opacity(el,opacity)\n" +
                "            {\n" +
                "                    setStyle(el,\"filter:\",\"alpha(opacity=\"+opacity+\")\");\n" +
                "                    setStyle(el,\"-moz-opacity\",opacity/100);\n" +
                "                    setStyle(el,\"-khtml-opacity\",opacity/100);\n" +
                "                    setStyle(el,\"opacity\",opacity/100);\n" +
                "            }\n" +
                "            function calendar(turni, day, month, year)\n" +
                "            {\n" +
                "                    var turniObj = JSON.parse(turni);\n" +
                "                    var date = new Date();\n" +
                "                    date.setMonth(month);\n" +
                "                    date.setYear(year);\n" +
                "                    date.setDate(day);\n" +
                "                    //var day = date.getDate();\n" +
                "                    //var month = date.getMonth();\n" +
                "                    //var year = date.getYear();\n" +
                "                    if(year<=200)\n" +
                "                    {\n" +
                "                            year += 1900;\n" +
                "                    }\n" +
                "                    months = new Array('January', 'February', 'March', 'April', 'May', 'June', 'Jully', 'August', 'September', 'October', 'November', 'December');\n" +
                "                    days_in_month = new Array(31,28,31,30,31,30,31,31,30,31,30,31);\n" +
                "                    if(year%4 == 0 && year!=1900)\n" +
                "                    {\n" +
                "                            days_in_month[1]=29;\n" +
                "                    }\n" +
                "                    total = days_in_month[month];\n" +
                "                    var date_today = day+' '+months[month]+' '+year;\n" +
                "                    beg_j = date;\n" +
                "                    beg_j.setDate(1);\n" +
                "                    if(beg_j.getDate()==2)\n" +
                "                    {\n" +
                "                            beg_j=setDate(0);\n" +
                "                    }\n" +
                "                    beg_j = beg_j.getDay();\n" +
                "                    document.write('<table class=\"cal_calendar\" onload=\"opacity(document.getElementById(\\'cal_body\\'),20);\"><tbody id=\"cal_body\"><tr><th colspan=\"7\">'+date_today+'</th></tr>');\n" +
                "                    document.write('<tr class=\"cal_d_weeks\"><th>DOM</th><th>LUN</th><th>MAR</th><th>MER</th><th>GIO</th><th>VEN</th><th>SAB</th></tr><tr>');\n" +
                "                    week = 0;\n" +
                "                    for(i=1;i<=beg_j;i++)\n" +
                "                    {\n" +
                "                            document.write('<td class=\"cal_days_bef_aft\">'+(days_in_month[month-1]-beg_j+i)+'</td>');\n" +
                "                            week++;\n" +
                "                    }\n" +
                "                    for(i=1;i<=total;i++)\n" +
                "                    {\n" +
                "                            if(week==0)\n" +
                "                            {\n" +
                "                                    document.write('<tr>');\n" +
                "                            }\n" +
                "                            if(day==i)\n" +
                "                            {\n" +
                "                                    document.write('<td class=\"cal_today\">' + i + '' + turniObj[i] + '</td>');\n" +
                "                            }\n" +
                "                            else\n" +
                "                            {\n" +
                "                                    document.write('<td>' + i + '<br/><b>' + turniObj[i] +'</b></td>');\n" +
                "                            }\n" +
                "                            week++;\n" +
                "                            if(week==7)\n" +
                "                            {\n" +
                "                                    document.write('</tr>');\n" +
                "                                    week=0;\n" +
                "                            }\n" +
                "                    }\n" +
                "                    for(i=1;week!=0;i++)\n" +
                "                    {\n" +
                "                            document.write('<td class=\"cal_days_bef_aft\">'+i+'</td>');\n" +
                "                            week++;\n" +
                "                            if(week==7)\n" +
                "                            {\n" +
                "                                    document.write('</tr>');\n" +
                "                                    week=0;\n" +
                "                            }\n" +
                "                    }\n" +
                "                    document.write('</tbody></table>');\n" +
                "                    opacity(document.getElementById('cal_body'),70);\n" +
                "                    return true;\n" +
                "            }        \n" +
                "        </script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <script type=\"text/javascript\">";

        HTMLTest += "calendar(' {\"1\":\"412\",\"2\":\"567\"} ', 20, 4, 2016);";

        HTMLTest += "        </script>\n" +
                "    </body>\n" +
                "</html>";
        return HTMLTest;
    }

//    private HashMap<String, Integer> months;
//    private String month;
//    private Integer monthDays;
//    private Integer firstDayOfWeek;
//    private Integer dayOfTheWeek;
//    private String HTML = "";
//    private String openHTML =   "<html>" +
//                                "<div style=\"font-family:fantasy; font-size:x-large;\">";
//    private String header;
//    private String table =  "  <tr>\n" +
//                            "    <td>LUN</td>\n" +
//                            "    <td>MAR</td>\n" +
//                            "    <td>MER</td>\n" +
//                            "    <td>GIO</td>\n" +
//                            "    <td>VEN</td>\n" +
//                            "    <td>SAB</td>\n" +
//                            "    <td>DOM</td>\n" +
//                            "  </tr>";
//
//    private String closeHTML =  "</table>\n" +
//                                "</div>\n" +
//                                "</body>\n" +
//                                "</html>";
//
//    public HTMLCalendar(String month, Integer year){
//        setUpMonthsMap();
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.set(year, months.get(month), 1);
//        header =    "<header align=\"center\">\n" +
//                    "<h2>" +
//                    month + " " + String.valueOf(year) +
//                    "</h2>\n" +
//                    "</header>" +
//                    "<table align=\"center\" border=\"2px\">";
//        this.dayOfTheWeek = gc.getTime().getDay();
//        switch (dayOfTheWeek){
//            case 1:
//                this.firstDayOfWeek = 1;
//                break;
//            case 2:
//                this.firstDayOfWeek = 7;
//                break;
//            case 3:
//                this.firstDayOfWeek = 6;
//                break;
//            case 4:
//                this.firstDayOfWeek = 5;
//                break;
//            case 5:
//                this.firstDayOfWeek = 4;
//                break;
//            case 6:
//                this.firstDayOfWeek = 3;
//                break;
//            case 0:
//                this.firstDayOfWeek = 2;
//                break;
//            default:
//                this.firstDayOfWeek = 1;
//                break;
//        }
//        this.month = month;
//        this.monthDays = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
//    }
//
//    private void setUpMonthsMap(){
//        months = new HashMap<>();
//        months.put("Gennaio", 0);
//        months.put("Febbraio", 1);
//        months.put("Marzo", 2);
//        months.put("Aprile", 3);
//        months.put("Maggio", 4);
//        months.put("Giugno", 5);
//        months.put("Luglio", 6);
//        months.put("Agosto", 7);
//        months.put("Settembre", 8);
//        months.put("Ottobre", 9);
//        months.put("Novembre", 10);
//        months.put("Dicembre", 11);
//    }
//
//    public void generateHTML(){
//        HTML = openHTML + header;
//        HTML += table;
//        Integer blankDays;
//        switch (firstDayOfWeek) {
//            case 2:
//                blankDays = 6;
//                break;
//            case 3:
//                blankDays = 5;
//                break;
//            case 4:
//                blankDays = 4;
//                break;
//            case 5:
//                blankDays = 3;
//                break;
//            case 6:
//                blankDays = 2;
//                break;
//            case 7:
//                blankDays = 1;
//                break;
//            case 8:
//                blankDays = 0;
//                break;
//            default:
//                blankDays = 0;
//        }
//
//        String firstWeekTR = "<tr>";
//
//        for (int i = 0; i<blankDays; i++){
//            firstWeekTR += "<td></td>";
//        }
//        for (int t = 1; t<8-blankDays; t++){
//            firstWeekTR += "<td>" + String.valueOf(t) + "</td>";
//        }
//
//        HTML += firstWeekTR + "</tr>";
//
//        int day = firstDayOfWeek;
//        int lastDay = 0;
//        String week = "<tr>";
//        for (int j = 0; j<monthDays; j++){
//            if (blankDays == 0 && day<=7) {
//                day++;
//                continue;
//            }
//            if (j == 6 || j == 13 || j == 20 || j == 27){
//                if (day<=monthDays) {
//                    week += "<td>" + String.valueOf(day) + "</td>" + "</tr>";
//                } else {
//                    week += "<td></td></tr>";
//                }
//                HTML += week;
//                week = "<tr>";
//                if (j==27) lastDay = day;
//            } else {
//                if (day<=monthDays) {
//                    week += "<td>" + String.valueOf(day) + "</td>";
//                } else {
//                    week += "<td></td>";
//                }
//            }
////            if (day<monthDays) {
////                day++;
////            }
//            day++;
//        }
//
//        String lastWeekTR = "<tr>";
//        int lastWeekDays = monthDays - lastDay;
//        for (int k = 0; k<lastWeekDays; k++){
//            lastWeekTR += "<td>" + String.valueOf(lastDay+1) + "</td>";
//            lastDay++;
//        }
//        if (lastWeekDays > 0) {
//            for (int z = 0; z < 7 - lastWeekDays; z++) {
//                lastWeekTR += "<td></td>";
//            }
//        }
//        HTML += lastWeekTR + "</tr>";
//        HTML += closeHTML;
//    }
//
//
//
//    public String getHTML(){
//        return this.HTML;
//    }
}
