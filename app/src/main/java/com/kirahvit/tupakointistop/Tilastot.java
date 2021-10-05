package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Tilastot extends AppCompatActivity {

    private float hinta;
    private float maara;
    private int paivatTupakoimatta;
    private float lopullinenhinta;
    private StorageManager storageManager;

    private ArrayList<Ostos> ostokset = new ArrayList<Ostos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilastot);

        storageManager = StorageManager.getStorageManager(this);

        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);

        loadValues();

        //Tähän ostokset
        ostokset.add(new Ostos("Finnkino leffalippu", 8, lopullinenhinta));

        updateUI();
    }

    private void updateUI(){

        TextView editHinta = (TextView) findViewById(R.id.textViewSaastetutRahat);

        editHinta.setText("Olet säästänyt jo "+ String.valueOf(lopullinenhinta)+" euroa!");

        ListView listView = findViewById(R.id.ListViewOstokset);

        listView.setAdapter(new ArrayAdapter<Ostos>(this, android.R.layout.simple_list_item_1,ostokset));

    }

    private void loadValues(){
        hinta = storageManager.loadValue("hinta");
        maara = storageManager.loadValue("maara");
        paivatTupakoimatta = storageManager.loadValueInt("paivatTupakoimatta");

        lopullinenhinta = (maara * hinta * paivatTupakoimatta);
    }
}
