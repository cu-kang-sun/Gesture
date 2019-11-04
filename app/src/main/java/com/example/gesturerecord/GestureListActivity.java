package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class GestureListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_list);

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


        List<String> values = new ArrayList<>();
        values.add("Next Page");
        values.add("Previous Page");
        values.add("Center");



        MyCustomAdapter adapter = new MyCustomAdapter(values, this);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                Object item = adapter.getItemAtPosition(position);

                Intent intent = new Intent(GestureListActivity.this, GestureSetting.class);
                //based on item add info to intent
                startActivity(intent);
            }
        });


    }


    public void drawGesture(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddGestureActivity.class);
        startActivity(intent);
    }


}
