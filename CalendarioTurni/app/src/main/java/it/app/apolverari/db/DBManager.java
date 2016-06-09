package it.app.apolverari.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

/**
 * Created by a.polverari on 04/05/2016.
 */
public class DBManager {

    private DBHelper dbhelper;

    public DBManager(Context ctx){
        dbhelper = new DBHelper(ctx);
    }

    public boolean save(ArrayList<String> turniAgente, String pos, String dataInizio){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_POS, pos);
        cv.put(DBHelper.FIELD_AGE, turniAgente.get(0));
        cv.put(DBHelper.FIELD_LUN, turniAgente.get(1));
        cv.put(DBHelper.FIELD_MAR, turniAgente.get(2));
        cv.put(DBHelper.FIELD_MER, turniAgente.get(3));
        cv.put(DBHelper.FIELD_GIO, turniAgente.get(4));
        cv.put(DBHelper.FIELD_VEN, turniAgente.get(5));
        cv.put(DBHelper.FIELD_SAB, turniAgente.get(6));
        cv.put(DBHelper.FIELD_DOM, turniAgente.get(7));
        cv.put(DBHelper.FIELD_DIN, dataInizio);
        try {
            db.replace(DBHelper.TABLE_TURNI_NAME, null, cv);
        } catch (SQLiteException e){
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public Cursor getAll(){
        Cursor crs = null;
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.query(DBHelper.TABLE_TURNI_NAME, null, null, null, null, null, DBHelper.FIELD_AGE, null);
        } catch (SQLiteException e){
            return null;
        }
        return crs;
    }

    public String getPosAgente(String agente){
        Cursor crs = null;
        String pos = "";
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_TURNI_NAME +
                    " WHERE " + DBHelper.FIELD_AGE + " = '" + agente + "'", null);
            if (crs.moveToFirst()) {
                pos = crs.getString(1);
            }
        } catch (SQLiteException e){
        } finally {
            crs.close();
            db.close();
        }
        return pos;
    }

    public String[] getTurnoByPos(String pos){
        Cursor crs = null;
        String[] turni = new String[7];
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_TURNI_NAME +
                    " WHERE " + DBHelper.FIELD_POS + " = '" + pos + "'", null);
            if (crs.moveToFirst()) {
                turni[0] = crs.getString(3);
                turni[1] = crs.getString(4);
                turni[2] = crs.getString(5);
                turni[3] = crs.getString(6);
                turni[4] = crs.getString(7);
                turni[5] = crs.getString(8);
                turni[6] = crs.getString(9);

            } else {
                return null;
            }

        } catch (SQLiteException e){
        } finally {
            crs.close();
            db.close();
        }
        return turni;
    }

    public boolean saveTurniHash(String turniAgente, String agente, Integer mese, Integer anno){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_AGE, agente);
        cv.put(DBHelper.FIELD_ANN, anno);
        cv.put(DBHelper.FIELD_MES, mese);
        cv.put(DBHelper.FIELD_HASH_MAP, turniAgente);
        try {
            db.replace(DBHelper.TABLE_HASH_NAME, null, cv);
        } catch (SQLiteException e){
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean updateTurniHash(String turniAgente, String agente, Integer mese, Integer anno){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_HASH_MAP, turniAgente);
        try {
            db.update(DBHelper.TABLE_HASH_NAME, cv, DBHelper.FIELD_AGE + " = '" + agente +
                        "' AND " + DBHelper.FIELD_ANN + " = " + anno +
                        "  AND " + DBHelper.FIELD_MES + " = " + mese, null );
        } catch (SQLiteException e){
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public String getTurnoMeseHash(String agente, Integer mese, Integer anno){
        Cursor crs = null;
        String turniJson = "";
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_HASH_NAME +
                    " WHERE " + DBHelper.FIELD_AGE + " = '" + agente + "'" +
                    " AND " + DBHelper.FIELD_MES + " = " + mese +
                    " AND " + DBHelper.FIELD_ANN + " = " + anno, null);
            if (crs.moveToFirst()) {
                turniJson = crs.getString(4);
            }
        } catch (SQLiteException e){
        } finally {
            crs.close();
            db.close();
        }
        return turniJson;
    }

    public boolean saveNote(String agente, String turno, Integer day, Integer mese, String note){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_AGE, agente);
        cv.put(DBHelper.FIELD_TUR, turno);
        cv.put(DBHelper.FIELD_GGG, day);
        cv.put(DBHelper.FIELD_MES, mese);
        cv.put(DBHelper.FIELD_NTT, note);
        try {
            db.replace(DBHelper.TABLE_NOTE_NAME, null, cv);
        } catch (SQLiteException e){
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public String getNote(String agente, String turno, Integer day, Integer mese){
        Cursor crs = null;
        String note = "";
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NOTE_NAME +
                    " WHERE " + DBHelper.FIELD_AGE + " = '" + agente + "'" +
                    " AND " + DBHelper.FIELD_TUR + " = '" + turno + "'" +
                    " AND " + DBHelper.FIELD_GGG + " = " + day +
                    " AND " + DBHelper.FIELD_MES + " = " + mese, null);
            if (crs.moveToFirst()) {
                note = crs.getString(5);
            }
        } catch (SQLiteException e){
        } finally {
            crs.close();
            db.close();
        }
        return note;
    }

}
