package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Risultati extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati);

        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        int livello =  settings.getInt("livello",0);

        Bundle datiPassati = getIntent().getExtras();

        String punteggio =datiPassati.getString("messagePunti");
        String paroleInserite =datiPassati.getString("messageParole");
        //String livello =datiPassati.getString("messageLivello");

        TextView tvLivello = (TextView)findViewById(R.id.titolo);
        tvLivello.setText("Risultati livello "+livello);

        TextView tvPunti = (TextView) findViewById(R.id.puntiLivello);
        tvPunti.setText("Hai totalizzatio "+punteggio+" punti" );


        TextView tvParole = (TextView) findViewById(R.id.numeroParole);
        tvParole.setText("Parole inserite: "+ paroleInserite+"/5");



        editor.putInt("livello",livello+1);
        editor.apply();


    }



    public void newLevel(View view){

        Intent intent = new Intent(this, Livello.class);
        startActivity(intent);

    }



}
