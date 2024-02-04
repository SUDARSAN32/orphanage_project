package com.trialapplication.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserHelperClass extends SQLiteOpenHelper
{
    private static final String Database_name="details";
    private static final String Table_name="users";
    private static final String col1="name";
    private static final String col2="phone";
    private static final String col3="email";
    private static final String col4="password";
    private static final String col5="c_password";
    private static final String col6="mode";

    public UserHelperClass(@Nullable Context context) {
        super(context, Database_name,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("create table if not exists "+Table_name+"(name TEXT,phone INTEGER(10),email varchar(50),password varchar(10),c_password varchar(10),mode varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
     db.execSQL("drop table if exists "+Table_name);
     onCreate(db);
    }
    public boolean insertData(String name,String phone,String email,String password,String c_password,String mode)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(col1,name);
        values.put(col2,phone);
        values.put(col3,email);
        values.put(col4,password);
        values.put(col5,c_password);
        values.put(col6,mode);

        long var=db.insert(Table_name,null,values);
        if(var==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_name + " WHERE " + col3 + "=? AND " + col4 + "=?", new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

}