package com.example.cards2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "MATCHES";
    private static final String TABLE_NAME2 = "PARTICIPANTS";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "start TEXT, ends TEXT, nplayers INTEGER, result TEXT)";
        db.execSQL(createTable);
        String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "participant TEXT, hand TEXT, score INTEGER, "+
                "MATCHES_id INTEGER, FOREIGN KEY(MATCHES_id) REFERENCES " + TABLE_NAME + "(MATCHES_id))";
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP IF TABLE EXISTS ";
        db.execSQL(drop + TABLE_NAME);
        db.execSQL(drop + TABLE_NAME2);
        onCreate(db);
    }

    public Cursor getMatchContent(int match_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=" + match_id, null);
        data.moveToFirst();
        return data;
    }

    public long AddMatch(String fStart, String fEnds, int fPlayers, String fResult){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("start", fStart);
        contentValues.put("ends", fEnds);
        contentValues.put("nplayers", fPlayers);
        contentValues.put("result", fResult);

        long MATCHID = db.insert(TABLE_NAME,null,contentValues);

        return MATCHID;
    }

    public boolean AddPart(String jPart, String jHand, String jScore, int jMID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("participant", jPart);
        contentValues.put("hand", jHand);
        contentValues.put("score", jScore);
        contentValues.put("MATCHES_id", jMID);

        long result = db.insert(TABLE_NAME2,null,contentValues);

        if(result==-1){
            return false;
        } else {
            return true;
        }
    }

}
