package com.example.cards2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TableActivity extends AppCompatActivity {

    Deck deck;
    DBHelper DB;
    TextView textView;
    Matches matches;
    Cursor data;
    Map<String, ArrayList<String>> handz;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11;
    List<ImageView> imgs;
    String strk;
    ImageView[] imageViews = new ImageView[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        deck = new Deck();
        DB = new DBHelper(TableActivity.this);
        textView = findViewById(R.id.textView);

        for(int u = 0; u < 11; u++){
            String imageID = "imageView" + (u+1);
            int resID = getResources().getIdentifier(imageID,"id",getPackageName());
            imageViews[u] = findViewById(resID);
        }

        Intent receivedIntent = getIntent();
        long match = receivedIntent.getLongExtra("MATCH_ID",0);

        strk = String.valueOf(match);

        data = DB.getMatchContent(Integer.parseInt(strk));
        matches = new Matches(data.getInt(0),data.getString(1),
                data.getString(2),data.getInt(3),
                data.getString(4));

        handz = deck.dealHands(matches.getNplayers());

        addPart("Deck", deck.arrayDeck().toString(),"",matches.getId());

        for(int z = 0; z < matches.getNplayers(); z++){
            addPart(String.valueOf(z+1), Objects.requireNonNull(handz.get("n" + (z + 1))).toString(),"",matches.getId());
        }

        dealHand();

    }

    public void addPart(String bPnum, String bHand, String bScore, int bID){
        boolean  result = DB.AddPart(bPnum,bHand,bScore,bID);
        if(result){
            Toast.makeText(this, "Data succesfully added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding data", Toast.LENGTH_SHORT).show();
        }
    }

    public void Sort(View view){
        Collections.sort(Objects.requireNonNull(handz.get("n1")));
        dealHand();
        updateData("1",handz.get("n1").toString(),"",strk);

    }

    public void dealHand(){

        for(int k = 0; k < 11; k++) {
            String fnm = Objects.requireNonNull(handz.get("n1")).get(k);
            final ImageView img = imageViews[k];
            String PACKAGE_NAME = getApplicationContext().getPackageName();
            int imgId = getResources().getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
            img.setImageBitmap(BitmapFactory.decodeResource(getResources(),imgId));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
                    if(params.gravity==Gravity.TOP){
                        params.gravity = Gravity.BOTTOM;
                    } else {
                        params.gravity = Gravity.TOP;
                    }
                    img.setLayoutParams(params);
                }
            });
        }

    }

    public void updateData(String vPart, String vHand, String vScore, String vMatch){
        boolean  result = DB.UpdatePart(vPart,vHand,vScore,vMatch);
        if(result){
            Toast.makeText(this, "Data succesfully added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding data", Toast.LENGTH_SHORT).show();
        }
    }



}
