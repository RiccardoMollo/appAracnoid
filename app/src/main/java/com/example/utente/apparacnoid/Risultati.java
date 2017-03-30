package com.example.utente.apparacnoid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Risultati extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati);


        Bundle datiPassati = getIntent().getExtras();
        String punteggio =datiPassati.getString("messagePunti");
        String paroleInserite =datiPassati.getString("messageParole");

        TextView tvPunti = (TextView) findViewById(R.id.puntiLivello);
        tvPunti.setText(punteggio );


        TextView tvParole = (TextView) findViewById(R.id.numeroParole);
        tvParole.setText(paroleInserite);

    }






}
