package com.example.utente.apparacnoid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Connessione extends Activity {

    Unbinder unbinder;

    private String host_url = "192.168.1.32";
    private int host_port = 8080;

    @BindView(R.id.set_display_pixels)
    Button set_display_pixels;

    @BindView(R.id.random_colors)
    Button randomColors;

    @BindView(R.id.move_backward_button)
    Button moveBackwardButton;

    @BindView(R.id.move_forward_button)
    Button moveForwardButton;

    @BindView(R.id.highlight_components_button)
    Button changeColorButton;

    @BindViews({R.id.first_byte_ip, R.id.second_byte_ip, R.id.third_byte_ip, R.id.fourth_byte_ip})
    List<EditText> ip_address_bytes;

    @BindView(R.id.host_port)
    EditText hostPort;

    private TextWatcher myIpTextWatcher;
    private JSONArray pixels_array;

    private Handler mNetworkHandler, mMainHandler;

    private NetworkThread mNetworkThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connessione);
        unbinder = ButterKnife.bind(this);

        set_display_pixels.setEnabled(false);
        randomColors.setEnabled(false);
        moveBackwardButton.setEnabled(false);
        moveForwardButton.setEnabled(false);
        changeColorButton.setEnabled(false);

        myIpTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (checkCorrectIp()) {
                    moveBackwardButton.setEnabled(true);
                    moveForwardButton.setEnabled(true);
                    randomColors.setEnabled(true);
                    set_display_pixels.setEnabled(true);
                    changeColorButton.setEnabled(true);
                    Message msg = mNetworkHandler.obtainMessage();
                    msg.what = NetworkThread.SET_SERVER_DATA;
                    msg.obj = host_url;
                    msg.arg1 = host_port;
                    msg.sendToTarget();

                    handleNetworkRequest(NetworkThread.SET_SERVER_DATA, host_url, host_port ,0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        for (EditText ip_byte : ip_address_bytes) {
            ip_byte.addTextChangedListener(myIpTextWatcher);
        }

        hostPort.addTextChangedListener(myIpTextWatcher);

        pixels_array = preparePixelsArray();

        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(Connessione.this, (String) msg.obj, Toast.LENGTH_LONG).show();
            }
        };

        startHandlerThread();
    }

    public void startHandlerThread() {
        mNetworkThread = new NetworkThread(mMainHandler);
        mNetworkThread.start();
        mNetworkHandler = mNetworkThread.getNetworkHandler();
    }

    private boolean checkCorrectIp() {
        StringBuilder sb = new StringBuilder();
        int port;

        if (hostPort.getText().length() == 0)
            return false;

        for (EditText editText : ip_address_bytes) {
            sb.append(editText.getText().toString());
            sb.append(".");
        }
        //cancello l'ultimo "."
        sb.deleteCharAt(sb.length() - 1);

        port = Integer.parseInt(hostPort.getText().toString());
        if (validIP(sb.toString()) && port >= 0 & port <= 65535) {
            host_url = sb.toString();
            host_port = port;
            return true;
        } else
            return false;
    }

    //from http://stackoverflow.com/questions/4581877/validating-ipv4-string-in-java
    public static boolean validIP(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            if (ip.endsWith(".")) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }



    @OnClick(R.id.random_colors)
    void setRandomColors() {

        try {
            JSONArray pixels_array = preparePixelsArray();

            for (int i = 0; i < pixels_array.length(); i++) {
                ((JSONObject) pixels_array.get(i)).put("r", (int) (Math.random() * 255.0f));
                ((JSONObject) pixels_array.get(i)).put("g", (int) (Math.random() * 255.0f));
                ((JSONObject) pixels_array.get(i)).put("b", (int) (Math.random() * 255.0f));
            }
            handleNetworkRequest(NetworkThread.SET_PIXELS, pixels_array, 0 ,0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.set_display_pixels)
    void setDisplayPixels() {
        try {
            JSONArray pixels_array = preparePixelsArray();

            for (int i = 0; i < pixels_array.length(); i++) {
                ((JSONObject) pixels_array.get(i)).put("r", (int) (Math.random() * 255.0f));
                ((JSONObject) pixels_array.get(i)).put("g", (int) (Math.random() * 255.0f));
                ((JSONObject) pixels_array.get(i)).put("b", (int) (Math.random() * 255.0f));
            }
            handleNetworkRequest(NetworkThread.SET_DISPLAY_PIXELS, pixels_array, 0 ,0);
        } catch (JSONException e) {
            // There should be no Exception
        }
    }

    @OnClick(R.id.highlight_components_button)
    void highLightComponents() {
        try {
            pixels_array = preparePixelsArray();
            handleNetworkRequest(NetworkThread.SET_PIXELS, pixels_array, 0 ,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.move_forward_button)
    void movePixelsForward() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < pixels_array.length(); i++) {
                jsonArray.put(pixels_array.get((i + pixels_array.length() - 10) % pixels_array.length()));
            }
            pixels_array = jsonArray;
            handleNetworkRequest(NetworkThread.SET_PIXELS, pixels_array, 0 ,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.move_backward_button)
    void movePixelsBackward() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < pixels_array.length(); i++) {
                jsonArray.put(pixels_array.get((i + 10) % pixels_array.length()));
            }
            pixels_array = jsonArray;
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

    @OnClick(R.id.next)
    public void clickNext(View view) {
        Intent intent = new Intent(this, NuovaPartita.class);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("host_url",host_url );
        editor.apply();
        editor.putInt("host_port",host_port);
        editor.apply();
        //intent.putExtra(NuovaPartita.HOST_URL_KEY, host_url);
        //intent.putExtra(NuovaPartita.HOST_PORT_KEY, host_port);
        startActivity(intent);
    }
}
