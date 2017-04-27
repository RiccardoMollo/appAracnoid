package com.example.utente.apparacnoid;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Livello extends AppCompatActivity {
    int contatore=0;
    int puntiLettera=0;
    int puntiParole=0;
    int r;
    int g;
    int b;
    int livello;
    //int puntiTempo;


    private static TextView txtCountDown;
    private static final long startTime = 20 * 1000;
    private static final long interval = 1000;
    CountDownTimer countDownTimer;



    Unbinder unbinder;
    private Handler mNetworkHandler, mMainHandler;
    private NetworkThread mNetworkThread = null;
    private String host_url ;
    private int host_port;
    JSONArray pixels_array=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livello);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("jsonArray");

        try{
            pixels_array = new JSONArray(jsonArray);
        }catch (JSONException e){
            e.printStackTrace();
        }



        SharedPreferences settings = getSharedPreferences("Settings",0);
        SharedPreferences.Editor editor = settings.edit();
        r=settings.getInt("r",0);
        g=settings.getInt("g",0);
        b=settings.getInt("b",0);
        livello=settings.getInt("livello",0);


        unbinder = ButterKnife.bind(this);
        host_port =  settings.getInt("host_port",0);
        host_url = settings.getString("host_url", "null");
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(Livello.this, (String) msg.obj, Toast.LENGTH_LONG).show();
            }
        };

        startHandlerThread();


        EditText et = (EditText) findViewById(R.id.parolaInserita);

        et.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        TextView tv = (TextView) findViewById(R.id.letter);

        String[] chars = {"A","B","C","D","E","F","G","I","L","M","N","O","P","R","S","T"};
        tv.setText(chars[(int) (Math.random() * 17)]);

// INIZIALIZZAZIONE VARIABILE TIMER

        txtCountDown = (TextView) findViewById(R.id.txtCountDown);
        countDownTimer = new MyCountDownTimer(startTime, interval, this);
        if(txtCountDown!=null){
            txtCountDown.setText(String.valueOf(""+ startTime / 1000));
        }
        countDownTimer.start();
    }

// REAZIONI ALLA PRESSIONE DEL TASTO DI INVIO


    public void pressSend(View view){
        EditText et = (EditText) findViewById(R.id.parolaInserita);
        TextView tv = (TextView) findViewById(R.id.letter);
        String confronto = tv.getText().toString();
        String parolaInserita= et.getText().toString();
               et.setHint("inserisci la parola");

        //String punti = String.valueOf(puntiLettera);
        // et.setHint(punti);
        et.setText(null);

        TextView counter = (TextView) findViewById(R.id.counter);
        if (parolaInserita.startsWith(confronto)){
                puntiLettera+= (parolaInserita.length()*10);
                contatore++;
                coloraLinea(contatore);
                counter.setText(Integer.toString(contatore));
                puntiParole=20*contatore;
                //String punti = String.valueOf(puntiParole);
                //et.setHint(punti);
        }
        else{
            et.setHint("parola non valida!");
            counter.setText(Integer.toString(contatore));
        }

// INSERIMENTO DELLA QUINTA PAROLA //

        if (contatore==5){

            countDownTimer.cancel();

            int puntiLivello =puntiLettera+puntiParole;
            Intent intent = new Intent (this, Risultati.class);

           // String messageLivello=String.valueOf(livello);
            String messageParole = String.valueOf(contatore);
            String messagePunti  = String.valueOf(puntiLivello);

            intent.putExtra("messagePunti", messagePunti);
            intent.putExtra("messageParole", messageParole);
            intent.putExtra("jsonArray",pixels_array.toString());
           // intent.putExtra("messageLivello",messageLivello);


            startActivity(intent);
            finish();
        }
    }

// TIMER ____ cambia activity quando il countdown arriva a zero

    public class MyCountDownTimer extends CountDownTimer {

        Context c;

        public MyCountDownTimer(long startTime, long interval, Context c) {
            super(startTime, interval);
            this.c = c;
        }


// IL TIMER RAGGIUNGE LO ZERO //
        @Override
        public void onFinish() {
            if(txtCountDown!=null){
                int puntiLivello = puntiLettera+puntiParole;

                Intent intent = new Intent (c, Risultati.class);


                String messagePunti  = String.valueOf(puntiLivello);

                intent.putExtra("messagePunti", messagePunti);
                intent.putExtra("messageParole", contatore);

                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(txtCountDown!=null){

                    txtCountDown.setText("" + millisUntilFinished / 1000);

            }
        }
    }

// FINE TIMER


    void coloraLinea(int i){

        if(livello==1){
            switch (i){
                case 1:
                    coloraLed(0,6,45,51);
                    break;
                case 2:
                    coloraLed(52,58,179,185);
                    break;
                case 3:
                    coloraLed(186,192,311,317);
                    break;
                case 4:
                    coloraLed(318,324,417,423);
                    break;
                case 5:
                    coloraLed(424,430,515,521);
                    break;
            }
        }
    }

    void coloraLed(int inizio1, int fine1,int inizio2, int fine2) {

        //RAGNATELA
        try {


            for (int i = inizio1; i < fine1; i++) {
                ((JSONObject) pixels_array.get(i)).put("r", r);
                ((JSONObject) pixels_array.get(i)).put("g", g);
                ((JSONObject) pixels_array.get(i)).put("b", b);
            }
            for (int i = inizio2; i < fine2; i++) {
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





}
