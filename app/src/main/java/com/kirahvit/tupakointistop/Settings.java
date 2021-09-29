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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private static final String TAG = "Settingslog";

    private int maara;
    private int hinta;
    private int paivatTupakoimatta;
    private Button buttonNollaa;


    // sharedpref tuonti, väliaikainen..
//    private SharedPreferences sharedPref;
//    private SharedPreferences.Editor editor;

    private StorageManager storageManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate() called");

        storageManager = StorageManager.getStorageManager(this);


        // kun tupakoille syötetään hinta tai määrä, nämä tallentavat ne aktiviteetin muuttujiin.
        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        editHinta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hinta = Integer.parseInt(editHinta.getText().toString());
                    hideKeyboard(view);
                    Log.d(TAG, "onFocusChange: hinta");
                }
            }
        });

        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);
        editMaara.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    maara = Integer.parseInt(editMaara.getText().toString());
                    hideKeyboard(view);
                    Log.d(TAG, "onFocusChange: maara");
                }
            }
        });
    }

    public void onButtonClick(View view){

        if(view.getId() == R.id.buttonNollaa){
            CreateAlertDialogue();
        }
    }

    // Lisävahvistus, ettei päiviä nollattaisi vahinnossa (kyllä / peruuta viesti nollaa painikkeen painamisen jälkeen).
    private void CreateAlertDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Oletko varma että haluat nollata päivät?");
        builder.setPositiveButton("Kyllä", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Settings.this, "Päivät nollattu", Toast.LENGTH_SHORT).show();
                paivatTupakoimatta = 0;
                Log.d(TAG, "päivät nollattu");
            }
        });
        builder.setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Settings.this, "Toiminto peruutettu", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "nollaus peruutettu");
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadValues();
    }

    // tämän olisi tarkoitus tallentaa arvot SharedPreferensseihin.
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");

        saveValues();
    }

    // Päivittää UI:n
    private void updateUI(){

        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);

        editMaara.setText(String.valueOf(maara));
        editHinta.setText(String.valueOf(hinta));
    }

    private void saveValues(){
        storageManager.saveValue("hinta", hinta);
        storageManager.saveValue("maara", maara);
        storageManager.saveValue("paivatTupakoimatta", paivatTupakoimatta);
    }

    private void loadValues(){
        hinta = storageManager.loadValue("hinta");
        maara = storageManager.loadValue("maara");
        paivatTupakoimatta = storageManager.loadValue("paivatTupakoimatta");

        updateUI();
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}