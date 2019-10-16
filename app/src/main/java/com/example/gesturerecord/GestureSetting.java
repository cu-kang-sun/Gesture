package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class GestureSetting extends AppCompatActivity {
    private Dialog viewGestureDialog;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_gesture);

        viewGestureDialog = new Dialog(this, DialogFragment.STYLE_NO_FRAME);
        viewGestureDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        viewGestureDialog.setContentView(getLayoutInflater().inflate(R.layout.gesture_display
                , null));

//        Bundle bundle = getIntent().getExtras();
//        Bitmap bm = (Bitmap) bundle.get("gesture_pic");
//        String pointlist = (String) bundle.get("gesture_points");


    }


    public void viewGesture(View view){
        viewGestureDialog.show();
    }

    public void stopViewGesture(View view){
        viewGestureDialog.dismiss();
    }


    public void editGesture(View view){
        Intent intent = new Intent(this, AddGestureActivity.class);
        startActivity(intent);
    }

    public void saveGesture(View view){


    }




    public void deleteGesture(View view){

    }


}
