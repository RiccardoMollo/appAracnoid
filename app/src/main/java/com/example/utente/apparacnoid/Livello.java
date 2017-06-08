package com.example.utente.apparacnoid;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Livello extends AppCompatActivity implements Callback {
    int contatore=0;
    int puntiLettera=0;
    int puntiParole=0;
    int r;
    int g;
    int b;
    int livello;
    //int puntiTempo;

    private myDBManager mydbm;

    private Button button_send;
    EditText et;
    TextView tv;
    String[] chars = {"A","B","C","D","E","F","G","I","L","M","N","O","P","R","S","T"};

    private static TextView txtCountDown;
    private static final long startTime = 30 * 1000;
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

        mydbm = new myDBManager(Livello.this);

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


        button_send = (Button) findViewById(R.id.sender);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressSend();
            }
        });
        button_send.setEnabled(false);
        txtCountDown = (TextView) findViewById(R.id.txtCountDown);
        txtCountDown.setText(String.valueOf(""+ startTime / 1000));


        et = (EditText) findViewById(R.id.parolaInserita);
        et.setText("");
        et.setEnabled(false);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        tv = (TextView) findViewById(R.id.letter);

        showCountdown();
    }

    public void showCountdown(){
        LayoutInflater inflater = getLayoutInflater();
        View layout_toast_1 = inflater.inflate(R.layout.custom_toast_1,
                (ViewGroup) findViewById(R.id.custom_toast_container_1));

        final Toast toast_1 = new Toast(getApplicationContext());
        toast_1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast_1.setDuration(Toast.LENGTH_SHORT);
        toast_1.setView(layout_toast_1);

        View layout_toast_2 = inflater.inflate(R.layout.custom_toast_2,
                (ViewGroup) findViewById(R.id.custom_toast_container_2));

        final Toast toast_2 = new Toast(getApplicationContext());
        toast_2.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast_2.setDuration(Toast.LENGTH_SHORT);
        toast_2.setView(layout_toast_2);

        View layout_toast_3 = inflater.inflate(R.layout.custom_toast_3,
                (ViewGroup) findViewById(R.id.custom_toast_container_3));

        final Toast toast_3 = new Toast(getApplicationContext());
        toast_3.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast_3.setDuration(Toast.LENGTH_SHORT);
        toast_3.setView(layout_toast_3);

        toast_3.show();
        toast_2.show();
        toast_1.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                launchTimerAndLetter();
            }
        }, 6500);

    }

    public void launchTimerAndLetter(){
        startTimer();
        tv.setText(chars[(int) (Math.random() * 16)]);
        et.setEnabled(true);
        et.requestFocus();
        button_send.setEnabled(true);
    }

    public void startTimer(){
        countDownTimer = new MyCountDownTimer(startTime, interval, Livello.this);
        countDownTimer.start();
    }

// REAZIONI ALLA PRESSIONE DEL TASTO DI INVIO
    public void pressSend(){
        EditText et = (EditText) findViewById(R.id.parolaInserita);
        TextView tv = (TextView) findViewById(R.id.letter);
        String confronto = tv.getText().toString();
        String parolaInserita= et.getText().toString();
               et.setHint("inserisci la parola");
        et.setText("");

        TextView counter = (TextView) findViewById(R.id.counter);
        if (parolaInserita.startsWith(confronto) && mydbm.getWordMatches(parolaInserita)){
                puntiLettera+= (parolaInserita.length()*10);
                contatore++;
                coloraLinea(contatore);
                counter.setText(Integer.toString(contatore));
                puntiParole=20*contatore;
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
            intent.putExtra("messagePunti", puntiLivello);
            intent.putExtra("messageParole", contatore);
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
                //String messagePunti  = String.valueOf(puntiLivello);
                intent.putExtra("messagePunti", puntiLivello);
                intent.putExtra("messageParole", contatore);
                intent.putExtra("jsonArray",pixels_array.toString());

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
                    coloraLed(0,6,45,52);
                    break;
                case 2:
                    coloraLed(52,58,179,185);
                    break;
                case 3:
                    coloraLed(186,192,311,318);
                    break;
                case 4:
                    coloraLed(318,324,417,424);
                    break;
                case 5:
                    coloraLed(424,430,515,522);
                    break;
            }
        }
        if(livello==2){
            switch (i){
                case 1:
                    coloraLed(6,14,37,45);
                    break;
                case 2:
                    coloraLed(58,66,171,179);
                    break;
                case 3:
                    coloraLed(192,200,303,311);
                    break;
                case 4:
                    coloraLed(324,332,409,417);
                    break;
                case 5:
                    coloraLed(430,438,507,515);
                    break;
            }
        }


        if(livello==3){
        switch (i){
            case 1:
                coloraLed(14,26,26,37);
                break;
            case 2:
                coloraLed(66,118,118,171);
                break;
            case 3:
                coloraLed(200,250,250,303);
                break;
            case 4:
                coloraLed(332,370,370,409);
                break;
            case 5:
                coloraLed(438,472,472,507);
                break;
        }
    }

}

    void coloraLed(int inizio1, int fine1,int inizio2, int fine2) {
        Handler handler = new Handler();
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                pressSend();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }





}
