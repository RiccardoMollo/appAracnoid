package com.example.utente.apparacnoid;

import android.support.annotation.NonNull;

/**
 * Created by Utente on 30/05/2017.
 */

public class Giocatore implements Comparable <Giocatore> {
    String nome;
    int punteggio;

    public Giocatore(String n, int p){
        this.nome=n;
        this.punteggio=p;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int compareTo(Giocatore giocatoreComp) {
        int comparePunteggio = ((Giocatore) giocatoreComp).getPunteggio();

        //ascending order
        return  comparePunteggio - this.punteggio;
    }
}
