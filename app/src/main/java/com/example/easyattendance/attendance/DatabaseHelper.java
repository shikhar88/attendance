package com.example.easyattendance.attendance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shikhar on 12/6/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "attendance.db";
    private static final String CREATE_TABLE_CLASSROOM = "CREATE TABLE "
            + "classroom" + "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "class_name"
            + " TEXT," + "class_year"+" TEXT," + "student_details" + " TEXT"+")";
    private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE "
            + "attendance" + "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "class_id"
            + " TEXT," + "year" + " TEXT," + "month" + " TEXT," + "day" + " TEXT," + "attendance" + " TEXT" + ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// creating required tables
        db.execSQL(CREATE_TABLE_CLASSROOM);
        db.execSQL(CREATE_TABLE_ATTENDANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "classroom");
        db.execSQL("DROP TABLE IF EXISTS " + "attendance");
        db.execSQL(CREATE_TABLE_CLASSROOM);
        db.execSQL(CREATE_TABLE_ATTENDANCE);

    }
}
