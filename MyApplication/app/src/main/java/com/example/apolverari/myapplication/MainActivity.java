package com.example.apolverari.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBManager db = null;
    private Cursor crs = null;
    private static Nota item = null;
    NoteAdapter na = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBManager(this);
        setItemList();
        Button nuova = (Button) findViewById(R.id.new_note);
        nuova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setItemList();
    }

    private void setItemList() {
        ArrayList<Nota> note = new ArrayList<Nota>();
        ListView lview = (ListView) findViewById(R.id.listView);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditNoteActivity.class);
                item = (Nota) parent.getItemAtPosition(position);
                Bundle b = new Bundle();
                b.putSerializable("nota", item);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = (Nota) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Eliminare la nota selezionata?")
                        .setPositiveButton("Si",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                db = new DBManager(getApplicationContext());
                                boolean result = db.delete(item);
                                if (result){
                                    Toast.makeText(MainActivity.this, "Nota eliminata correttamente.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Errore durante la cancellazione della nota.", Toast.LENGTH_SHORT).show();
                                }
                                if (na.getCount()==1){
                                    finish();
                                    startActivity(getIntent());
                                } else {
                                    setItemList();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            };
        });
        crs = db.getAll();
        if (crs != null && crs.getCount() > 0) {
            if (crs.moveToFirst()) {
                do {
                    Nota n = new Nota(crs.getString(1), crs.getString(2));
                    note.add(n);
                } while (crs.moveToNext());
            }
            Nota[] n = new Nota[note.size()];
            for (int i = 0; i < note.size(); i++) {
                n[i] = note.get(i);
            }
            na = new NoteAdapter(this, R.layout.custom_row, n);
            lview.setAdapter(na);
        }

    }

}
