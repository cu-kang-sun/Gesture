package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.gesturerecord.Database.DbBitmapUtility;
import com.example.gesturerecord.Database.GestureData;
import com.example.gesturerecord.Database.GestureDataDbHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GestureSetting extends AppCompatActivity {


    private Dialog viewGestureDialog;
    private GestureDataDbHelper db;

    String currentPoints;
    String currentName;
    private static Bitmap currentImage;
    private static Bitmap currentBackground;

    private static final int GET_FROM_GALLERY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_setting);

        db = new GestureDataDbHelper(this);

        Bundle extras = getIntent().getExtras();
        String source = extras.getString("source");

        if(source.equals("GestureList")){
            String name = extras.getString("gesture_name");

            //search db to set the image and points
            GestureData data = db.getGesture(name);
            currentPoints = data.getPoints();
            currentImage = data.getImage();
            currentName = name;

        }else if(source.equals("AddGesture")){
            currentPoints = extras.getString("points");
            if(extras.containsKey("name")){
                currentName = extras.getString("name");
            }else
                currentName = "default_name";

        }

        EditText editText =  (EditText) findViewById(R.id.gesture_name);
        editText.setText(currentName);



        viewGestureDialog = new Dialog(this, DialogFragment.STYLE_NO_FRAME);
        viewGestureDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        viewGestureDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewGestureDialog.setContentView(getLayoutInflater().inflate(R.layout.gesture_display
                , null));

        //hide bottom bar for viewGestureDialog
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = viewGestureDialog.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = viewGestureDialog.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }



        ImageView image = (ImageView) findViewById(R.id.gesture_img);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageDisplay = (ImageView) viewGestureDialog.findViewById(R.id.gesture_on_display);
                Log.i("image display", String.valueOf(imageDisplay));
                imageDisplay.setImageBitmap(currentImage);

                viewGestureDialog.show();

            }
        });

        image.setImageBitmap(currentImage);


    }




    public void stopViewGesture(View view){
        viewGestureDialog.dismiss();
    }



    public void editGesture(View view){
        Intent intent = new Intent(this, AddGestureActivity.class);
        intent.putExtra("source","GestureSetting");
        //pass the points
        intent.putExtra("points",currentPoints);
        intent.putExtra("name",currentName);
        //pass the background pic
        AddGestureActivity.setBackgroundPic(currentBackground);
        currentBackground = null;
        startActivity(intent);
    }



    public void saveGesture(View view){
        EditText editText =  (EditText) findViewById(R.id.gesture_name);
        currentName = String.valueOf(editText.getText());


        //check if the name exists first
        boolean isExist = db.isExist(currentName);


        if(isExist){
            //if it exists, show alertDialog, let user change the gesture name
            new AlertDialog.Builder(this)
                    .setTitle("This name is already taken")
                    .setMessage("Do you want to overwrite this existing gesture?")
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            //if it does not exist, save the gesture to database and redirect user to the gesture list page
                            GestureData newGesture = new GestureData(currentName, currentPoints, currentImage, currentBackground);
                            db.updateGesture(newGesture, currentName);
                            jumpToListPage();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            //if it does not exist, save the gesture to database and redirect user to the gesture list page
            GestureData newGesture = new GestureData(currentName, currentPoints, currentImage, currentBackground);
            db.addGesture(newGesture);
            jumpToListPage();

        }

    }

    public void jumpToListPage(){
        //redirect to gesture list page
        Intent intent = new Intent(this, GestureListActivity.class);
        startActivity(intent);
    }

    public void uploadBackgroundImg(View view){
        //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Log.i("gallery","start");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),GET_FROM_GALLERY);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                AddGestureActivity.setBackgroundPic(bitmap);
                jumpToEditGesture();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    public void resetBackGroundImg(View view){
        AddGestureActivity.setBackgroundPic(null);
        jumpToEditGesture();
    }


    public void jumpToEditGesture(){
        Intent intent = new Intent(this, AddGestureActivity.class);
        intent.putExtra("source","GestureSetting");
        intent.putExtra("points",currentPoints);
        intent.putExtra("name",currentName);
        startActivity(intent);
    }



    public static void setScreenShotImage(Bitmap bm){
        currentImage = bm;
    }

    public static void setBackgroundImage(Bitmap bm){
        currentBackground = bm;
    }


}
