package com.example.utente.apparacnoid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Utente on 30/03/2017.
 */

public class WordsDataSource {
    private SQLiteDatabase databese;
    private  dataBaseHelper dbHelper;

    public void WordsDataSource (Context context) {
        dbHelper = new dataBaseHelper(context);
    }
    public void open() throws SQLException{
        databese = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public void putString (String word){
        ContentValues values = new ContentValues();
        values.put(dataBaseHelper.COLUMN_WORD, word);
        long idWord = databese.insert(dataBaseHelper.TABLE_WORDS, null, values);
    }
}
