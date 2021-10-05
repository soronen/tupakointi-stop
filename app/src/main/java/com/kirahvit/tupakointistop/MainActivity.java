package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

    }

    public void saveValues(){
        storageManager.saveValue("paivatTupakoimatta", paivatTupakoimatta);
    }

    public void loadValues(){
        paivatTupakoimatta = storageManager.loadValueInt("paivatTupakoimatta");
    }

    public void UpdateTV() {
            int i = getPaivatTupakoimatta();

            switch (i){
                case 0:
                    tv.setText(R.string.paiva0);
                    break;
                case 1:
                    tv.setText(R.string.paiva0);
                    break;
                case 2:
                    tv.setText(R.string.paiva0);
                    break;
                case 3:
                    tv.setText(R.string.paiva0);
                    break;
                case 4:
                    tv.setText(R.string.paiva0);
                    break;
                case 5:
                    tv.setText(R.string.paiva0);
                    break;
                case 6:
                    tv.setText(R.string.paiva0);
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