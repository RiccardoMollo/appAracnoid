package com.example.utente.apparacnoid;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Utente on 24/05/2017.
 */

public class myDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    public myDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}

