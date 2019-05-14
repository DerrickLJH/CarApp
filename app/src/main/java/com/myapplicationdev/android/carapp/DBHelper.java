package com.myapplicationdev.android.carapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "Car.db";

    private static final String TABLE_CAR = "car";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BRAND= "brand";
    private static final String COLUMN_LITRE = "litre";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_CAR + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BRAND + "	TEXT,"
                + COLUMN_LITRE + " 	NUMERIC )";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        String createTableSql = "CREATE TABLE " + TABLE_CAR + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BRAND + " TEXT,"
                + COLUMN_LITRE + " NUMERIC )";
        db.execSQL(createTableSql);
        onCreate(db);
    }

    public void insertCar(String brand, double litre) {
        //TODO insert the data into the database
        SQLiteDatabase db = this.getWritableDatabase();
        /* We use ContentValues object to store the values for
        the db operation */
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_BRAND, brand);
        // Store the column name as key and the date as value
        values.put(COLUMN_LITRE, litre);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_CAR, null , values);
        // Close the database connection
        db.close();
    }

    public ArrayList<Car> getAllCars() {
        //TODO return records in Java objects
        ArrayList<Car> tasks = new ArrayList<Car>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_BRAND + ", "
                + COLUMN_LITRE
                + " FROM " + TABLE_CAR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String brand = cursor.getString(1);
                double litre = cursor.getDouble(2);
                Car obj = new Car(id, brand, litre);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    
}
