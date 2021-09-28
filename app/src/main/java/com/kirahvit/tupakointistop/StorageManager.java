package com.kirahvit.tupakointistop;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Luokka sisältää SharedPreferences tallentamista ja lukemista helpottavia metodeja
 * @author Rasmus Nygård
 * test
 */

public class StorageManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public StorageManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("com.kirahvit.app",Context.MODE_PRIVATE);
    }

    /**
     * Palauttaa tallennetun arvon, joka vastaa annettua tunnistetta
     * @param name tunnistenimi, jolla haetaan tallennettua arvoa
     * @return int loadedValue löydetty arvo, oletusarvo 0
     */
    public int loadValue(String name){

        int loadedValue = sharedPreferences.getInt(name, 0);
        Log.d("logger", name + " value: " + loadedValue + " loaded.");

        return loadedValue;
    }

    /**
     * Tallentaa arvon annetulle tunnisteelle
     * @param name tunnistenimi
     * @param value arvo, joka tallennetaan tunnisteelle
     */
    public void saveValue(String name, int value){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(name, value);
        editor.commit();
        Log.d("logger", name + " value: " + value + " saved.");
    }
}
