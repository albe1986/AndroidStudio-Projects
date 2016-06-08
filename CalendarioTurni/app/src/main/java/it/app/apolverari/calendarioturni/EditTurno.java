package it.app.apolverari.calendarioturni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTurno extends AppCompatActivity {

    private EditText turnoField;
    private EditText note;
    private Button save;
    private String day;
    private String turno;
    private String agente;
    private Integer month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_turno);
        turnoField = (EditText) findViewById(R.id.editTurno);
        note = (EditText) findViewById(R.id.editNote);
        save = (Button) findViewById(R.id.editSave);
        day = getIntent().getStringExtra("day");
        turno = getIntent().getStringExtra("turno");
        month = Integer.valueOf(getIntent().getStringExtra("month"));
        agente = getIntent().getStringExtra("agente");
        turnoField.setText(turno);
        note.setText(agente + "   " + month + "  " + day);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
