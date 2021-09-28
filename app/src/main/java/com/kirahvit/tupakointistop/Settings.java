package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    private static final String TAG = "Settings";

    public int mMaara;
    public int hHinta;
    public int pPaivat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // tupakoiden määrän ja hinnan textView

        TextView tupakoidenMaara = (TextView) findViewById(R.id.tvTupakoidenMaara);
        tupakoidenMaara.setText(String.valueOf(mMaara));

        TextView tupakoidenHinta = (TextView) findViewById(R.id.tvTupakoidenHinta);
        tupakoidenHinta.setText(String.valueOf(hHinta));


        Log.d(TAG, "onCreate() called");


    }
    public void resetPressed (View v) {
        pPaivat = 0;

    }
}