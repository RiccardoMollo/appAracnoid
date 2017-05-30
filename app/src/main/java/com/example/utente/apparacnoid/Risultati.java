package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Risultati extends AppCompatActivity {

    JSONArray pixels_array;

    Unbinder unbinder;

    private Handler mNetworkHandler, mMainHandler;
    private NetworkThread mNetworkThread = null;
    private String host_url ;
    private int host_port;

    int r;
    int g;
    int b;
    int livello;
    int punteggioFinale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati);

        unbinder = ButterKnife.bind(this);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        host_port =  settings.getInt("host_port",0);
        host_url = settings.getString("host_url", "null");
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(Risultati.this, (String) msg.obj, Toast.LENGTH_LONG).show();
            }
        };



        SharedPreferences.Editor editor = settings.edit();
        livello =  settings.getInt("livello",0);
        punteggioFinale = settings.getInt("punteggioFinale",0);

        r=settings.getInt("r",0);
        g=settings.getInt("g",0);
        b=settings.getInt("b",0);

        Intent datiPassati = getIntent();

        int punteggioLivello = datiPassati.getIntExtra("messagePunti",0);
        int contatore =datiPassati.getIntExtra("messageParole",0);
        String jsonArray = datiPassati.getStringExtra("jsonArray");
        try{
            pixels_array = new JSONArray(jsonArray);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //String livello =datiPassati.getString("messageLivello");

        TextView tvLivello = (TextView)findViewById(R.id.titolo);
        tvLivello.setText("Risultati livello "+livello);
        TextView tvPunti = (TextView) findViewById(R.id.puntiLivello);
        tvPunti.setText("Hai totalizzato "+punteggioLivello+" punti" );
        TextView tvParole = (TextView) findViewById(R.id.numeroParole);
        tvParole.setText("Parole inserite: "+ contatore+"/5");


        editor.putInt("livello",livello+1);
        editor.apply();
        editor.putInt("punteggioFinale",punteggioFinale+punteggioLivello);
        editor.apply();

        startHandlerThread();
        coloraAnello(contatore,livello);


    }

    void coloraAnello(int cont, int lev){

        if(lev==1){
            switch (cont){
                case 1:
                    coloraLed(522,541);
                    break;
                case 2:
                    coloraLed(522,552);
                    break;
                case 3:
                    coloraLed(522,586);
                    break;
                case 4:
                    coloraLed(522,598);
                    break;
                case 5:
                    coloraLed(522,613);
                    break;
            }
        }
        if(lev==2){
            switch (cont){
                case 1:
                    coloraLed(613,648);
                    break;
                case 2:
                    coloraLed(613,670);
                    break;
                case 3:
                    coloraLed(613,737);
                    break;
                case 4:
                    coloraLed(613,759);
                    break;
                case 5:
                    coloraLed(613,791);
                    break;
            }
        }
        if(lev==3){
            switch(cont){
                case 1:

                coloraLed(791,845);
                break;
            case 2:
                coloraLed(791,880);
                break;
            case 3:
                coloraLed(791,985);
                break;
            case 4:
                coloraLed(791,1020);
                break;
            case 5:
                coloraLed(791,1072);
                break;
            }
        }


}

    void coloraLed(int inizio, int fine) {

        //RAGNATELA
        try {
            for (int i = inizio; i < fine; i++) {
                ((JSONObject) pixels_array.get(i)).put("r", r);
                ((JSONObject) pixels_array.get(i)).put("g", g);
                ((JSONObject) pixels_array.get(i)).put("b", b);
            }
            handleNetworkRequest(NetworkThread.SET_PIXELS, pixels_array, 0 ,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void handleNetworkRequest(int what, Object payload, int arg1, int arg2) {
        Message msg = mNetworkHandler.obtainMessage();
        msg.what = what;
        msg.obj = payload;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.sendToTarget();
    }



    public void newLevel(View view){

        if ( livello ==3){
            Intent finale = new Intent(this, RisultatiFinali.class);
            startActivity(finale);
            finish();
        }
        else {
            Intent intent = new Intent(this, Livello.class);
            intent.putExtra("jsonArray",pixels_array.toString());
            startActivity(intent);
        }
        finish();

    }



}
