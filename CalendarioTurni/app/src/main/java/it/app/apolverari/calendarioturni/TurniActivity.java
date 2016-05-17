package it.app.apolverari.calendarioturni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        String HTML = HTMLCalendar.test();
        calendarioHTML = (WebView) findViewById(R.id.calendarioHTML);
        calendarioHTML.setWebViewClient(new WebViewClient());
        calendarioHTML.getSettings().setJavaScriptEnabled(true);
        calendarioHTML.getSettings().setLoadWithOverviewMode(true);
        //calendarioHTML.getSettings().setUseWideViewPort(true);
        calendarioHTML.getSettings().setBuiltInZoomControls(true);
        calendarioHTML.loadDataWithBaseURL("blarg://ignored", HTML, "text/html", "utf-8", "");

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
