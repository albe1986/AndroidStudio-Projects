package com.example.apolverari.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a.polverari on 18/04/2016.
 */
public class DBManager {

    private DBHelper dbhelper;

    public DBManager(Context ctx){
        dbhelper = new DBHelper(ctx);
    }

    public void save(Nota n){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_TITLE, n.getTitolo());
        cv.put(DBHelper.FIELD_CONTENT, n.getContenuto());
        try {
            db.insert(DBHelper.TABLE_NAME, null, cv);
        } catch (SQLiteException e){
        } finally {
            db.close();
        }
    }

    public boolean delete(Nota n){
        String id = getIdNota(n);
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
            crs = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null, null);
        } catch (SQLiteException e){
            return null;
        } finally {
        }
        return crs;
    }

    public String getIdNota(Nota n){
        Cursor crs = null;
        Integer id = 0;
        SQLiteDatabase db = null;
        //Cursor crs = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null, null);
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME +
                    " WHERE " + DBHelper.FIELD_TITLE + " = '" + n.getTitolo() + "'" +
                    " AND " + DBHelper.FIELD_CONTENT + " = '" + n.getContenuto() + "'", null);
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
