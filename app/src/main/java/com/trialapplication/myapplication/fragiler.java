package com.trialapplication.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class fragiler extends SQLiteOpenHelper {

    public static final String Database_name = "post_details";
    public static final String Table_name = "postdet";
    public static final String col1 = "name";
    public static final String col2 = "item_name";
    public static final String col3 = "phone";
    public static final String col4 = "address";

    public fragiler(@Nullable Context context) {
        super(context, Database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + Table_name + "(name TEXT,item_name TEXT,phone INTEGER(14),address TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + Table_name);
        onCreate(db);
    }


    public boolean insertData(String name, String item_name, String phone_no, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, name);
        values.put(col2, item_name);
        values.put(col3, phone_no);
        values.put(col4, address);

        long var = db.insert(Table_name, null, values);
        if (var == -1) {
            Log.e("fragiler", "Data insertion failed");
            return false;
        } else {
            Log.d("fragiler", "Data inserted successfully");
            return true;
        }
    }
    public boolean deleteRecord(String name, String item_name, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {name, item_name, phone, address};
        int rowsAffected = db.delete(Table_name, "name=? AND item_name=? AND phone=? AND address=?", whereArgs);
        return rowsAffected > 0;
    }

    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns to be retrieved
        String[] columns = {col1, col2, col3, col4};

        // Query the database
        Cursor cursor = db.query(Table_name, columns, null, null, null, null, null);

        // Log column indices and names for debugging
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.d("Column Index", "Index: " + i + ", Name: " + cursor.getColumnName(i));
        }

        // Loop through the cursor and add records to the list
        while (cursor.moveToNext()) {
            // Retrieve column indices
            int nameIndex = cursor.getColumnIndex(col1);
            int itemNameIndex = cursor.getColumnIndex(col2);
            int phoneIndex = cursor.getColumnIndex(col3);
            int addressIndex = cursor.getColumnIndex(col4);

            // Log indices for debugging
            Log.d("Column Indices", "Name: " + nameIndex + ", ItemName: " + itemNameIndex + ", Phone: " + phoneIndex + ", Address: " + addressIndex);

            // Check if indices are valid before accessing values
            if (nameIndex >= 0 && itemNameIndex >= 0 && phoneIndex >= 0 && addressIndex >= 0) {
                String name = cursor.getString(nameIndex);
                String itemName = cursor.getString(itemNameIndex);
                String phone = cursor.getString(phoneIndex);
                String address = cursor.getString(addressIndex);

                // Create a new Record object and add it to the list
                Record record = new Record(name, itemName, phone, address);
                records.add(record);
            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return records;
    }
}
