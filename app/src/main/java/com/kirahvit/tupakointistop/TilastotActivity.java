package com.kirahvit.tupakointistop;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class TilastotActivity extends AppCompatActivity {

    private float hinta;
    private float maara;
    private int paivatTupakoimatta;
    private float lopullinenhinta;
    private float elinika;
    private StorageManager storageManager;
    private BottomNavigationView navigationView;

    private ArrayList<Ostos> ostokset = new ArrayList<Ostos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilastot);

        storageManager = StorageManager.getStorageManager(this);

        navigationView = findViewById(R.id.NavigationViewBottom);
        setNavigationListeners();
        navigationView.setSelectedItemId(R.id.insights);

        loadValues();

        //Tähän ostokset
        ostokset.add(new Ostos("Finnkino leffalippu", 8, lopullinenhinta));
        ostokset.add(new Ostos("Linnanmäki ranneke", 40, lopullinenhinta));
        ostokset.add(new Ostos("NHL 22", 60, lopullinenhinta));
        ostokset.add(new Ostos("Samsung Galaxy AO3s älypuhelin", 140, lopullinenhinta));
        ostokset.add(new Ostos("PlayStation 5", 500, lopullinenhinta));

        updateUI();
    }

    private void updateUI(){

        TextView saastetutRahat = (TextView) findViewById(R.id.textViewSaastetutRahat);

        saastetutRahat.setText("Olet säästänyt jo "+ String.valueOf(lopullinenhinta)+" euroa!");

        TextView saastettuelinika = (TextView) findViewById(R.id.textViewElinIka);

        saastettuelinika.setText("Olet pidentänyt elinikääsi "+ String.valueOf(elinika)+" tunnilla!");

        ListView listView = findViewById(R.id.ListViewOstokset);

        listView.setAdapter(new ArrayAdapter<Ostos>(this, android.R.layout.simple_list_item_1,ostokset));

    }

    private void loadValues(){
        hinta = storageManager.loadValue("hinta");
        maara = storageManager.loadValue("maara");
        paivatTupakoimatta = storageManager.loadValueInt("paivatTupakoimatta");

        lopullinenhinta = (maara * hinta * paivatTupakoimatta);
        elinika = (maara * paivatTupakoimatta * 5);
    }

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
