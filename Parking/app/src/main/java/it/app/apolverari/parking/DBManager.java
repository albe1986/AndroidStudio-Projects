package it.app.apolverari.parking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by a.polverari on 18/04/2016.
 */
public class DBManager {

    private DBHelper dbhelper;

    public DBManager(Context ctx){
        dbhelper = new DBHelper(ctx);
    }

    public boolean save(String titolo, String coordinate, String note, String data){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_TITLE, titolo);
        cv.put(DBHelper.FIELD_CONTENT, coordinate);
        cv.put(DBHelper.FIELD_NOTES, note);
        cv.put(DBHelper.FIELD_DATE, data);
        try {
            db.insert(DBHelper.TABLE_NAME, null, cv);
        } catch (SQLiteException e){
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean delete(String titolo, String coordinate, String note){
        String id = getIdPark(titolo, coordinate, note);
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
        }
        return crs;
    }

    public String getIdPark(String titolo, String coordinate, String note){
        Cursor crs = null;
        Integer id = 0;
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getReadableDatabase();
            crs = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME +
                    " WHERE " + DBHelper.FIELD_TITLE + " = '" + titolo + "'" +
                    " AND " + DBHelper.FIELD_CONTENT + " = '" + coordinate + "'" +
                    " AND " + DBHelper.FIELD_CONTENT + " = '" + note + "'", null);
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
