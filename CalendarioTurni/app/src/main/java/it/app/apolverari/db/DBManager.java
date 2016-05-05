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
            db.replace(DBHelper.TABLE_NAME, null, cv);
        } catch (SQLiteException e){
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean delete(String agente, String dataInizio, String note){
        String id = getIdPark(agente, dataInizio);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            int recordDeleted = db.delete(DBHelper.TABLE_NAME, DBHelper.FIELD_ID + "=?", new String[]{id});
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
            crs = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, DBHelper.FIELD_AGE, null);
        } catch (SQLiteException e){
            return null;
        }
        return crs;
    }

    public String getIdPark(String agente, String dataInizio){
        Cursor crs = null;
        Integer id = 0;
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME +
                    " WHERE " + DBHelper.FIELD_AGE + " = '" + agente + "'" +
                    " AND " + DBHelper.FIELD_DIN + " = '" + dataInizio + "'", null);
            if (crs.moveToFirst()) {
                id = crs.getInt(0);
            }
        } catch (SQLiteException e){
        } finally {
            crs.close();
            db.close();
        }
        return id.toString();
    }
}
