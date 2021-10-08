package com.kirahvit.tupakointistop;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Luokka sisältää SharedPreferences tallentamista ja lukemista helpottavia metodeja
 * @author Rasmus Nygård
 */

public class StorageManager {

    public static StorageManager storageManager_Instance;

    private Context context;
    private SharedPreferences sharedPreferences;

    private StorageManager(Context context){
        sharedPreferences = context.getSharedPreferences("com.kirahvit.app",Context.MODE_PRIVATE);
    }

    /**
     * Singleton
     */
    public static StorageManager getStorageManager(Context context) {

        if (storageManager_Instance == null) {
            storageManager_Instance = new StorageManager(context);
        }
        return storageManager_Instance;
    }

    /**
     * Palauttaa tallennetun arvon, joka vastaa annettua tunnistetta
     * @param name tunnistenimi, jolla haetaan tallennettua arvoa
     * @return int loadedValue löydetty arvo, oletusarvo 0
     */
    public float loadValue(String name){

        float loadedValue = sharedPreferences.getFloat(name, 0);
        Log.d("logger", name + " value: " + loadedValue + " loaded.");

        return loadedValue;
    }

    public int loadValueInt(String name){

        int loadedValue = Math.round(sharedPreferences.getFloat(name, 0));
        Log.d("logger", name + " value: " + loadedValue + " loaded.");

        return loadedValue;
    }

    /**
     * Tallentaa arvon annetulle tunnisteelle
     * @param name tunnistenimi
     * @param value arvo, joka tallennetaan tunnisteelle
     */
    public void saveValue(String name, float value){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(name, value);
        editor.commit();
        Log.d("logger", name + " value: " + value + " saved.");
    }

    /**
     * Poistaa tallennetun tunniste/arvoparin
     * @param name tunnistenimi
     */
    public void removeValue(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(name);
        editor.commit();
        Log.d("logger", name + " removed.");
    }
}
