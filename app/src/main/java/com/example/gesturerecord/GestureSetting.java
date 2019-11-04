package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

public class GestureSetting extends AppCompatActivity {
    private Dialog viewGestureDialog;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_setting);

        viewGestureDialog = new Dialog(this, DialogFragment.STYLE_NO_FRAME);
        viewGestureDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        viewGestureDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewGestureDialog.setContentView(getLayoutInflater().inflate(R.layout.gesture_display
                , null));



        ImageView listView = (ImageView) findViewById(R.id.gesture_img_display);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGestureDialog.show();
            }
        });


//        Bundle bundle = getIntent().getExtras();
//        Bitmap bm = (Bitmap) bundle.get("gesture_pic");
//        String pointlist = (String) bundle.get("gesture_points");




    }


    public void viewGesture(View view){

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
