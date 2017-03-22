package com.example.utente.apparacnoid;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class Livello extends AppCompatActivity {
    int i=0;

    private static TextView txtCountDown;
    private static CountDownTimer countDownTimer = null;
    private static final long startTime = 50 * 1000;
    private static final long interval = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livello);

        EditText et = (EditText) findViewById(R.id.parolaInserita);

        et.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        TextView tv = (TextView) findViewById(R.id.letter);

        String[] chars = {"a","b","c","d","e","f","g","i","l","m","n","o","p","r","s","t","u","v","z"};
        tv.setText(chars[(int) (Math.random() * 19)]);

// INIZIALIZZAZIONE VARIABILE TIMER
        txtCountDown = (TextView) findViewById(R.id.txtCountDown);
        countDownTimer = new MyCountDownTimer(startTime, interval, this);
        if(txtCountDown!=null){
            txtCountDown.setText(String.valueOf(""+ startTime / 1000));
        }
        countDownTimer.start();

    }

    public void pressSend(View view){
        EditText et = (EditText) findViewById(R.id.parolaInserita);
        et.setText(null);
        et.setHint("inserisci la parola");
        TextView counter = (TextView) findViewById(R.id.counter);
        String parolaInserita= et.getText().toString();
            i++;
            counter.setText(Integer.toString(i));

        if (i==5){
            Intent intent = new Intent (this, Risultati.class);
            startActivity(intent);
        }
    }

// TIMER ____ cambia activity quando il countdown arriva a zero

    public class MyCountDownTimer extends CountDownTimer {

        Context c;

        public MyCountDownTimer(long startTime, long interval, Context c) {
            super(startTime, interval);
            this.c = c;
        }

        @Override
        public void onFinish() {
            if(txtCountDown!=null){
                Intent intent = new Intent (c, Risultati.class);
                startActivity(intent);
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

}
