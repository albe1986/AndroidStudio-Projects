package it.app.apolverari.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a.polverari on 04/05/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CALENDARIO_TURNI";
    public static final String TABLE_NAME = "TURNI";
    public static final String FIELD_ID = "ID";
    public static final String FIELD_POS = "POS";
    public static final String FIELD_AGE = "AGE";
    public static final String FIELD_LUN = "LUN";
    public static final String FIELD_MAR = "MAR";
    public static final String FIELD_MER = "MER";
    public static final String FIELD_GIO = "GIO";
    public static final String FIELD_VEN = "VEN";
    public static final String FIELD_SAB = "SAB";
    public static final String FIELD_DOM = "DOM";
    public static final String FIELD_DIN = "DAT_INI";
    private static final String CREATE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_POS + " TEXT UNIQUE, " +
                    FIELD_AGE + " TEXT UNIQUE, " +
                    FIELD_LUN + " TEXT, " +
                    FIELD_MAR + " TEXT, " +
                    FIELD_MER + " TEXT, " +
                    FIELD_GIO + " TEXT, " +
                    FIELD_VEN + " TEXT, " +
                    FIELD_SAB + " TEXT, " +
                    FIELD_DOM + " TEXT, " +
                    FIELD_DIN + " TEXT " + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
