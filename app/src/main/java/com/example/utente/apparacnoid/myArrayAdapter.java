package com.example.utente.apparacnoid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class myArrayAdapter extends ArrayAdapter<Giocatore> {

    public myArrayAdapter( Context context,  int textViewResourceId, List<Giocatore> objects) {
        super(context, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, null);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(), "Comfortaa-Regular.ttf");

        TextView nome = (TextView)rowView.findViewById(R.id.nome_list_tv);
        //TextView punteggio = (TextView)rowView.findViewById(R.id.punteggio_list_tv);
        Giocatore g = getItem(position);
        nome.setText(g.getPunteggio()+ "    " + g.getNome());
        nome.setTypeface(typeface);
        //punteggio.setText(g.getPunteggio());
        return rowView;
    }
}
