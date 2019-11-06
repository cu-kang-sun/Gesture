package com.example.gesturerecord;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gesturerecord.Database.GestureData;
import com.example.gesturerecord.Database.GestureDataDbHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class AddGestureActivity extends AppCompatActivity {


    private long delay = 0;
    private boolean inProgress = false;

    private ProgressBar mProgressBar;
    private FingerLine fl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //hide top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //hide bottom bar
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }


        setContentView(R.layout.activity_add_gesture);





        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(0);

        //Check for touches on our main layout
        fl = (FingerLine) findViewById(R.id.finger_line);
        fl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //If someone touches, reset our delay
                delay = 0;
                mProgressBar.setProgress(0);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mProgressBar.setVisibility(View.INVISIBLE);
                    inProgress = true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    mProgressBar.setVisibility(View.VISIBLE);
                    inProgress = false;
                }

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

                        if(inProgress == false){
                            //Incement the delay
                            delay += 1000;
                            mProgressBar.setProgress((int)delay/1000*100/(3000/1000));
                            //If our delay in MS is over 10,000
                            if (delay > 2000) {
                                Bitmap bm = getScreenShot();
                                List<Point> list = fl.getPoints();
                                saveCurrentGesture(list, bm);
                                return;
                            }
                        }
                    }
                }
            }.start();
    }


    private void saveCurrentGesture(List<Point> points, Bitmap bm){
        //pass the points back


        Intent intent = new Intent(this, GestureSetting.class);

        intent.putExtra("source","AddGesture");
        GestureSetting.currentImage = bm;
        intent.putExtra("points", convertPointlistToStr(points));
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


    private Bitmap getScreenShot(View view) {
        stopCounting();
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }



    private Bitmap getScreenShot(){
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap bm = getScreenShot(rootView);
        return bm;
    }

    private void stopCounting(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
