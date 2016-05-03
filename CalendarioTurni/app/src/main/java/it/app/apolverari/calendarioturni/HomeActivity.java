package it.app.apolverari.calendarioturni;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    private EditText filePath;
    private Button loadFile;
    private ArrayList<String> results = new ArrayList();
    private File dir = Environment.getExternalStorageDirectory();
    private String defaultPath = dir.getAbsolutePath() + "/Download/**.xls";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        filePath = (EditText) findViewById(R.id.filePath);
        loadFile = (Button) findViewById(R.id.buttonLoad);
        ActivityCompat.requestPermissions( this, new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1 );
        filePath.setText(defaultPath);
        loadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String newPath = filePath.getText().toString();
                    results = ExcelReader.read(newPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this, "Errore durante il caricamento del file: " +
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
