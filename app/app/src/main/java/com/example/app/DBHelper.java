package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Practices1.db";
    private static final String TABLE_NAME = "practice_library";
    private static final String TABLE_NAME2 = "practice_library2";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_PUSHUP = "pushup";
    private static final String COLUMN_CRUNCH = "crunch";
    private static final String COLUMN_SQUART = "squart";
    private static final String COLUMN_WEIGHT = "weight";




    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE if not EXISTS " + TABLE_NAME + "(date TEXT PRIMARY KEY, pushup INTEGER, crunch INTEGER, squart INTEGER)";
        String sql2 = "CREATE TABLE if not EXISTS " + TABLE_NAME2 + "(date TEXT PRIMARY KEY, weight REAL)";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists " + TABLE_NAME;

        db.execSQL(sql);
        onCreate(db);
    }

    public void insertData2(String date, Integer a, Integer b, Integer c) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "insert or replace into practice_library (date, pushup, crunch, squart) values ('" + date + "', '" + a + "', '" + b + "', '" + c + "');";

        db.execSQL(sql);
    }

    public void insertWeight(String date, float a) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "insert or replace into practice_library2 (date, weight) values ('" + date + "', '" + a + "');";

        db.execSQL(sql);
    }




    public Cursor getAllData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where date = '" + date + "';", null);

        return res;
    }

    public Cursor getAllData2(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME2 + " where date = '" + date + "';", null);

        return res;
    }


}








