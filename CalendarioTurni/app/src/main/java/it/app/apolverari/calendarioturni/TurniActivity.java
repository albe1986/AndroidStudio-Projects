package it.app.apolverari.calendarioturni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class TurniActivity extends AppCompatActivity {

    private HTMLCalendar calendar;
    private WebView calendarioHTML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turni);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendarioHTML = (WebView) findViewById(R.id.calendarioHTML);
        calendar = new HTMLCalendar("Maggio", 2016);
        calendar.generateHTML();
        String HTML = calendar.getHTML();
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
