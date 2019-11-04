package com.example.gesturerecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashMap;
//We need one table now(id: gesture_name)
//column: gesture_name, points, picture_name


//we use android built-in sqlite db service to do that
public class GestureDataDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GestureReader.db";

//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + GestureData.GestureEntry.TABLE_NAME + " (" +
//                    GestureData.GestureEntry._ID + " INTEGER PRIMARY KEY," +
//                    GestureData.GestureEntry.COLUMN_NAME_GESTURE_NAME + " TEXT," +
//                    GestureData.GestureEntry.COLUMN_NAME_GESTURE_POINTS + " TEXT," +
//                    GestureData.GestureEntry.COLUMN_NAME_GESTURE_IMAGE + " BLOB)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GestureData.GestureEntry.TABLE_NAME;

    public GestureDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
