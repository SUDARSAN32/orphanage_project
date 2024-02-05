package com.trialapplication.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class orphanageHelper extends SQLiteOpenHelper {

    private static final String Database_name = "opdetails";
    private static final String Table_name = "orphanage";
    private static final String col1 = "email";
    private static final String col2 = "phone";
    private static final String col3 = "password";
    private static final String col4 = "c_password";
    private static final String col5 = "Orphanage_id";
    private static final String col6 = "mode";

    public orphanageHelper(@SuppressLint("Range") Context context) {
        super(context, Database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + Table_name + "(email varchar(50),phone INTEGER(10),password varchar(10),c_password varchar(10),Orphanage_id INTEGER(3),mode varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + Table_name);
        onCreate(db);
    }

    public boolean insertData(String email, String phone, String password, String c_password, String Orphanage_id, String mode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, email);
        values.put(col2, phone);
        values.put(col3, password);
        values.put(col4, c_password);
        values.put(col5, Orphanage_id);
        values.put(col6, mode);

        long var = db.insert(Table_name, null, values);
        return var != -1;
    }

    public boolean authenticateOrphanage(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_name + " WHERE " + col1 + "=? AND " + col3 + "=?", new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public String getOrphanagePhoneNumber(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s=email;
        Log.e("OrphanageHelper", "email is"+s);


        if (email == null) {
            Log.e("OrphanageHelper", "Email is null");
            return null;
        }

        Cursor cursor = db.query(Table_name, new String[]{col2}, col1 + "=?", new String[]{email}, null, null, null);

        String phoneNumber = null;
        try {
            if (cursor.moveToFirst()) {
                phoneNumber = cursor.getString(cursor.getColumnIndex(col2));
            }
        } catch (Exception e) {
            Log.e("OrphanageHelper", "Error fetching orphanage phone number", e);
        } finally {
            cursor.close();
        }

        if (phoneNumber != null) {
            Log.e("OrphanageHelper", "Phone number for " + email + ": " + phoneNumber);
        } else {
            Log.e("OrphanageHelper", "Phone number not found for " + email);
        }

        return phoneNumber;
    }

}
