package com.kirahvit.tupakointistop;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

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
 * Sovelluksen Asetukset-näkymä, sisältää siinä käytettyjä luokkia
 *
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

        storageManager = StorageManager.getStorageManager(this);

        navigationView = findViewById(R.id.NavigationViewBottom);
        setNavigationListeners();
        navigationView.setSelectedItemId(R.id.settings);


        // kun tupakoille syötetään hinta tai määrä, nämä tallentavat ne aktiviteetin muuttujiin.
        EditText editHinta = (EditText) findViewById(R.id.editTextHinta);
        EditText editMaara = (EditText) findViewById(R.id.editTextMaara);

        editHinta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hinta = Float.parseFloat(editHinta.getText().toString());
                    hideKeyboard(view);
                    Log.d(TAG, "onFocusChange: hinta");
                }
            }
        });

        editMaara.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    maara = Float.parseFloat(editMaara.getText().toString());
                    hideKeyboard(view);
                    Log.d(TAG, "onFocusChange: maara");
                }
            }
        });

        editHinta.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hinta = Float.parseFloat(editHinta.getText().toString());
                    hideKeyboard(view);
                    Log.d(TAG, "onEditorAction: hinta");
                    return true;
                }
                return false;
            }
        });

        editMaara.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    maara = Float.parseFloat(editMaara.getText().toString());
                    hideKeyboard(view);
                    Log.d(TAG, "onEditorAction: maara");
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Button nollaa painallus kutsuu private metodin CreateAlertDialogue
     * @param view
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
        builder.setPositiveButton(R.string.kylla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SettingsActivity.this, R.string.paivatNollattu, Toast.LENGTH_SHORT).show();
                nollaa();
                Log.d(TAG, "päivät nollattu");
            }
        });
        builder.setNegativeButton(R.string.peruuta, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SettingsActivity.this, R.string.toimintoPeruutettu, Toast.LENGTH_SHORT).show();
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

    private void nollaa(){
        storageManager.removeValue("paivatTupakoimatta");
        storageManager.removeValue("seuraavaTavoite");

    }

    private void saveValues(){
        storageManager.saveValue("hinta", hinta);
        storageManager.saveValue("maara", maara);
    }

    private void loadValues(){
        hinta = storageManager.loadValue("hinta");
        maara = storageManager.loadValue("maara");

        updateUI();
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

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