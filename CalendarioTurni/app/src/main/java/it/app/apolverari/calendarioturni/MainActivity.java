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
import android.view.Menu;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                android.R.layout.simple_spinner_item, arrayComboAgenti);
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
        calcTurni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String agente = (String) comboAgenti.getSelectedItem();
                ExcelReader.calculate((ArrayList) turniAgente.get(agente), results);
                Intent i = new Intent(getApplicationContext(), TurniActivity.class);
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
}
