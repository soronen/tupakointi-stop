package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int paivatTupakoimatta;
    private int paivatTavoitteeseen;

    private StorageManager storageManager;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageManager = StorageManager.getStorageManager(this);

        TextView tv = findViewById(R.id.tvMotivoivatViestit);

    }

    public void onButtonClick(View view){
        int viewId = view.getId();

        if(viewId == R.id.lisääpäiväbutton){
            paivatTupakoimatta++;
        }
        if(viewId == R.id.buttonTilastot){
            goToTilastot();
        }
        else if (viewId == R.id.toiseennäkymäänbutton){
            goToSettings();
        }

        updateUI();
    }

    private void goToSettings(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    private void goToTilastot(){
        Intent intent = new Intent(this, Tilastot.class);
        startActivity(intent);
    }


    public void updateUI(){

        TextView paivatTupakoimattaView = findViewById(R.id.päivättupakoimatta);
        TextView paivatTavoitteeseenView = findViewById(R.id.päivättavoitteeseen);

        paivatTupakoimattaView.setText(String.valueOf(paivatTupakoimatta));
        paivatTavoitteeseenView.setText(String.valueOf(paivatTavoitteeseen));

        UpdateTV();
    }

    public void saveValues(){
        storageManager.saveValue("paivatTupakoimatta", paivatTupakoimatta);
    }

    public void loadValues(){
        paivatTupakoimatta = storageManager.loadValueInt("paivatTupakoimatta");
    }


    public void UpdateTV() {
        int i = getPaivatTupakoimatta();
        TextView tv = findViewById(R.id.tvMotivoivatViestit);


        // päiville 1-7 on omat viestit, sitten uusi viesti vain joka viikko viikkon 12 asti.
        if (i > 7 && i % 7 != 0 && i <= 84) {
            return;

        // viikon 12 jälkeen kuukausilla 3-11 omat viestinsä.
        } else if (i > 84 && i % 30 !=0 && i < 365) {
            return;

        // 1 vuoden kohdalla viesti
        } else if (i == 365) {
            tv.setText(R.string.vuosi);
            return;

        // 1 vuoden jälkeen vielä viimeinen viesti, sitten ei mitään.
        } else if (i >= 366) {
            tv.setText(R.string.eteenpain);
            return;
        }
        // switch case, jossa viesti tavoitteiden kohdalla
        switch (i) {
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
            case 365:
                tv.setText(R.string.vuosi);
                break;
            case 366:
                tv.setText(R.string.eteenpain);
                break;
            default:
                tv.setText(R.string.paivaDEFAULT);
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
        updateUI();
    }

    public int getPaivatTupakoimatta() {
        return paivatTupakoimatta;
    }
}