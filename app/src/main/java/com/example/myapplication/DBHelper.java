package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Users.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PHONE";
    public static final String COL_5 = "PASSWORD";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, PHONE TEXT, PASSWORD TEXT)");
        Log.d("DBHelper", "Database and table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, phone);
        contentValues.put(COL_5, password);

        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d("DBHelper", "Data insertion result: " + result);

        if (result == -1) {
            Log.d("DBHelper", "Data insertion failed.");
            return false;
        } else {
            Log.d("DBHelper", "Data inserted successfully.");
            return true;
        }
    }

    // Method to retrieve all users for debugging purposes
    public void getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.getCount() == 0) {
            Log.d("DBHelper", "No users found.");
        } else {
            while (cursor.moveToNext()) {
                Log.d("DBHelper", "User: " +
                        "ID: " + cursor.getInt(0) +
                        ", Name: " + cursor.getString(1) +
                        ", Email: " + cursor.getString(2) +
                        ", Phone: " + cursor.getString(3) +
                        ", Password: " + cursor.getString(4));
            }
        }

        // Do not close the cursor here to allow accessing it later
        // cursor.close();
    }
}
