package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gesturerecord.R;

public class DrawGestureActivity extends AppCompatActivity {
    private DialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_gesture);
    }



    public void addGesture(View view){

//        LayoutInflater inflater = this.getLayoutInflater();
//        View layout = inflater.inflate(R.layout.activity_draw_gesture, null);
//
//        FingerLine fingerLineView = layout.findViewById(R.id.finger_line);
//        if(fingerLineView == null){
//            Log.i("point_info","get null");
//        }else{
//            Log.i("point_info","get something");
//            List<Point> pointsList = fingerLineView.getPoints();
//            for(int i=0;i<pointsList.size();i++){
//                Log.i("point_info",pointsList.get(i).x+":"+pointsList.get(i).y);
//            }
//        }

    }




    public void deleteGesture(View view){


    }


}
