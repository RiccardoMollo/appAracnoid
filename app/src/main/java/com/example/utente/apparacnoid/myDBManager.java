package com.example.utente.apparacnoid;

import android.content.Context;

/**
 * Created by Utente on 24/05/2017.
 */

public class myDBManager {
    private myDBHelper dbhelper;

    public myDBManager(Context context){
        dbhelper=new myDBHelper(context);
    }

}
