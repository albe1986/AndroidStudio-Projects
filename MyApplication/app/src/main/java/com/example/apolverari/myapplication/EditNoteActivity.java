package com.example.apolverari.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {

    private DBManager db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        EditText tv = (EditText) findViewById(R.id.title_tv);
        EditText et = (EditText) findViewById(R.id.content);
        Nota n = null;
        if (getIntent().getExtras() != null) {
            n = (Nota) getIntent().getExtras().getSerializable("nota");
        }
        if (n != null){
            tv.setText(n.getTitolo());
            et.setText(n.getContenuto());
        } else {
            tv.setText("NuovaNota");
            et.setText("Inserire testoS");
        }

        Button salva = (Button) findViewById(R.id.save_note);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tv = (EditText) findViewById(R.id.title_tv);
                EditText et = (EditText) findViewById(R.id.content);
                String titolo = tv.getText().toString();
                String contenuto = et.getText().toString();
                Nota n = new Nota(titolo, contenuto);
                db = new DBManager(getApplicationContext());
                boolean result = db.save(n);
                if (result){
                    Toast.makeText(EditNoteActivity.this, "Nota salvata correttamente.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditNoteActivity.this, "Errore durante il salvataggio della nota.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

}
