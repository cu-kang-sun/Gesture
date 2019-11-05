package com.example.gesturerecord.Database;

import android.graphics.Bitmap;
import android.provider.BaseColumns;

public final class GestureData {

    private String name;
    private String points;
    private Bitmap image;

    public GestureData(String n, String p, Bitmap i){
        this.name = n;
        this.points = p;
        this.image = i;
    }

    public String getName() {
        return name;
    }

    public String getPoints() {
        return points;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
