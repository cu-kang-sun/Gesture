package com.example.gesturerecord;

import android.graphics.Bitmap;
import android.provider.BaseColumns;

public final class GestureData {
    private GestureData(){

    }


    public static class GestureEntry {
        public static final String TABLE_NAME = "gesture";
        public static final String COLUMN_NAME_GESTURE_NAME ="name";
        public static final String COLUMN_NAME_GESTURE_POINTS = "points";
//        public static final Bitmap COLUMN_NAME_GESTURE_IMAGE = "image";
    }

}
