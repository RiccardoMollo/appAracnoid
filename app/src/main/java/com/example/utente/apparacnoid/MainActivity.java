package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nome_et;
    Button scegli_colore;
    Button avvia;
    WordsDataSource dizionario;
    int[] lista_colori= new int[]{Color.argb(255,200,0,0),Color.argb(255,0,255,0),Color.argb(255,0,0,255)};
    int i=0;
    int colore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome_et=(EditText) findViewById(R.id.nome_et);
        scegli_colore=(Button) findViewById(R.id.scegli_colore);
        avvia = (Button) findViewById(R.id.inizia);
        dizionario = new WordsDataSource(this);
        dizionario.open();
        try {
            dizionario.fillDictionary(this);
            nome_et.setHint("dizionario riempito");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void change(View view) {
        //scegli_colore.getBackground().setColorFilter(lista_colori[i], PorterDuff.Mode.SRC_ATOP);
        colore = lista_colori[i];
        scegli_colore.setBackgroundColor(colore);
        i++;
        if(i==3){
            i=0;
        }
    }

    public void start (View view){
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("nome",nome_et.getText().toString() );
        editor.apply();
        editor.putInt("colore",colore);
        editor.apply();
        editor.putInt("livello",1);
        editor.apply();

        if (nome_et.getText().toString().matches("")) {
            //Intent intent = new Intent(this, Livello.class);
            //startActivity(intent);
            nome_et.setHint("INSERIRE NOME UTENTE");
        }
        else {

            Intent intent = new Intent(this, Livello.class);
            startActivity(intent);

        }
    }

}



