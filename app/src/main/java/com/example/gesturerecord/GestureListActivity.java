package com.example.gesturerecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gesturerecord.R;

public class GestureListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_list);

        ListView listView = (ListView) findViewById(R.id.list);
        String[] values = new String[] { "Next Page", "Previous Page","Center"
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
    }


    public void drawGesture(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DrawGestureActivity.class);
        startActivity(intent);
    }


}
