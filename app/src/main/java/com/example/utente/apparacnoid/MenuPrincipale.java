package com.example.utente.apparacnoid;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MenuPrincipale extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principale);
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
