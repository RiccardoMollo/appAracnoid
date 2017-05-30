package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RisultatiFinali extends AppCompatActivity {

    int risultato;
    TextView risultati_tv;
    String nomeGiocatore;
    Giocatore giocatore;
    ArrayList<Giocatore> listaGiocatori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_finali);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        risultati_tv = (TextView) findViewById(R.id.risultatiFinali);
        risultato = settings.getInt("punteggioFinale",0);
        nomeGiocatore = settings.getString("nome","");
        risultati_tv.setText("Punteggio finale di " + nomeGiocatore + " : " + risultato);
        giocatore=new Giocatore(nomeGiocatore,risultato);
        listaGiocatori = getListaGiocatori(settings);
        listaGiocatori.add(giocatore);
        Collections.sort(listaGiocatori);
        provaLista(listaGiocatori);
        saveListaGiocatori(settings,listaGiocatori);




    }

    private void saveListaGiocatori(SharedPreferences settings,ArrayList listagiocatori) {

        SharedPreferences.Editor prefsEditor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listagiocatori);
        prefsEditor.putString("listaGiocatori", json);
        prefsEditor.apply();
    }

    private void provaLista(ArrayList<Giocatore> listaGiocatori) {
        if(listaGiocatori.size()!=0){
            String sequenzaNomi="";
            for(int i=0; i<listaGiocatori.size();i++){
                sequenzaNomi = sequenzaNomi + listaGiocatori.get(i).getNome();
            }
            risultati_tv.setText(sequenzaNomi);
        }
    }

    private ArrayList<Giocatore> getListaGiocatori(SharedPreferences settings) {
        ArrayList <Giocatore> listaG;
        Gson gson = new Gson();
        String json = settings.getString("listaGiocatori", "");
        if (json.isEmpty()) {
            listaG = new ArrayList<Giocatore>();
        } else {
            Type type = new TypeToken<List<Giocatore>>() {
            }.getType();
            listaG = gson.fromJson(json, type);
        }
        return listaG;
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
