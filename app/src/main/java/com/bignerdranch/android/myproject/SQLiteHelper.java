package com.bignerdranch.android.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 21/02/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "DemoDataBase1";
    public static final String TABLE_NAME="demo";
    public static final String COL1 = "id";
    public static final String COL2 = "name";
    public static final String COL3 = "alarmType";
    public static final String COL4 = "notification";
    public static final String COL5 = "ringtone";
    public static final String COL6 = "alarmTiming";
    public static final String COL7 = "alarmDate";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+COL1+" INTEGER PRIMARY KEY, "+COL2+" TEXT," +
                " "+COL3+" TEXT, "+COL4+" TEXT, "+COL5+" TEXT, "+COL6+" TEXT, "+COL7+" TEXT)";
        try{
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }




}
