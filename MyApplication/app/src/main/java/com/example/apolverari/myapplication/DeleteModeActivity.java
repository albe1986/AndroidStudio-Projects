package com.example.apolverari.myapplication;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class DeleteModeActivity extends AppCompatActivity {

    private DBManager db = null;
    private Cursor crs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_mode);
        db = new DBManager(this);
        setNoteList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setNoteList(){
        ArrayList<Nota> note = new ArrayList<Nota>();
        crs = db.getAll();
        if (crs != null && crs.getCount() > 0) {
            if (crs.moveToFirst()) {
                do {
                    Nota n = new Nota(crs.getString(1), crs.getString(2));
                    note.add(n);
                } while (crs.moveToNext());
            }
        }
        LinearLayout l = (LinearLayout) findViewById(R.id.delete_layout);
        for (int i = 0; i < note.size(); i++){
            CheckedTextView tv = new CheckedTextView(this);
            tv.setText(note.get(i).getTitolo());
            tv.setTextSize(35);
            tv.setChecked(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getBackground() == null) {
                        v.setBackgroundColor(Color.CYAN);
                    } else {
                        ColorDrawable cd = (ColorDrawable) v.getBackground();
                        if (cd.getColor() == Color.CYAN) {
                            v.setBackgroundColor(Color.TRANSPARENT);
                        } else if (cd.getColor() == Color.TRANSPARENT) {
                            v.setBackgroundColor(Color.CYAN);
                        }
                    }
                }
            });
            l.addView(tv);
        }
    }
}
