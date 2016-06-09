package it.app.apolverari.calendarioturni;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import it.app.apolverari.db.DBManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DBManager db;
    private Cursor crs;
    private TextView noTurni;
    private Button calcTurni;
    private LinearLayout layout;
    private Spinner giorno;
    private Spinner mese;
    private Spinner anno;
    private Spinner comboAgenti;
    private String arrayComboAgenti[];
    private ArrayList results = new ArrayList();
    private HashMap turniAgente = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DBManager(this);
        setUpViewsAndButtons();

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (checkTurni()){
            layout.removeView(noTurni);
            setUpComboDataInizio();
            setUpComboAgenti();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        if (id == R.id.navImport) {
            i = new Intent(getApplicationContext(), UploadActivity.class);
            startActivity(i);
        } else if (id == R.id.navImpostazioni) {
            i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.navCondividi) {

        } else if (id == R.id.navExit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpComboAgenti(){
        arrayComboAgenti = new String[results.size() - 2];
        for (int i = 2; i<results.size(); i++){
            ArrayList<String> row = (ArrayList<String>) results.get(i);
            if (row.size() == 9) {
                arrayComboAgenti[i - 2] = row.get(0);
                ArrayList<String> turni = new ArrayList<>();
                turni.add(row.get(1));
                turni.add(row.get(2));
                turni.add(row.get(3));
                turni.add(row.get(4));
                turni.add(row.get(5));
                turni.add(row.get(6));
                turni.add(row.get(7));
                turniAgente.put(row.get(0), turni);
            }
        }

        for (int j = 0; j<arrayComboAgenti.length; j++){
            if (arrayComboAgenti[j] == null){
                arrayComboAgenti[j] = "--!Record Errato rimosso!--";
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_item_layout, arrayComboAgenti);
        comboAgenti.setAdapter(adapter);
        comboAgenti.setVisibility(View.VISIBLE);
        calcTurni.setVisibility(View.VISIBLE);
    }

    private void setUpViewsAndButtons(){
        layout = (LinearLayout) findViewById(R.id.turniContent);
        noTurni = (TextView) findViewById(R.id.noTurni);
        comboAgenti = (Spinner) findViewById(R.id.comboAgenti);
        calcTurni = (Button) findViewById(R.id.calcTurni);
        comboAgenti = (Spinner) findViewById(R.id.comboAgenti);
        giorno = (Spinner) findViewById(R.id.giorno);
        mese = (Spinner) findViewById(R.id.mese);
        anno = (Spinner) findViewById(R.id.anno);
        calcTurni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String agente = (String) comboAgenti.getSelectedItem();
                Integer day = (Integer) giorno.getSelectedItem();
                String month = (String) mese.getSelectedItem();
                Integer year = (Integer) anno.getSelectedItem();
                Intent i = new Intent(getApplicationContext(), TurniActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("turniAgente", (ArrayList) turniAgente.get(agente));
                i.putExtras(b);
                i.putExtra("agente", agente);
                i.putExtra("day", day);
                i.putExtra("month", month);
                i.putExtra("year", year);
                i.putExtra("activity", "Main");
                startActivity(i);
            }
        });
    }

    public boolean checkTurni() {
        crs = db.getAll();
        if (crs != null && crs.getCount() > 0) {
            if (results.isEmpty()) {
                if (crs.moveToFirst()) {
                    do {
                        ArrayList<String> turnoAgente = new ArrayList<>();
                        turnoAgente.add(crs.getString(2));
                        turnoAgente.add(crs.getString(3));
                        turnoAgente.add(crs.getString(4));
                        turnoAgente.add(crs.getString(5));
                        turnoAgente.add(crs.getString(6));
                        turnoAgente.add(crs.getString(7));
                        turnoAgente.add(crs.getString(8));
                        turnoAgente.add(crs.getString(9));
                        turnoAgente.add(crs.getString(10));
                        results.add(turnoAgente);
                    } while (crs.moveToNext());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void setUpComboDataInizio(){
        Integer[] arrayGG = new Integer[31];
        String[] arrayMM = new String[12];
        arrayMM[0] = "Gennaio";
        arrayMM[1] = "Febbraio";
        arrayMM[2] = "Marzo";
        arrayMM[3] = "Aprile";
        arrayMM[4] = "Maggio";
        arrayMM[5] = "Giugno";
        arrayMM[6] = "Luglio";
        arrayMM[7] = "Agosto";
        arrayMM[8] = "Settembre";
        arrayMM[9] = "Ottobre";
        arrayMM[10] = "Novembre";
        arrayMM[11] = "Dicembre";

        Integer[] arrayYY = new Integer[5];
        arrayYY[0] = 2016;
        arrayYY[1] = 2017;
        arrayYY[2] = 2018;
        arrayYY[3] = 2019;
        arrayYY[4] = 2020;

        for (int i = 0; i<arrayGG.length; i++){
            arrayGG[i] = i+1;
        }
        ArrayAdapter adapterGG = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayGG);

        ArrayAdapter adapterMM = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayMM);

        ArrayAdapter adapterYY = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayYY);

        giorno.setAdapter(adapterGG);
        giorno.setSelection(1);
        giorno.setVisibility(View.VISIBLE);
        giorno.setEnabled(false);

        mese.setAdapter(adapterMM);
        mese.setSelection(4);
        mese.setVisibility(View.VISIBLE);
        mese.setEnabled(false);

        anno.setAdapter(adapterYY);
        anno.setSelection(0);
        anno.setVisibility(View.VISIBLE);
        anno.setEnabled(false);
    }
}
