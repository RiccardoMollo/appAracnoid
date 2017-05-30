package com.example.utente.apparacnoid;

import android.support.annotation.NonNull;

public class Giocatore implements Comparable <Giocatore> {
    private String nome;
    private int punteggio;

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
    public int compareTo(@NonNull Giocatore giocatoreComp) {
        int comparePunteggio = ((Giocatore) giocatoreComp).getPunteggio();

        //ascending order
        return  comparePunteggio - this.punteggio;
    }
}
