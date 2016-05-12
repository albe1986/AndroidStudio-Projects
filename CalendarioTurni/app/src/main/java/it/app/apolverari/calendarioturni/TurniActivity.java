package it.app.apolverari.calendarioturni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.HashMap;


public class TurniActivity extends AppCompatActivity {

    private HTMLCalendar calendar;
    private WebView calendarioHTML;
    private HashMap<Integer,String> months = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turni);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        months.put(0,"Gennaio");
        months.put(1, "Febbraio");
        months.put(2, "Marzo");
        months.put(3, "Aprile");
        months.put(4, "Maggio");
        months.put(5, "Giugno");
        months.put(6, "Luglio");
        months.put(7, "Agosto");
        months.put(8, "Settembre");
        months.put(9, "Ottobre");
        months.put(10, "Novembre");
        months.put(11, "Dicembre");
        String HTML = "";
        for (int i = 0; i<11; i++){
            HTMLCalendar c = new HTMLCalendar(months.get(i), 2016);
            c.generateHTML();
            String tmp = c.getHTML();
            HTML += tmp;
        }
        calendarioHTML = (WebView) findViewById(R.id.calendarioHTML);
//        calendar = new HTMLCalendar("Dicembre", 2016);
//        calendar.generateHTML();
//        String HTML = calendar.getHTML();
        calendarioHTML.getSettings().getJavaScriptEnabled();
        calendarioHTML.loadData(HTML, "text/html", "UTF-8");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.export_turni, menu);
        getActionBar();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.export_pdf) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
