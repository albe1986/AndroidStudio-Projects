package com.example.apolverari.myapplication;

import java.util.ArrayList;

/**
 * Created by a.polverari on 14/04/2016.
 */

public class Nota {
    private String titolo;
    private String contenuto;

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Nota(String titolo, String contenuto){
        this.titolo = titolo;
        this.contenuto =  contenuto;
    }

    public static ArrayList<Nota> getAllNotes(){
        ArrayList<Nota> lista = new ArrayList<Nota>();
        for (int i = 0; i<3; i++){
            Nota n = new Nota("Nota" + i, "Prova" + i);
            lista.add(n);
        }
        return lista;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
}
