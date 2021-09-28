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

    private int mMaara;
    private int hHinta;
    private int pPaivat;



    // sharedpref tuonti, väliaikainen..

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // shared preferenssien tuonti, väliaikainen.. + niiden liittäminen muuttujiin joita käytetään tässä aktiviteetissa.
        sharedPref = getSharedPreferences("maara" , Activity.MODE_PRIVATE);
        hHinta = sharedPref.getInt("maara", 0);

        sharedPref = getSharedPreferences("hinta" ,Activity.MODE_PRIVATE);
        mMaara = sharedPref.getInt("hinta", 0);

        sharedPref = getSharedPreferences("paivat" ,Activity.MODE_PRIVATE);
        pPaivat = sharedPref.getInt("paivat", 0);


        // tupakoiden määrän ja hinnan textView
        TextView tupakoidenMaara = (TextView) findViewById(R.id.editTextMaara);
        tupakoidenMaara.setText(String.valueOf(mMaara));

        TextView tupakoidenHinta = (TextView) findViewById(R.id.editTextHinta);
        tupakoidenHinta.setText(String.valueOf(hHinta));


        Log.d(TAG, "onCreate() called");

    }


    public void resetPressed (View v) {
        pPaivat = 0;

    }
    // nämä eivät toimi

    public void updateMaara (View view) {
        EditText editTextMaara = (EditText) findViewById(R.id.editTextMaara);
        int mMaara = Integer.parseInt(editTextMaara.getText().toString());
    }
    public void updateHinta (View view) {
        EditText editTextHinta = (EditText) findViewById(R.id.editTextHinta);
        int hHinta = Integer.parseInt(editTextHinta.getText().toString());

    }

    final TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView editTextHinta, int i, KeyEvent KEYCODE_ENTER) {
            boolean handled = false;
            if (9001 == EditorInfo.IME_ACTION_DONE) {
                editTextHinta = (EditText) findViewById(R.id.editTextHinta);
                hHinta = Integer.parseInt(editTextHinta.getText().toString());
                handled = true;
            }
            return handled;
        }
    };


    // tämän olisi tarkoitus tallentaa arvot SharedPreferensseihin.
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences hits = getSharedPreferences("maara" , Activity.MODE_PRIVATE);
        editor = hits.edit();
        editor.putInt("maara", mMaara);
        editor.commit();

        sharedPref = getSharedPreferences("hinta",Activity.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("hinta", hHinta);
        editor.commit();

        sharedPref = getSharedPreferences("paivat",Activity.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("paivat", pPaivat);
        editor.commit();

        Log.d(TAG, "onPause() called");
    }
}