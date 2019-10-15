package com.example.gesturerecord;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AddGestureActivity extends AppCompatActivity {
    private long delay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gesture);

        //Check for touches on our main layout
        final FingerLine fl = (FingerLine) findViewById(R.id.finger_line);
        fl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //If someone touches, reset our delay
                delay = 0;
                return false;
            }
        });

        //Start a thread that will keep count of the time
        new Thread("Listen for touch thread") {
            public void run() {
                    while(true){
                        try{
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //Incement the delay
                        delay += 1000;

                        //If our delay in MS is over 10,000
                        if (delay > 2000) {
                            Bitmap bm = getScreenShot();
                            List<Point> list = fl.getPoints();

                            saveGesture(list, bm);
                            return;
                        }
                    }


                }
            }.start();

    }


    private void saveGesture(List<Point> points, Bitmap bm){
        //pass the points back

        Intent intent = new Intent(this, GestureSetting.class);
        intent.putExtra("gesture_points", convertPointlistToStr(points));
        intent.putExtra("gesture_pic", bm);
        intent.putExtra("source","AddGesture");
        startActivity(intent);
    }

    private String convertPointlistToStr(List<Point> points){
        //x,y
        //connect (x,y) with ":"
        //so like (1,2):(2,3):(3,4)
        Queue<String> queue = new LinkedList<>();
        String str = "";
        for(int i=0;i<points.size();i++){
            queue.add("("+String.valueOf(points.get(i).x) + "," + String.valueOf(points.get(i).y)+")");
        }

        while(!queue.isEmpty()){
            str = ":" + queue.poll() + str;
        }
        if(str.length() > 1){
            str = str.substring(1, str.length());
        }
        return str;

    }


    private static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private static void store(Bitmap bm, String fileName){
        String dirPath = "res/drawable";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getScreenShot(){
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap bm = getScreenShot(rootView);
        return bm;
    }

}
