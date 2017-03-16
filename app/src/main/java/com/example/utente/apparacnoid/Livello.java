package com.example.utente.apparacnoid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class Livello extends AppCompatActivity {
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livello);

        EditText et = (EditText) findViewById(R.id.parolaInserita);

        et.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        TextView tv = (TextView) findViewById(R.id.letter);

        String[] chars = {"a","b","c","d","e","f","g","h","x","y"};
        tv.setText(chars[(int) (Math.random() * 10)]);

    }

    public void pressSend(View view){
        EditText et = (EditText) findViewById(R.id.parolaInserita);
        et.setText(null);
        et.setHint("inserisci la parola");
        TextView counter = (TextView) findViewById(R.id.counter);
        counter.setText(Integer.toString(i));
        i++;
        if (i==6){
            Intent intent = new Intent (this, Risultati.class);
            startActivity(intent);
        }
    }




}
