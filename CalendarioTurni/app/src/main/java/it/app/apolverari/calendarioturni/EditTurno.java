package it.app.apolverari.calendarioturni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import it.app.apolverari.db.DBManager;

public class EditTurno extends AppCompatActivity {

    private TextView editTitle;
    private EditText turnoField;
    private EditText note;
    private Button save;
    private Button annulla;
    private String day;
    private String turno;
    private String agente;
    private Integer month;
    private String HTML;
    private DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_turno);
        setUpViews();
        day = getIntent().getStringExtra("day");
        turno = getIntent().getStringExtra("turno");
        month = Integer.valueOf(getIntent().getStringExtra("month"));
        agente = getIntent().getStringExtra("agente");
        turnoField.setText(turno);
        editTitle.setText("Modifica turno del giorno " + day + "/" + (month+1) + " *** Agente: " + agente + " ***");
        db = new DBManager(this);
        note.setText(db.getNote(agente, turno, Integer.valueOf(day), month));
    }

    public void setUpViews(){
        editTitle = (TextView) findViewById(R.id.editTitle);
        turnoField = (EditText) findViewById(R.id.editTurno);
        note = (EditText) findViewById(R.id.editNote);
        save = (Button) findViewById(R.id.editSave);
        annulla = (Button) findViewById(R.id.editAnnulla);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTML = MiscUtils.updateAndReloadTurni(db, agente, month, 4, day, turnoField.getText().toString());
                db.saveNote(agente, turnoField.getText().toString(), Integer.valueOf(day), month, note.getText().toString());
                Intent i = new Intent(getApplicationContext(), TurniActivity.class);
                i.putExtra("HTML", HTML);
                i.putExtra("activity", "Edit");
                startActivity(i);
                finish();
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTML = MiscUtils.updateAndReloadTurni(db, agente, month, 4, day, turnoField.getText().toString());
                Intent i = new Intent(getApplicationContext(), TurniActivity.class);
                i.putExtra("HTML", HTML);
                i.putExtra("activity", "Edit");
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
