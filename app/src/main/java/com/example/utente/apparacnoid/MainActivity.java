package com.example.utente.apparacnoid;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nome_et;
    Button inizia;
    Button scegli_colore;
    int[] lista_colori= new int[]{Color.argb(255,200,0,0),Color.argb(255,0,255,0),Color.argb(255,0,0,255)};
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome_et=(EditText) findViewById(R.id.nome_et);
        inizia=(Button) findViewById(R.id.inizia);
        scegli_colore=(Button) findViewById(R.id.scegli_colore);


        scegli_colore.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


                scegli_colore.getBackground().setColorFilter(lista_colori[i], PorterDuff.Mode.SRC_ATOP);
                i++;
                if(i==3){
                    i=0;
                }
            }
        });
    }


}
