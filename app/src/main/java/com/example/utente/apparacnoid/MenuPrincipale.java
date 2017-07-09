package com.example.utente.apparacnoid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MenuPrincipale extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principale);

        //cambiare il font di testo e bottoni
        Typeface typeface=Typeface.createFromAsset(getAssets(), "Comfortaa-Regular.ttf");
        TextView menu = (TextView) findViewById(R.id.menu);
        menu.setTypeface(typeface);
        Button nuovapartita = (Button) findViewById(R.id.nuovapartita);
        Button regole = (Button) findViewById(R.id.regole);
        nuovapartita.setTypeface(typeface);
        regole.setTypeface(typeface);
    }


    public void clickNuovaPartita(View view) {

            Intent intent = new Intent(this, NuovaPartita.class);

            startActivity(intent);
            finish();

    }


    public void clickRegole(View view) {

        Intent intent = new Intent(this, Regole.class);
        startActivity(intent);

    }

    /*
    public void clickNext(View view) {

        Intent intent = new Intent(this, HighestScore.class);
        startActivity(intent);
        finish();

    }*/
}
