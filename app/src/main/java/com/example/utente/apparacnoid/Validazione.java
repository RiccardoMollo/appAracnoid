package com.example.utente.apparacnoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Validazione extends AppCompatActivity {

    Random rand= new Random();

    int verifica=0;

    int coloreOut = rand.nextInt(3);
    int coloreMid = rand.nextInt(3);
    int coloreIn  = rand.nextInt(3);
    int rO;
    int gO;
    int bO;
    int rM;
    int gM;
    int bM;
    int rI;
    int gI;
    int bI;

    @BindView(R.id.out1)
    ImageButton out1;
    @BindView(R.id.out2)
    ImageButton out2;
    @BindView(R.id.out3)
    ImageButton out3;
    @BindView(R.id.mid1)
    ImageButton mid1;
    @BindView(R.id.mid2)
    ImageButton mid2;
    @BindView(R.id.mid3)
    ImageButton mid3;
    @BindView(R.id.in1)
    ImageButton in1;
    @BindView(R.id.in2)
    ImageButton in2;
    @BindView(R.id.in3)
    ImageButton in3;

    JSONArray pixels_array;

    Unbinder unbinder;

    private Handler mNetworkHandler, mMainHandler;
    private NetworkThread mNetworkThread = null;
    private String host_url ;
    private int host_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validazione);
        TextView titolo = (TextView) findViewById(R.id.titolo);
        TextView descrizione = (TextView) findViewById(R.id.descrizione);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "Comfortaa-Regular.ttf");
        titolo.setTypeface(typeface);
        descrizione.setTypeface(typeface);

        unbinder = ButterKnife.bind(this);

        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        host_port =  settings.getInt("host_port",0);
        host_url = settings.getString("host_url", "null");

        pixels_array = preparePixelsArray();

        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(Validazione.this, (String) msg.obj, Toast.LENGTH_LONG).show();
            }
        };

        if (coloreOut==0){
            rO=255;
            gO=0;
            bO=0;


        }

        if (coloreOut==1){
            rO=0;
            gO=0;
            bO=255;

        }

        if (coloreOut==2){
            rO=0;
            gO=255;
            bO=0;

        }
        if (coloreMid==0){
            rM=255;
            gM=0;
            bM=0;

        }

        if (coloreMid==1){
            rM=0;
            gM=0;
            bM=255;

        }

        if (coloreMid==2){
            rM=0;
            gM=255;
            bM=0;

        }
        if (coloreIn==0){
            rI=255;
            gI=0;
            bI=0;

        }
        if (coloreIn==1){
            rI=0;
            gI=0;
            bI=255;

        }
        if (coloreIn==2){
            rI=0;
            gI=255;
            bI=0;

        }



        startHandlerThread();
        setAnelli();

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



    void setAnelli() {


        //RAGNATELA
        try {

            for (int i = 522; i < 613; i++) {
                ((JSONObject) pixels_array.get(i)).put("r", rI);
                ((JSONObject) pixels_array.get(i)).put("g", gI);
                ((JSONObject) pixels_array.get(i)).put("b", bI);
            }
            handleNetworkRequest(NetworkThread.SET_PIXELS, pixels_array, 0 ,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            for (int i = 613; i < 791; i++) {
                ((JSONObject) pixels_array.get(i)).put("r", rM);
                ((JSONObject) pixels_array.get(i)).put("g", gM);
                ((JSONObject) pixels_array.get(i)).put("b", bM);
            }
            handleNetworkRequest(NetworkThread.SET_PIXELS, pixels_array, 0 ,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            for (int i = 791; i < 1072; i++) {
                ((JSONObject) pixels_array.get(i)).put("r", rO);
                ((JSONObject) pixels_array.get(i)).put("g", gO);
                ((JSONObject) pixels_array.get(i)).put("b", bO);
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
                    tmp.put("g", 0);
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


    public void GoNext() {
        if(verifica==3) {
            Intent intent = new Intent(this, MenuPrincipale.class);
            startActivity(intent);
            finish();
        }

    }
    @OnClick(R.id.out1)
    public void clickOut1(View view) {
        if (coloreOut == 0) {
            verifica++;
            out1.setEnabled(false);
            out1.setAlpha(45);
            }
        GoNext();


    }
    @OnClick(R.id.out2)
    public void clickOut2(View view) {
        if (coloreOut == 1) {
            verifica++;
            out2.setEnabled(false);
            out2.setAlpha(45);
        }
        GoNext();

    }
    @OnClick(R.id.out3)
    public void clickOut3(View view) {
        if (coloreOut==2){
            verifica++;
            out3.setEnabled(false);
            out3.setAlpha(45);
        }
        GoNext();


    }
    @OnClick(R.id.mid1)
    public void clickMid1(View view) {
        if (coloreMid == 0) {
            verifica++;
            mid1.setEnabled(false);
            mid1.setAlpha(45);
        }
        GoNext();
    }
    @OnClick(R.id.mid2)
    public void clickMid2(View view) {
        if (coloreMid == 1) {
            verifica++;
            mid2.setEnabled(false);
            mid2.setAlpha(45);
        }
        GoNext();
    }
    @OnClick(R.id.mid3)
    public void clickMid3(View view) {
        if (coloreMid==2){
            verifica++;
            mid3.setEnabled(false);
            mid3.setAlpha(45);
        }
        GoNext();

    }
    @OnClick(R.id.in1)
    public void clickIn1(View view) {
        if (coloreIn == 0) {
            verifica++;
            in1.setEnabled(false);
            in1.setAlpha(45);
        }
        GoNext();
    }
    @OnClick(R.id.in2)
    public void clickIn2(View view) {
        if (coloreIn == 1) {
            verifica++;
            in2.setEnabled(false);
            in2.setAlpha(45);
        }
        GoNext();
    }
    @OnClick(R.id.in3)
    public void clickIn3(View view) {
        if (coloreIn==2){
            verifica++;
            in3.setEnabled(false);
            in3.setAlpha(45);
        }
        GoNext();

    }

}



