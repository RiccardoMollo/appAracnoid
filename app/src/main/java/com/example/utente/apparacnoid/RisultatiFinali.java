package com.example.utente.apparacnoid;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RisultatiFinali extends AppCompatActivity {

    int risultato;
    TextView risultati_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_finali);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        risultati_tv = (TextView) findViewById(R.id.risultatiFinali);
        risultato = settings.getInt("punteggioFinale",0);
        risultati_tv.setText("Punteggio finale: " + risultato);
    }
}
