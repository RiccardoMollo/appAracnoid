package com.example.utente.apparacnoid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class WordsDataSource {
    private SQLiteDatabase database;
    private  dataBaseHelper dbHelper;
    private String[] allColumns = {dataBaseHelper.COLUMN_WORD};
    private InputStream in;
    private BufferedReader reader;
    private String line;

    public WordsDataSource (Context context) {
        dbHelper = new dataBaseHelper(context);
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public void putString (String word){
        ContentValues values = new ContentValues();
        values.put(dataBaseHelper.COLUMN_WORD, word);
        long idWord = database.insert(dataBaseHelper.TABLE_WORDS, null, values);
    }

    public void fillDictionary (Context context) throws IOException {
        in = context.getAssets().open("Dizionario");
        reader = new BufferedReader(new InputStreamReader(in));
        line = reader.readLine();
        if(line!=null){
            putString(line);
        }
        else{
            reader.close();
        }
    }

    public boolean isCorrectedWord (String w){
        Cursor cursor = database.query(dbHelper.TABLE_WORDS, allColumns, dbHelper.COLUMN_WORD + "=?", new String[] {w}, null, null, null, null);
        if(cursor!=null){
            cursor.close();
            return true;
        }
        else {
            return false;
        }
    }
}
