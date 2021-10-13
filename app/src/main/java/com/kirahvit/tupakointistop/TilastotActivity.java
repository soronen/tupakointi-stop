package com.kirahvit.tupakointistop;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * Sovelluksen Tilasto -näkymä sisältää käyttäjädataa havainnollistavia toimintoja (elinikä, säästöt, rahan käyttökohteet)
 * @author Rasmus, Petrus, Eetu
 */

public class TilastotActivity extends AppCompatActivity {

    private float hinta;
    private float maara;
    private int paivatTupakoimatta;
    private float lopullinenhinta;
    private float elinika;
    private StorageManager storageManager;
    private BottomNavigationView navigationView;

    private ArrayList<Ostos> ostokset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilastot);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageManager = StorageManager.getStorageManager(this);

        navigationView = findViewById(R.id.NavigationViewBottom);
        setNavigationListeners();
        navigationView.setSelectedItemId(R.id.insights);

        loadValues();

        // Ostosten lisäys
        ostokset.add(new Ostos("Finnkino leffalippu", 8, lopullinenhinta));
        ostokset.add(new Ostos("Linnanmäki ranneke", 40, lopullinenhinta));
        ostokset.add(new Ostos("NHL 22", 60, lopullinenhinta));
        ostokset.add(new Ostos("Samsung Galaxy AO3s älypuhelin", 140, lopullinenhinta));
        ostokset.add(new Ostos("PlayStation 5", 500, lopullinenhinta));

        updateUI();
    }

    // Layoutin päivitys
    @SuppressLint("SetTextI18n")
    private void updateUI(){
        TextView saastetutRahat = (TextView) findViewById(R.id.textViewSaastetutRahat);
        saastetutRahat.setText("Olet säästänyt jo "+ lopullinenhinta +" euroa!");

        TextView saastettuelinika = (TextView) findViewById(R.id.textViewElinIka);
        saastettuelinika.setText("Olet pidentänyt elinikääsi "+ elinika +" tunnilla!");

        ListView listView = findViewById(R.id.ListViewOstokset);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ostokset));
    }

    // Arvojen lataus
    private void loadValues(){
        hinta = storageManager.loadValue("hinta");
        maara = storageManager.loadValue("maara");
        paivatTupakoimatta = storageManager.loadValueInt("paivatTupakoimatta");

        lopullinenhinta = (maara * hinta * paivatTupakoimatta);
        elinika = (maara * paivatTupakoimatta * 5);
    }

    // Navigaatiotoimintojen Listenerit
    @SuppressLint("NonConstantResourceId")
    private void setNavigationListeners(){
        // Back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // NavigationView
        navigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    break;
                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    break;
            }

            return true;
        });
    }
}
