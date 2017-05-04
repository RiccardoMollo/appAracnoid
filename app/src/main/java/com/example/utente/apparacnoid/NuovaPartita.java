package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NuovaPartita extends AppCompatActivity {

    EditText nome_et;
    Button scegli_colore;
    Button avvia;
    int[] lista_colori= new int[]{Color.argb(255,200,0,0),Color.argb(255,0,255,0),Color.argb(255,0,0,255)};
    int [] coloriDisplay = new int[]{200,0,0,0,255,0,0,0,255};
    int i=0;
    int colore=0;
    int r=0;
    int g=0;
    int b=0;

    JSONArray pixels_array;

    Unbinder unbinder;

    private Handler mNetworkHandler, mMainHandler;
    private NetworkThread mNetworkThread = null;
    private String host_url ;
    private int host_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovapartita);

        unbinder = ButterKnife.bind(this);

        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        host_port =  settings.getInt("host_port",0);
        host_url = settings.getString("host_url", "null"); 

        nome_et=(EditText) findViewById(R.id.nome_et);
        scegli_colore=(Button) findViewById(R.id.scegli_colore);
        avvia = (Button) findViewById(R.id.inizia);

        pixels_array = preparePixelsArray();

        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(NuovaPartita.this, (String) msg.obj, Toast.LENGTH_LONG).show();
            }
        };

        startHandlerThread();
        setDisplayStart();

    }

    public void startHandlerThread() {
        mNetworkThread = new NetworkThread(mMainHandler);
        mNetworkThread.start();
        mNetworkHandler = mNetworkThread.getNetworkHandler();

        Message msg = mNetworkHandler.obtainMessage();
        msg.what = NetworkThread.SET_SERVER_DATA;
        msg.obj = host_url;
        msg.arg1 = host_port;
        msg.sendToTarget();

        handleNetworkRequest(NetworkThread.SET_SERVER_DATA, host_url, host_port ,0);

    }

    public void change(View view) {
        //scegli_colore.getBackground().setColorFilter(lista_colori[i], PorterDuff.Mode.SRC_ATOP);
        colore = lista_colori[i];
        scegli_colore.setBackgroundColor(colore);

        setDisplayPlayerColor();
        i++;
        if(i==3){
            i=0;
        }
    }

    public void start (View view){
        //salva il nome del giocatore e il colore scelto
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("nome",nome_et.getText().toString() );
        editor.apply();
        editor.putInt("colore",colore);
        editor.apply();
        editor.putInt("r",r);
        editor.apply();
        editor.putInt("g",g);
        editor.apply();
        editor.putInt("b",b);
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
            intent.putExtra("jsonArray",pixels_array.toString());
            startActivity(intent);

        }
    }

    void setDisplayPlayerColor() {
        try {
            JSONArray pixels_array = preparePixelsArray();

            if(i==0){
                r=coloriDisplay[0];
                g=coloriDisplay[1];
                b=coloriDisplay[2];
            }
            else{
                if(i==1){
                    r=coloriDisplay[3];
                    g=coloriDisplay[4];
                    b=coloriDisplay[5];
                }
                else{
                    r=coloriDisplay[6];
                    g=coloriDisplay[7];
                    b=coloriDisplay[8];
                }
            }

            for (int i = 0; i < pixels_array.length(); i++) {
                ((JSONObject) pixels_array.get(i)).put("r", r);
                ((JSONObject) pixels_array.get(i)).put("g", g);
                ((JSONObject) pixels_array.get(i)).put("b", b);
            }
            handleNetworkRequest(NetworkThread.SET_DISPLAY_PIXELS, pixels_array, 0 ,0);
        } catch (JSONException e) {
            // There should be no Exception
        }
    }

    void setDisplayStart() {
        //DISPLAY
        try {

            int r = 255;
            int g = 255;
            int b = 0;

            for (int i = 0; i < pixels_array.length(); i++) {
                ((JSONObject) pixels_array.get(i)).put("r", r);
                ((JSONObject) pixels_array.get(i)).put("g", g);
                ((JSONObject) pixels_array.get(i)).put("b", b);
            }
            handleNetworkRequest(NetworkThread.SET_DISPLAY_PIXELS, pixels_array, 0 ,0);
        } catch (JSONException e) {
            // There should be no Exception
        }

        //RAGNATELA
        try {

            int r = 255;
            int g = 255;
            int b = 0;

            for (int i = 0; i < pixels_array.length(); i++) {
                ((JSONObject) pixels_array.get(i)).put("r", r);
                ((JSONObject) pixels_array.get(i)).put("g", g);
                ((JSONObject) pixels_array.get(i)).put("b", b);
            }
            handleNetworkRequest(NetworkThread.SET_PIXELS, pixels_array, 0 ,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONArray preparePixelsArray() {
        JSONArray pixels_array = new JSONArray();
        JSONObject tmp;
        try {
            for (int i = 0; i < 1072; i++) {
                tmp = new JSONObject();
                tmp.put("a", 0);
                if (i < 522) {
                    tmp.put("g", 255);
                    tmp.put("b", 0);
                    tmp.put("r", 0);
                } else if (i < 613) {
                    tmp.put("r", 255);
                    tmp.put("g", 0);
                    tmp.put("b", 0);
                } else if (i < 791) {
                    tmp.put("b", 255);
                    tmp.put("g", 0);
                    tmp.put("r", 0);
                } else {
                    tmp.put("b", 255);
                    tmp.put("g", 0);
                    tmp.put("r", 255);
                }
                pixels_array.put(tmp);
            }
        } catch (JSONException exception) {
            // No errors expected here
        }
        return pixels_array;
    }

    private void handleNetworkRequest(int what, Object payload, int arg1, int arg2) {
        Message msg = mNetworkHandler.obtainMessage();
        msg.what = what;
        msg.obj = payload;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.sendToTarget();
    }

}



