package com.example.cards2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    DBHelper DB;
    Spinner dropdown;
    Button create_match, reconnect;
    String State;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);

        create_match = findViewById(R.id.button);
        reconnect = findViewById(R.id.button3);

        dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Pick", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("state", Context.MODE_PRIVATE);
        State = prefs.getString("state","DISCONNECTED");

        Log.d("State",State);

        if(State.equals("CONNECTED")){
            dropdown.setEnabled(false);
            create_match.setEnabled(false);
            reconnect.setEnabled(true);
        } else {
            dropdown.setEnabled(true);
            create_match.setEnabled(true);
            reconnect.setEnabled(false);
        }

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

            id = addMatch(startTime, "-", num, "-");

            Intent i = new Intent(MainActivity.this, TableActivity.class);

            SharedPreferences profs = getSharedPreferences("state", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = profs.edit();
            editor.putLong("id",id);
            editor.apply();

            startActivity(i);
        }
    }

    public void Reconnect(View view){
        Intent i = new Intent(MainActivity.this, TableActivity.class);
        startActivity(i);
    }


}
