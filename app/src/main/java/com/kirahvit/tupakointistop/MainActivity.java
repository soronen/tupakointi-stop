package com.kirahvit.tupakointistop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int paivatTupakoimatta;
    private int paivatTavoitteeseen;

    private StorageManager storageManager = new StorageManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view){
        int viewId = view.getId();

        if(viewId == R.id.lisääpäiväbutton){
            paivatTupakoimatta++;
            paivatTavoitteeseen--;
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

    public void updateUI(){

        TextView paivatTupakoimattaView = findViewById(R.id.päivättupakoimatta);
        TextView paivatTavoitteeseenView = findViewById(R.id.päivättavoitteeseen);

        paivatTupakoimattaView.setText(String.valueOf(paivatTupakoimatta));
        paivatTavoitteeseenView.setText(String.valueOf(paivatTavoitteeseen));
    }

    public void saveValues(){
        storageManager.saveValue("paivatTupakoimatta", paivatTupakoimatta);
        storageManager.saveValue("paivatTavoitteeseen",paivatTavoitteeseen);
    }

    public void loadValues(){
        paivatTupakoimatta = storageManager.loadValue("paivatTupakoimatta");
        paivatTavoitteeseen = storageManager.loadValue("paivatTavoitteeseen");
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
}