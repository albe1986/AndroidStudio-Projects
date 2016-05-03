package it.app.apolverari.calendarioturni;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private TextView filePathLabel;
    private EditText filePath;
    private Button loadFile;
    private Button calcTurni;
    private Spinner comboAgenti;
    private CalendarView calendarioTurni;
    private LinearLayout layoutTurni;
    private String arrayComboAgenti[];
    private HashMap turniAgente = new HashMap();
    private ArrayList results = new ArrayList();
    private File dir = Environment.getExternalStorageDirectory();
    private String defaultPath = dir.getAbsolutePath() + "/Download/**.xls";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpViewsAndButtons();
        ActivityCompat.requestPermissions( this, new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1 );
    }

    private void setUpComboAgenti(){
        arrayComboAgenti = new String[results.size() - 2];
        for (int i = 2; i<results.size(); i++){
            ArrayList<String> row = (ArrayList<String>) results.get(i);
            arrayComboAgenti[i-2] = row.get(0);
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
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayComboAgenti);
        comboAgenti.setAdapter(adapter);
        comboAgenti.setVisibility(View.VISIBLE);
        calcTurni.setVisibility(View.VISIBLE);
        layoutTurni.removeView(filePathLabel);
        layoutTurni.removeView(loadFile);
        layoutTurni.removeView(filePath);
    }

    private void setUpViewsAndButtons(){
        filePathLabel = (TextView) findViewById(R.id.filePathLabel);
        filePath = (EditText) findViewById(R.id.filePath);
        loadFile = (Button) findViewById(R.id.buttonLoad);
        comboAgenti = (Spinner) findViewById(R.id.comboAgenti);
        calcTurni = (Button) findViewById(R.id.buttonCalc);
        layoutTurni = (LinearLayout) findViewById(R.id.turniLayout);
        calendarioTurni = (CalendarView) findViewById(R.id.calendarioTurni);

        filePath.setText(defaultPath);

        loadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String newPath = filePath.getText().toString();
                    results = ExcelReader.read(newPath);
                    setUpComboAgenti();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this, "Errore durante il caricamento del file: " +
                                    e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        calcTurni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String agente = (String) comboAgenti.getSelectedItem();
                ExcelReader.calculate((ArrayList) turniAgente.get(agente), results);
                calendarioTurni.setVisibility(View.VISIBLE);
            }
        });
    }

}
