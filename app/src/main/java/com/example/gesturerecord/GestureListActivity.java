package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gesturerecord.Database.GestureData;
import com.example.gesturerecord.Database.GestureDataDbHelper;

import java.util.ArrayList;
import java.util.List;


public class GestureListActivity extends AppCompatActivity{

    private GestureDataDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_list);

        db = new GestureDataDbHelper(this);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                Object item = adapter.getItemAtPosition(position);

                Intent intent = new Intent(GestureListActivity.this, GestureSetting.class);
                //based on item add info to intent
                startActivity(intent);
            }
        });

        final List<String> values = db.getAllGestureNames();

        MyCustomAdapter adapter = new MyCustomAdapter(values, this);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                String gestureName = values.get(position);

                Intent intent = new Intent(GestureListActivity.this, GestureSetting.class);
                //based on item add info to intent
                intent.putExtra("source","GestureList");
                intent.putExtra("gesture_name", gestureName);
                startActivity(intent);

            }
        });

        //initialize other static parameters
        GestureSetting.currentImage = null;

    }


    public void drawGesture(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddGestureActivity.class);
        intent.putExtra("source","GestureList");
        startActivity(intent);
    }


}
