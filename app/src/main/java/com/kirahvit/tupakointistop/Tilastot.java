package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Tilastot extends AppCompatActivity {

    private float hinta;
    private float maara;
    private int paivatTupakoimatta;
    private float lopullinenhinta;
    private StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilastot);

        storageManager = StorageManager.getStorageManager(this);

        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);

        loadValues();
        lopullinenhinta = (maara * hinta * paivatTupakoimatta);
        updateUI();
    }
    private void updateUI(){

        TextView editHinta = (TextView) findViewById(R.id.textViewSaastetutRahat);

        editHinta.setText("Olet säästänyt jo "+ String.valueOf(lopullinenhinta)+" euroa!");

    }
        private void loadValues(){
            hinta = storageManager.loadValue("hinta");
            maara = storageManager.loadValue("maara");
            paivatTupakoimatta = storageManager.loadValueInt("paivatTupakoimatta");

        }



}
