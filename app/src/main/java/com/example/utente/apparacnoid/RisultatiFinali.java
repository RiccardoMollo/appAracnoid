package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

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
    ListView elenco_lv;
    myArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "Comfortaa-Regular.ttf");
        setContentView(R.layout.activity_risultati_finali);
        elenco_lv = (ListView) findViewById(R.id.elenco);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        risultati_tv = (TextView) findViewById(R.id.risultatiFinali);
        risultati_tv.setTypeface(typeface);
        risultato = settings.getInt("punteggioFinale",0);
        nomeGiocatore = settings.getString("nome","");
        listaGiocatori = getListaGiocatori(settings);
        if(listaGiocatori.isEmpty()){
            creaLaPrimaListagiocatori(settings);
            listaGiocatori = getListaGiocatori(settings);
        }

        risultati_tv.setText("Punteggio finale di " + nomeGiocatore + " : " + risultato);
        giocatore=new Giocatore(nomeGiocatore,risultato);
        //listaGiocatori = getListaGiocatori(settings);

        listaGiocatori.add(giocatore);
        Collections.sort(listaGiocatori);
        saveListaGiocatori(settings,listaGiocatori);
        adapter = new myArrayAdapter(this, R.layout.row, listaGiocatori);
        elenco_lv.setAdapter(adapter);
        Button b1=(Button) findViewById(R.id.replay);
        Button b2=(Button) findViewById(R.id.exit);
        b1.setTypeface(typeface);
        b2.setTypeface(typeface);
    }

    private void creaLaPrimaListagiocatori(SharedPreferences settings) {
        ArrayList<Giocatore>lista=new ArrayList<>();
        lista.add(new Giocatore("Riky",1220));
        lista.add(new Giocatore("Fra",960));
        lista.add(new Giocatore("Ric",910));
        lista.add(new Giocatore("Marty",500));
        lista.add(new Giocatore("Ale",360));
        lista.add(new Giocatore("Sam",540));
        lista.add(new Giocatore("Cri",740));
        lista.add(new Giocatore("Joe",980));
        lista.add(new Giocatore("Fra B.",780));
        lista.add(new Giocatore("Rob",1010));
        SharedPreferences.Editor prefsEditor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        prefsEditor.putString("listaGiocatori", json);
        prefsEditor.apply();
    }

    private void saveListaGiocatori(SharedPreferences settings,ArrayList listagiocatori) {

        SharedPreferences.Editor prefsEditor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listagiocatori);
        prefsEditor.putString("listaGiocatori", json);
        prefsEditor.apply();
    }

    private void provaLista(ArrayList<Giocatore> listaGiocatori) {
        Typeface typeface=Typeface.createFromAsset(getAssets(), "Comfortaa-Regular.ttf");
        if(listaGiocatori.size()!=0){
            String sequenzaNomi="";
            for(int i=0; i<10;i++){
                sequenzaNomi = sequenzaNomi + listaGiocatori.get(i).getNome();
            }
            risultati_tv.setText(sequenzaNomi);
            risultati_tv.setTypeface(typeface);
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
