package com.example.utente.apparacnoid;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_WORDS = "words";
    public static final String COLUMN_WORD ="word";
    private static final String String_DATABASE_NAME = "dizionario.db";
    private static final int DATABASE_VERSION = 1;

    private  static final String DATABASE_CREATE = "create table "
                    + TABLE_WORDS + "( " + COLUMN_WORD + " parole del dizionario);";

    public dataBaseHelper(Context context){
        super(context, String_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
