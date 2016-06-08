package it.app.apolverari.calendarioturni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditTurno extends AppCompatActivity {

    private EditText turnoField;
    private EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_turno);
        turnoField = (EditText) findViewById(R.id.editText);
        note = (EditText) findViewById(R.id.editText2);
        String day = getIntent().getStringExtra("day");
        String turno = getIntent().getStringExtra("turno");
        turnoField.setText(turno);
        note.setText(day);

    }
}
