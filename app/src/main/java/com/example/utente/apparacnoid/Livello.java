package com.example.utente.apparacnoid;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class Livello extends AppCompatActivity {
    int contatore=0;
    int puntiLettera=0;
    int puntiParole=0;
    //int puntiTempo;


    private static TextView txtCountDown;
    private static final long startTime = 20 * 1000;
    private static final long interval = 1000;
    CountDownTimer countDownTimer;
    WordsDataSource dizionario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livello);

        EditText et = (EditText) findViewById(R.id.parolaInserita);
        dizionario = new WordsDataSource(this);
        dizionario.open();

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
            if(dizionario.isCorrectedWord(parolaInserita)){
                puntiLettera+= (parolaInserita.length()*10);
                contatore++;
                counter.setText(Integer.toString(contatore));
                puntiParole=20*contatore;
                //String punti = String.valueOf(puntiParole);
                //et.setHint(punti);
            }
           else{
                et.setHint("parola non valida!");
                counter.setText(Integer.toString(contatore));
            }
        }
        else{
            et.setHint("parola non valida!");
            counter.setText(Integer.toString(contatore));
        }

// INSERIMENTO DELLA QUINTA PAROLA //

        if (contatore==5){

            countDownTimer.cancel();

            SharedPreferences settings = getSharedPreferences("Settings", 0);
            SharedPreferences.Editor editor = settings.edit();
            int livello =  settings.getInt("livello",0);

            int puntiLivello =puntiLettera+puntiParole;
            Intent intent = new Intent (this, Risultati.class);

           // String messageLivello=String.valueOf(livello);
            String messageParole = String.valueOf(contatore);
            String messagePunti  = String.valueOf(puntiLivello);

            intent.putExtra("messagePunti", messagePunti);
            intent.putExtra("messageParole", messageParole);
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

                String messageParole = String.valueOf(contatore);
                String messagePunti  = String.valueOf(puntiLivello);

                intent.putExtra("messagePunti", messagePunti);
                intent.putExtra("messageParole", messageParole);

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






}
