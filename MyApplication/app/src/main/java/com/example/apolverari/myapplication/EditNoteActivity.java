package com.example.apolverari.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        TextView tv = (TextView) findViewById(R.id.title_tv);
        EditText et = (EditText) findViewById(R.id.content);
        Nota n = (Nota)getIntent().getExtras().getSerializable("nota");
        if (n != null){
            tv.setText(n.getTitolo());
            et.setText(n.getContenuto());
        }
    }

}
