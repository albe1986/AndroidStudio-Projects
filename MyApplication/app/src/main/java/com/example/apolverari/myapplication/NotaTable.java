package com.example.apolverari.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a.polverari on 14/04/2016.
 */
public class NotaTable extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String NOTE_TABLE_NAME = "note";
    private static final String NOTE_ID = "id";
    private static final String NOTE_TITLE = "titolo";
    private static final String NOTE_CONTENT = "contenuto";
    private static final String NOTE_TABLE_CREATE =
            "CREATE TABLE " + NOTE_TABLE_NAME + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY, " +
                    NOTE_TITLE + " TEXT" +
                    NOTE_CONTENT + " TEXT " + ");";

    public NotaTable(Context context) {
        super(context, "AppDB", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
