package com.example.utente.apparacnoid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Regole3 extends AppCompatActivity {

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regole3);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.tomenu)
    public void clickRegole(View view) {
        Intent intent = new Intent(this, MenuPrincipale.class);
        startActivity(intent);
        finish();
    }


}
