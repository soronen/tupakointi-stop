package com.kirahvit.tupakointistop;

import android.util.Log;

public class Ostos {
    public float hinta;
    public String nimi;
    public int kappaleMaara;

    public Ostos(String nimi, float hinta, float rahaMaara){
        this.hinta = hinta;
        this.nimi = nimi;
        kappaleMaara = (int) Math.floor(rahaMaara / hinta);
        Log.d("logger", String.valueOf(kappaleMaara));
    }

    @Override
    public String toString() {
        if(kappaleMaara == 0){
            return "";
        }

        return kappaleMaara + "x " + nimi;
    }
}
