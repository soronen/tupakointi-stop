package com.kirahvit.tupakointistop;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Sovelluksen Asetukset-näkymä, sisältää käyttäjän datan syöttöön ja käsittelyyn liittyviä toimintoja
 * @author Rasmus, Petrus, Eetu
 */

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "Settingslog";

    private float maara;
    private float hinta;

    private StorageManager storageManager;
    private BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate() called");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageManager = StorageManager.getStorageManager(this);

        navigationView = findViewById(R.id.NavigationViewBottom);
        setNavigationListeners();
        navigationView.setSelectedItemId(R.id.settings);

        // kun tupakoille syötetään hinta tai määrä, nämä tallentavat ne aktiviteetin muuttujiin.
        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);

        editHinta.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                hinta = Float.parseFloat(editHinta.getText().toString());
                hideKeyboard(view);
                Log.d(TAG, "onFocusChange: hinta");
            }
        });

        editMaara.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                maara = Float.parseFloat(editMaara.getText().toString());
                hideKeyboard(view);
                Log.d(TAG, "onFocusChange: maara");
            }
        });

        editHinta.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hinta = Float.parseFloat(editHinta.getText().toString());
                hideKeyboard(view);
                Log.d(TAG, "onEditorAction: hinta");
                return true;
            }
            return false;
        });

        editMaara.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                maara = Float.parseFloat(editMaara.getText().toString());
                hideKeyboard(view);
                Log.d(TAG, "onEditorAction: maara");
                return true;
            }
            return false;
        });
    }

    /**
     * Metodi vastaa "nollaa päivät" -napin painallukseen
     * @param view viittaus clickattuun viewiin
     */
    public void onButtonClick(View view){

        if(view.getId() == R.id.buttonNollaa){
            CreateAlertDialogue();
        }
    }

    // Lisävahvistus, ettei päiviä nollattaisi vahinnossa (kyllä / peruuta viesti nollaa painikkeen painamisen jälkeen).
    private void CreateAlertDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.asetuksetNollausOletkoVarma);
        builder.setPositiveButton(R.string.kylla, (dialogInterface, i) -> {
            Toast.makeText(SettingsActivity.this, R.string.paivatNollattu, Toast.LENGTH_SHORT).show();
            nollaa();
            Log.d(TAG, "päivät nollattu");
        });
        builder.setNegativeButton(R.string.peruuta, (dialogInterface, i) -> {
            Toast.makeText(SettingsActivity.this, R.string.toimintoPeruutettu, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "nollaus peruutettu");
        });
        builder.create();
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");

        saveValues();
    }

    // Layoutin päivitys
    private void updateUI(){

        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);

        editMaara.setText(String.valueOf(maara));
        editHinta.setText(String.valueOf(hinta));
    }

    // Arvojen nollaus
    private void nollaa(){
        storageManager.removeValue("paivatTupakoimatta");
        storageManager.removeValue("seuraavaTavoite");
    }

    // Arvojen tallennus
    private void saveValues(){
        storageManager.saveValue("hinta", hinta);
        storageManager.saveValue("maara", maara);
    }

    // Arvojen hakeminen
    private void loadValues(){
        hinta = storageManager.loadValue("hinta");
        maara = storageManager.loadValue("maara");

        updateUI();
    }

    // Piilottaa näppäimistön
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Navigaatiotoimintojen Listenerit
    @SuppressLint("NonConstantResourceId")
    private void setNavigationListeners(){
        // Back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // NavigationView
        navigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.insights:
                    startActivity(new Intent(getApplicationContext(), TilastotActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
            }

            return true;
        });
    }
}