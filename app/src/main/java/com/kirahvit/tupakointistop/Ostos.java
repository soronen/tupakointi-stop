package com.kirahvit.tupakointistop;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Luokka, joka sisältää yhden ostoksen (listanäkymää varten)
 * @author Rasmus Nygård
 */

public class Ostos {
    /**
     * ostoksen kappalehinta
     */
    public float hinta;
    /**
     * ostoksen nimi
     */
    public String nimi;
    /**
     * käyttäjän ostettavissa oleva kappalemäärä
     */
    public int kappaleMaara;

    /**
     * Luo ostoksen annetuilla arvoilla
     * @param nimi ostoksen nimi
     * @param hinta ostoksen kappalehinta
     * @param rahaMaara käyttäjän säästetty rahamäärä
     */
    public Ostos(String nimi, float hinta, float rahaMaara){
        this.hinta = hinta;
        this.nimi = nimi;
        kappaleMaara = (int) Math.floor(rahaMaara / hinta);
        Log.d("logger", String.valueOf(kappaleMaara));
    }

    /**
     * Palauttaa ostoksen String -muodossa.
     * @return String, joka sisältää ostoksen nimen ja käyttäjän ostettavissa olevan kappalemäärän
     */
    @NonNull
    @Override
    public String toString() {
        if(kappaleMaara == 0){
            return "";
        }

        return kappaleMaara + "x " + nimi;
    }
}
