package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;

public class MenuPrincipale extends AppCompatActivity {

    private String host_url ;
    private int host_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principale);
    }

    @OnClick(R.id.nuovapartita)
    public void clickNuovaPartita(View view) {

            Intent intent = new Intent(this, NuovaPartita.class);
            SharedPreferences settings = getSharedPreferences("Settings", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("host_url", host_url);
            editor.apply();
            editor.putInt("host_port", host_port);
            editor.apply();
            startActivity(intent);
            finish();

    }

    @OnClick(R.id.regole)
    public void clicRegole(View view) {

        Intent intent = new Intent(this, Regole.class);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("host_url", host_url);
        editor.apply();
        editor.putInt("host_port", host_port);
        editor.apply();
        startActivity(intent);
        finish();

    }

    /*@OnClick(R.id.score)
    public void clickNext(View view) {

        Intent intent = new Intent(this, HighestScore.class);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("host_url", host_url);
        editor.apply();
        editor.putInt("host_port", host_port);
        editor.apply();
        startActivity(intent);
        finish();

    }*/
}
