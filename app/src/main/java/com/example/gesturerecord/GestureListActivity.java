package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class GestureListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_list);

        ListView listView = (ListView) findViewById(R.id.list);
        final String[] values = new String[] { "Next Page", "Previous Page","Center"
//                ,"Next Page", "Previous Page","Center","Next Page", "Previous Page","Center","Next Page", "Previous Page","Center","Next Page", "Previous Page","Center","Next Page", "Previous Page","Center"
        };

        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(GestureListActivity.this, GestureSetting.class);
                String message = values[position];
                intent.putExtra("gesture_name", message);
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
