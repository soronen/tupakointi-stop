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
    ArrayList<String> viestit = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageManager = StorageManager.getStorageManager(this);

        TextView tv = findViewById(R.id.tvMotivoivatViestit);

        viestit.add(0, getResources().getString(R.string.paiva0));
        viestit.add(1, getResources().getString(R.string.paiva1));
        viestit.add(2, getResources().getString(R.string.paiva2));

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

        // päiville 1-7 on omat viestit, sitten uusi viesti vain joka viikko.
        if (i % 7 != 0 && i > 7) {
            return;

        // viikon 24 jälkeen ei viestejä kun vasta päivän 365 ja 366 kohdalla.
        } else if (i > 168 &&  i < 365) {
            return;
        } else if (i > 366) {
            tv.setText(R.string.eteenpain);
        }

        switch (i) {
            case 0:
                tv.setText(R.string.paiva0);
                paivatTavoitteeseen = 1;
                break;
            case 1:
                tv.setText(R.string.paiva1);
                paivatTavoitteeseen = 3;
                break;
            case 2:
                tv.setText(R.string.paiva2);
                paivatTavoitteeseen = 1;
                break;
            case 3:
                tv.setText(R.string.paiva3);
                paivatTavoitteeseen = 7;
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
            case 91:
                tv.setText(R.string.viikko13);
                break;
            case 98:
                tv.setText(R.string.viikko14);
                break;
            case 105:
                tv.setText(R.string.viikko15);
                break;
            case 112:
                tv.setText(R.string.viikko16);
                break;
            case 119:
                tv.setText(R.string.viikko17);
                break;
            case 126:
                tv.setText(R.string.viikko18);
                break;
            case 133:
                tv.setText(R.string.viikko19);
                break;
            case 140:
                tv.setText(R.string.viikko20);
                break;
            case 147:
                tv.setText(R.string.viikko21);
                break;
            case 154:
                tv.setText(R.string.viikko22);
                break;
            case 161:
                tv.setText(R.string.viikko23);
                break;
            case 168:
                tv.setText(R.string.viikko24);
                break;
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