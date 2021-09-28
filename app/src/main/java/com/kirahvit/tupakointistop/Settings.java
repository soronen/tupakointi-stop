package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    private static final String TAG = "Settings";

    private int maara;
    private int hinta;
    private int paivat;


    // sharedpref tuonti, väliaikainen..
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // shared preferenssien tuonti, väliaikainen.. + niiden liittäminen muuttujiin joita käytetään tässä aktiviteetissa.
        sharedPref = getSharedPreferences("hinta" , Activity.MODE_PRIVATE);
        hinta = sharedPref.getInt("hinta", 0);

        sharedPref = getSharedPreferences("maara" ,Activity.MODE_PRIVATE);
        maara = sharedPref.getInt("maara", 0);

        sharedPref = getSharedPreferences("paivat" ,Activity.MODE_PRIVATE);
        paivat = sharedPref.getInt("paivat", 0);


        // tupakoiden määrän ja hinnan textView
        TextView tupakoidenMaara = (TextView) findViewById(R.id.editTextMaara);
        tupakoidenMaara.setText(String.valueOf(maara));

        TextView tupakoidenHinta = (TextView) findViewById(R.id.editTextHinta);
        tupakoidenHinta.setText(String.valueOf(hinta));


        // kun tupakoille syötetään hinta tai määrä ja painetaan entteriä, nämä tallentavat ne aktiviteetin muuttujiin.
        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        editHinta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean boo = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Perform your Actions here.
                    hinta = Integer.parseInt(tupakoidenHinta.getText().toString());
                    Log.d(TAG, "Enter (hinta) pressed");
                }
                return boo;
            }
        });

        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);
        editMaara.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean boo = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Perform your Actions here.
                    maara = Integer.parseInt(tupakoidenMaara.getText().toString());
                    Log.d(TAG, "Enter (maara) pressed");
                }
                return boo;
            }
        });


        Log.d(TAG, "onCreate() called");

    }

    public void resetPressed (View v) {
        paivat = 0;
    }

    // tämän olisi tarkoitus tallentaa arvot SharedPreferensseihin.
    @Override
    public void onPause() {
        super.onPause();

        sharedPref = getSharedPreferences("maara" , Activity.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("maara", maara);
        editor.commit();

        sharedPref = getSharedPreferences("hinta",Activity.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("hinta", hinta);
        editor.commit();

        sharedPref = getSharedPreferences("paivat",Activity.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("paivat", paivat);
        editor.commit();

        Log.d(TAG, "onPause() called");
    }
}