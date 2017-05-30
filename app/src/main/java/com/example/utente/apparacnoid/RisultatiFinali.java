package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    public void replay(View view) {

        Intent intent = new Intent(this, NuovaPartita.class);

        startActivity(intent);
        finish();

    }
    public void exitGame(View view) {
        finish();

    }
}
