package com.example.cards2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    DBHelper DB;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);

        dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Pick", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }

    public long addMatch(String xStart, String xEnds, int xPlayers, String xResult){
        long result = DB.AddMatch(xStart,xEnds,xPlayers,xResult);
        if(result!=-1){
            Toast.makeText(this, "Data succesfully added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding data", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public void AddToSQL(View view){

        if(dropdown.getSelectedItem().toString().equals("Pick")){
            Toast.makeText(this, "Pick the number of players", Toast.LENGTH_SHORT).show();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String startTime = sdf.format(new Date());
            int num = Integer.parseInt(dropdown.getSelectedItem().toString());

            long id = addMatch(startTime, "-", num, "-");


            Intent i = new Intent(MainActivity.this, TableActivity.class);
            i.putExtra("MATCH_ID", id);
            startActivity(i);
        }
    }


}
