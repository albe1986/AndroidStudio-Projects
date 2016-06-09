package it.app.apolverari.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a.polverari on 04/05/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CALENDARIO_TURNI";
    public static final String TABLE_TURNI_NAME = "TURNI";
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
            "CREATE TABLE " + TABLE_TURNI_NAME + " (" +
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

    public static final String TABLE_HASH_NAME = "TurnoAgente";
    public static final String FIELD_ANN = "ANN";
    public static final String FIELD_MES = "MES";
    public static final String FIELD_HASH_MAP = "HSH";

    private static final String CREATE_QUERY_2 =
            "CREATE TABLE " + TABLE_HASH_NAME + " (" +
                    FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_AGE + " TEXT, " +
                    FIELD_ANN + " INTEGER, " +
                    FIELD_MES + " INTEGER, " +
                    FIELD_HASH_MAP + " TEXT " + ");";

    public static final String TABLE_NOTE_NAME = "NoteTurno";
    public static final String FIELD_GGG = "GGG";
    public static final String FIELD_TUR = "TUR";
    public static final String FIELD_NTT = "NTT";
    private static final String CREATE_QUERY_3 =
            "CREATE TABLE " + TABLE_NOTE_NAME + " (" +
                    FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_AGE + " TEXT, " +
                    FIELD_TUR + " TEXT, " +
                    FIELD_GGG + " INTEGER, " +
                    FIELD_MES + " INTEGER, " +
                    FIELD_NTT + " TEXT " + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY_2);
        db.execSQL(CREATE_QUERY_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
