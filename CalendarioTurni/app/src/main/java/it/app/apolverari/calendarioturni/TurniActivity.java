package it.app.apolverari.calendarioturni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.HashMap;

import it.app.apolverari.db.DBManager;


public class TurniActivity extends AppCompatActivity {

    private DBManager db;
    private String agente;
    private Integer bday;
    private String bmonth;
    private String HTML;
    private Integer byear;
    private WebView calendarioHTML;
    private HashMap<String, Integer> months = new HashMap<>();
    private ArrayList<String> turniAgente = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turni);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DBManager(this);
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
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            agente = i.getStringExtra("agente");
            turniAgente = (ArrayList<String>) bundle.getSerializable("turniAgente");
            bday = i.getIntExtra("day", 0);
            bmonth = i.getStringExtra("month");
            byear = i.getIntExtra("year", 0);
        }

        HTML = MiscUtils.calcolaTurni(db, agente, turniAgente, months.get(bmonth), bday);

        calendarioHTML = (WebView) findViewById(R.id.calendarioHTML);
        calendarioHTML.setWebViewClient(new WebViewClient());
        //calendarioHTML.setWebChromeClient(new WebChromeClient());
        calendarioHTML.getSettings().setJavaScriptEnabled(true);
        //calendarioHTML.loadData(HTML, "text/html", "utf-8");
        calendarioHTML.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        calendarioHTML.getSettings().setDomStorageEnabled(true);
        calendarioHTML.loadDataWithBaseURL("blarg://ignored", HTML, "text/html", "utf-8", "");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
