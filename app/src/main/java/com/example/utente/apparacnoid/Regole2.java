package com.example.utente.apparacnoid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Regole2 extends AppCompatActivity {

    Unbinder unbinder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regole2);
        unbinder1 = ButterKnife.bind(this);
    }
    @OnClick(R.id.toregole3)
    public void clickRegole(View view) {
        Intent intent = new Intent(this, Regole3.class);

        startActivity(intent);
    }
}
