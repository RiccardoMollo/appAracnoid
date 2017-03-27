package com.example.utente.apparacnoid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class Livello extends AppCompatActivity {
    int i=0;
    int puntiLettera=0;
    int puntiParole=0;
    int puntiTempo;

    private static TextView txtCountDown;
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
        CountDownTimer countDownTimer = new MyCountDownTimer(startTime, interval, this);
        if(txtCountDown!=null){
            txtCountDown.setText(String.valueOf(""+ startTime / 1000));
        }
        countDownTimer.start();

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EditTextListener", "beforeTextChanged "+charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("EditTextListener", "onTextChanged "+charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("EditTextListener", "afterTextChanged "+editable.toString());
            }
        });
    }


    public void pressSend(View view){
        EditText et = (EditText) findViewById(R.id.parolaInserita);
        TextView tv = (TextView) findViewById(R.id.letter);
        String confronto = tv.getText().toString();
        String parolaInserita= et.getText().toString();
        puntiLettera+= (parolaInserita.length()*10);
        et.setHint("inserisci la parola");
        //String punti = String.valueOf(puntiLettera);
        // et.setHint(punti);
        et.setText(null);

        TextView counter = (TextView) findViewById(R.id.counter);
        if (parolaInserita.startsWith(confronto)){
            i++;
            counter.setText(Integer.toString(i));
            puntiParole=20*i;
            //String punti = String.valueOf(puntiParole);
            //et.setHint(punti);
        }
        else{
            et.setHint("parola non valida!");
            counter.setText(Integer.toString(i));
        }


        if (i==5){
            int puntiLivello =puntiLettera+puntiParole;
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
                int puntiLivello = puntiLettera+puntiParole;
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
