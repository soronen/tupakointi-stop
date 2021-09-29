package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private static final String TAG = "Settings";

    private int maara;
    private int hinta;
    private int paivat;
    private Button buttonNollaa;


    // sharedpref tuonti, väliaikainen..
//    private SharedPreferences sharedPref;
//    private SharedPreferences.Editor editor;

    StorageManager prefs = StorageManager.getStorageManager(getApplicationContext());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // shared preferenssien tuonti, väliaikainen.. + niiden liittäminen muuttujiin joita käytetään tässä aktiviteetissa.
//        sharedPref = getSharedPreferences("hinta" , Activity.MODE_PRIVATE);
//        hinta = sharedPref.getInt("hinta", 0);
//
//        sharedPref = getSharedPreferences("maara" ,Activity.MODE_PRIVATE);
//        maara = sharedPref.getInt("maara", 0);
//
//        sharedPref = getSharedPreferences("paivat" ,Activity.MODE_PRIVATE);
//        paivat = sharedPref.getInt("paivat", 0);


        // StorageManager muuttujien lataus
        hinta = prefs.loadValue("hinta");
        maara = prefs.loadValue("maara");
        paivat = prefs.loadValue("paivat");

        // tupakoiden määrän ja hinnan textView
        TextView tupakoidenMaara = (TextView) findViewById(R.id.editTextMaara);
        tupakoidenMaara.setText(String.valueOf(maara));

        TextView tupakoidenHinta = (TextView) findViewById(R.id.editTextHinta);
        tupakoidenHinta.setText(String.valueOf(hinta));


        // kun tupakoille syötetään hinta tai määrä ja painetaan entteriä, nämä tallentavat ne aktiviteetin muuttujiin.
        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        editHinta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent key) {
                boolean event = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hinta = Integer.parseInt(tupakoidenHinta.getText().toString());
                    Log.d(TAG, "Enter (hinta) pressed");
                }
                return event;
            }
        });

        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);
        editMaara.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent key) {
                boolean event = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    maara = Integer.parseInt(tupakoidenMaara.getText().toString());
                    Log.d(TAG, "Enter (maara) pressed");
                }
                return event;
            }
        });

        // aloita alusta nappi
        Button buttonNollaa = (Button) findViewById(R.id.buttonNollaa);  // has to be in inside onCreate()
        buttonNollaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialogue();
            }
        });

        Log.d(TAG, "onCreate() called");

    }

    // Lisävahvistus, ettei päiviä nollattaisi vahinnossa (kyllä / peruuta viesti nollaa painikkeen painamisen jälkeen).
    private void CreateAlertDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Oletko varma että haluat nollata päivät?");
        builder.setPositiveButton("Kyllä", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Settings.this, "Päivät nollattu", Toast.LENGTH_SHORT).show();
                paivat = 0;
                Log.d("TAG", "päivät nollattu");
            }
        });
        builder.setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Settings.this, "Toiminto peruutettu", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "nollaus peruutettu");
            }
        });
        builder.create();
        builder.show();
    }


    // tämän olisi tarkoitus tallentaa arvot SharedPreferensseihin.
    @Override
    public void onPause() {
        super.onPause();

//        sharedPref = getSharedPreferences("maara" , Activity.MODE_PRIVATE);
//        editor = sharedPref.edit();
//        editor.putInt("maara", maara);
//        editor.commit();
//
//        sharedPref = getSharedPreferences("hinta",Activity.MODE_PRIVATE);
//        editor = sharedPref.edit();
//        editor.putInt("hinta", hinta);
//        editor.commit();
//
//        sharedPref = getSharedPreferences("paivat",Activity.MODE_PRIVATE);
//        editor = sharedPref.edit();
//        editor.putInt("paivat", paivat);
//        editor.commit();

        // StorageManager tallenus
        prefs.saveValue("hinta", hinta);
        prefs.saveValue("maara", maara);
        prefs.saveValue("paivat", maara);


        Log.d(TAG, "onPause() called");
    }
}