package com.example.apolverari.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a.polverari on 14/04/2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "NOTE";
    public static final String TABLE_NAME = "Note";
    public static final String FIELD_ID = "id";
    public static final String FIELD_TITLE = "titolo";
    public static final String FIELD_CONTENT = "contenuto";
    private static final String CREATE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_TITLE + " TEXT" +
                    FIELD_CONTENT + " TEXT " + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
