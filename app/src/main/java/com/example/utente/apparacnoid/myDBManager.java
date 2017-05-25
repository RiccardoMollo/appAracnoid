package com.example.utente.apparacnoid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class myDBManager {
    private myDBHelper dbhelper;

    public myDBManager(Context context){
        dbhelper=new myDBHelper(context);
    }

    public boolean getWordMatches(String wordSearched) {

        String selection = "word" + " = ?";
        String[] selectionArgs = new String[] {wordSearched.toLowerCase()};
        //String[] columns = new String[]{"word"};
        String[] columns = null;

        return queryWord(selection, selectionArgs, columns);
    }

    private boolean queryWord(String selection, String[] selectionArgs, String[] columns) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();


        Cursor cursor = db.query("words",
                columns, selection, selectionArgs, null, null, null);



        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }
}



