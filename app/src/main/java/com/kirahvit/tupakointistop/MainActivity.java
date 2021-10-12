package com.kirahvit.tupakointistop;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int paivatTupakoimatta;
    private int seuraavaTavoite;

    private StorageManager storageManager;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageManager = StorageManager.getStorageManager(this);

        navigationView = findViewById(R.id.NavigationViewBottom);
        setNavigationListeners();

    }

    public void onButtonClick(View view){
        int viewId = view.getId();

        if(viewId == R.id.lisääpäiväbutton){
            paivatTupakoimatta++;
            updateTavoite();
            updateUI();
        }
    }

    public void updateUI(){

        TextView paivatTupakoimattaView = findViewById(R.id.päivättupakoimatta);
        TextView paivatTavoitteeseenView = findViewById(R.id.päivättavoite);

        paivatTupakoimattaView.setText(String.valueOf(paivatTupakoimatta));
        paivatTavoitteeseenView.setText(String.valueOf(seuraavaTavoite));

        navigationView = findViewById(R.id.NavigationViewBottom);
        navigationView.setSelectedItemId(R.id.home);

        UpdateViesti();
    }

    public void saveValues(){
        storageManager.saveValue("paivatTupakoimatta", paivatTupakoimatta);
        storageManager.saveValue("seuraavaTavoite", seuraavaTavoite);
    }

    public void loadValues(){
        paivatTupakoimatta = storageManager.loadValueInt("paivatTupakoimatta");
        seuraavaTavoite = storageManager.loadValueInt("seuraavaTavoite");
    }

    /**
     * Päivittää TextViewin, jossa main activiteetin viestit näkyy.
     * Viesti vaihtuu kun päiviä tupakoimatta arvo saavuttaa switch case:n vaaditun arvon.
     * 365 päivän kohdalla tavoite muutetaan vielä kerran.
     *
     */

    public void UpdateViesti() {

        TextView tv = findViewById(R.id.tvMotivoivatViestit);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


        // switch case, joka vaihtaa viestin kun tavoitepäivä on saavutettu
        switch (paivatTupakoimatta) {
            case 0:
                tv.setText(R.string.paiva0);
                break;
            case 1:
                tv.setText(R.string.paiva1);
                break;
            case 2:
                tv.setText(R.string.paiva2);
                break;
            case 3:
                tv.setText(R.string.paiva3);
                break;
            case 4:
                tv.setText(R.string.paiva4);
                break;
            case 5:
                tv.setText(R.string.paiva5);
                break;
            case 6:
                tv.setText(R.string.paiva6);
                break;
            case 7:
                tv.setText(R.string.viikko1);
                break;
            case 14:
                tv.setText(R.string.viikko2);
                break;
            case 21:
                tv.setText(R.string.viikko3);
                break;
            case 28:
                tv.setText(R.string.viikko4);
                break;
            case 30:
                tv.setText(R.string.kuukausi1);
                break;
            case 35:
                tv.setText(R.string.viikko5);
                break;
            case 42:
                tv.setText(R.string.viikko6);
                break;
            case 49:
                tv.setText(R.string.viikko7);
                break;
            case 56:
                tv.setText(R.string.viikko8);
                break;
            case 60:
                tv.setText(R.string.kuukausi2);
                break;
            case 63:
                tv.setText(R.string.viikko9);
                break;
            case 70:
                tv.setText(R.string.viikko10);
                break;
            case 77:
                tv.setText(R.string.viikko11);
                break;
            case 84:
                tv.setText(R.string.viikko12);
                break;
            case 90:
                tv.setText(R.string.kuukausi3);
                break;
            case 120:
                tv.setText(R.string.kuukausi4);
                break;
            case 150:
                tv.setText(R.string.kuukausi5);
                break;
            case 180:
                tv.setText(R.string.kuukausi6);
                break;
            case 210:
                tv.setText(R.string.kuukausi7);
                break;
            case 240:
                tv.setText(R.string.kuukausi8);
                break;
            case 270:
                tv.setText(R.string.kuukausi9);
                break;
            case 300:
                tv.setText(R.string.kuukausi10);
                break;
            case 330:
                tv.setText(R.string.kuukausi11);
                break;
            case 360:
                tv.setText(R.string.vielavahan);
                break;
            case 365:
                tv.setText(R.string.vuosi);
                break;
            case 366:
                tv.setText(R.string.eteenpain);
                break;
            default:
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv.setText(generateRandomText());
                break;
        }
    }

    private String generateRandomText(){
        Random r = new Random();
        String text = "";

        int randomInt = r.nextInt(100);
        if(randomInt < 10){
            text = getString(R.string.randomviesti1);

        }else if(randomInt < 20){
            text = getString(R.string.randomviesti2);

        }else if(randomInt < 30){
            text = getString(R.string.randomviesti3);

        }else if(randomInt < 40){
            text = getString(R.string.randomviesti4);

        }else if(randomInt < 50){
            text = getString(R.string.randomviesti5);

        }else if(randomInt < 60){
            text = getString(R.string.randomviesti6);

        }else if(randomInt < 70){
            text = getString(R.string.randomviesti7);

        }else if(randomInt < 80){
            text = getString(R.string.randomviesti8);

        }else if(randomInt < 90){
            text = getString(R.string.randomviesti9);

        }else if(randomInt < 100){
            text = getString(R.string.randomviesti10);
        }

        return text;
    }

    private void updateTavoite(){
        if(paivatTupakoimatta != seuraavaTavoite){
            return;
        }

        if(seuraavaTavoite % 30 == 0 && seuraavaTavoite != 330){
            seuraavaTavoite += 30;
            return;
        }

        switch(seuraavaTavoite){
            case 3:
                seuraavaTavoite = 7;
                break;
            case 7:
                seuraavaTavoite = 14;
                break;
            case 14:
                seuraavaTavoite = 21;
                break;
            case 21:
                seuraavaTavoite = 30;
                break;
            case 330:
                seuraavaTavoite = 365;
                break;
            case 365:
                seuraavaTavoite = 390;
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveValues();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadValues();

        // seuraavaTavoite on 0 kun se ladataan ensimmäisen kerran StorageManagerista.
        if (seuraavaTavoite == 0) {
            seuraavaTavoite = 3;
        }

        updateUI();
    }

    private void setNavigationListeners(){
        // Back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
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
                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    break;
            }

            return true;
        });
    }
}